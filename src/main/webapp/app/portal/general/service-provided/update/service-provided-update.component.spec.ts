import { ServiceProvidedService } from '../../../../entities/service-provided/service/service-provided.service';

jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { IServiceProvided, ServiceProvided } from '../service-provided.model';

import { ServiceProvidedUpdatePortalComponent } from './service-provided-update.component';

describe('Component Tests', () => {
  describe('ServiceProvided Management Update Component', () => {
    let comp: ServiceProvidedUpdatePortalComponent;
    let fixture: ComponentFixture<ServiceProvidedUpdatePortalComponent>;
    let activatedRoute: ActivatedRoute;
    let serviceProvidedService: ServiceProvidedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ServiceProvidedUpdatePortalComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ServiceProvidedUpdatePortalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceProvidedUpdatePortalComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      serviceProvidedService = TestBed.inject(ServiceProvidedService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const serviceProvided: IServiceProvided = { id: 456 };

        activatedRoute.data = of({ serviceProvided });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(serviceProvided));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ServiceProvided>>();
        const serviceProvided = { id: 123 };
        jest.spyOn(serviceProvidedService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ serviceProvided });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: serviceProvided }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(serviceProvidedService.update).toHaveBeenCalledWith(serviceProvided);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ServiceProvided>>();
        const serviceProvided = new ServiceProvided();
        jest.spyOn(serviceProvidedService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ serviceProvided });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: serviceProvided }));
        saveSubject.complete();

        // THEN
        expect(serviceProvidedService.create).toHaveBeenCalledWith(serviceProvided);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ServiceProvided>>();
        const serviceProvided = { id: 123 };
        jest.spyOn(serviceProvidedService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ serviceProvided });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(serviceProvidedService.update).toHaveBeenCalledWith(serviceProvided);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
