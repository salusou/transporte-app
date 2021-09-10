jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CostCenterService } from '../service/cost-center.service';
import { ICostCenter, CostCenter } from '../cost-center.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';

import { CostCenterUpdateComponent } from './cost-center-update.component';

describe('Component Tests', () => {
  describe('CostCenter Management Update Component', () => {
    let comp: CostCenterUpdateComponent;
    let fixture: ComponentFixture<CostCenterUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let costCenterService: CostCenterService;
    let affiliatesService: AffiliatesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CostCenterUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CostCenterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CostCenterUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      costCenterService = TestBed.inject(CostCenterService);
      affiliatesService = TestBed.inject(AffiliatesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Affiliates query and add missing value', () => {
        const costCenter: ICostCenter = { id: 456 };
        const affiliates: IAffiliates = { id: 95759 };
        costCenter.affiliates = affiliates;

        const affiliatesCollection: IAffiliates[] = [{ id: 96645 }];
        jest.spyOn(affiliatesService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliatesCollection })));
        const additionalAffiliates = [affiliates];
        const expectedCollection: IAffiliates[] = [...additionalAffiliates, ...affiliatesCollection];
        jest.spyOn(affiliatesService, 'addAffiliatesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ costCenter });
        comp.ngOnInit();

        expect(affiliatesService.query).toHaveBeenCalled();
        expect(affiliatesService.addAffiliatesToCollectionIfMissing).toHaveBeenCalledWith(affiliatesCollection, ...additionalAffiliates);
        expect(comp.affiliatesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const costCenter: ICostCenter = { id: 456 };
        const affiliates: IAffiliates = { id: 71363 };
        costCenter.affiliates = affiliates;

        activatedRoute.data = of({ costCenter });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(costCenter));
        expect(comp.affiliatesSharedCollection).toContain(affiliates);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CostCenter>>();
        const costCenter = { id: 123 };
        jest.spyOn(costCenterService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ costCenter });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: costCenter }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(costCenterService.update).toHaveBeenCalledWith(costCenter);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CostCenter>>();
        const costCenter = new CostCenter();
        jest.spyOn(costCenterService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ costCenter });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: costCenter }));
        saveSubject.complete();

        // THEN
        expect(costCenterService.create).toHaveBeenCalledWith(costCenter);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CostCenter>>();
        const costCenter = { id: 123 };
        jest.spyOn(costCenterService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ costCenter });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(costCenterService.update).toHaveBeenCalledWith(costCenter);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackAffiliatesById', () => {
        it('Should return tracked Affiliates primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackAffiliatesById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
