jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VehicleInspectionsImagensService } from '../service/vehicle-inspections-imagens.service';
import { IVehicleInspectionsImagens, VehicleInspectionsImagens } from '../vehicle-inspections-imagens.model';
import { IVehicleInspections } from 'app/entities/vehicle-inspections/vehicle-inspections.model';
import { VehicleInspectionsService } from 'app/entities/vehicle-inspections/service/vehicle-inspections.service';

import { VehicleInspectionsImagensUpdateComponent } from './vehicle-inspections-imagens-update.component';

describe('Component Tests', () => {
  describe('VehicleInspectionsImagens Management Update Component', () => {
    let comp: VehicleInspectionsImagensUpdateComponent;
    let fixture: ComponentFixture<VehicleInspectionsImagensUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let vehicleInspectionsImagensService: VehicleInspectionsImagensService;
    let vehicleInspectionsService: VehicleInspectionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VehicleInspectionsImagensUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VehicleInspectionsImagensUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehicleInspectionsImagensUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      vehicleInspectionsImagensService = TestBed.inject(VehicleInspectionsImagensService);
      vehicleInspectionsService = TestBed.inject(VehicleInspectionsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call VehicleInspections query and add missing value', () => {
        const vehicleInspectionsImagens: IVehicleInspectionsImagens = { id: 456 };
        const vehicleInspections: IVehicleInspections = { id: 59743 };
        vehicleInspectionsImagens.vehicleInspections = vehicleInspections;

        const vehicleInspectionsCollection: IVehicleInspections[] = [{ id: 53168 }];
        jest.spyOn(vehicleInspectionsService, 'query').mockReturnValue(of(new HttpResponse({ body: vehicleInspectionsCollection })));
        const additionalVehicleInspections = [vehicleInspections];
        const expectedCollection: IVehicleInspections[] = [...additionalVehicleInspections, ...vehicleInspectionsCollection];
        jest.spyOn(vehicleInspectionsService, 'addVehicleInspectionsToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleInspectionsImagens });
        comp.ngOnInit();

        expect(vehicleInspectionsService.query).toHaveBeenCalled();
        expect(vehicleInspectionsService.addVehicleInspectionsToCollectionIfMissing).toHaveBeenCalledWith(
          vehicleInspectionsCollection,
          ...additionalVehicleInspections
        );
        expect(comp.vehicleInspectionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const vehicleInspectionsImagens: IVehicleInspectionsImagens = { id: 456 };
        const vehicleInspections: IVehicleInspections = { id: 67561 };
        vehicleInspectionsImagens.vehicleInspections = vehicleInspections;

        activatedRoute.data = of({ vehicleInspectionsImagens });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(vehicleInspectionsImagens));
        expect(comp.vehicleInspectionsSharedCollection).toContain(vehicleInspections);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleInspectionsImagens>>();
        const vehicleInspectionsImagens = { id: 123 };
        jest.spyOn(vehicleInspectionsImagensService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleInspectionsImagens });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vehicleInspectionsImagens }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(vehicleInspectionsImagensService.update).toHaveBeenCalledWith(vehicleInspectionsImagens);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleInspectionsImagens>>();
        const vehicleInspectionsImagens = new VehicleInspectionsImagens();
        jest.spyOn(vehicleInspectionsImagensService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleInspectionsImagens });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vehicleInspectionsImagens }));
        saveSubject.complete();

        // THEN
        expect(vehicleInspectionsImagensService.create).toHaveBeenCalledWith(vehicleInspectionsImagens);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleInspectionsImagens>>();
        const vehicleInspectionsImagens = { id: 123 };
        jest.spyOn(vehicleInspectionsImagensService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleInspectionsImagens });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(vehicleInspectionsImagensService.update).toHaveBeenCalledWith(vehicleInspectionsImagens);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackVehicleInspectionsById', () => {
        it('Should return tracked VehicleInspections primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackVehicleInspectionsById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
