jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FeesService } from '../service/fees.service';
import { IFees, Fees } from '../fees.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';

import { FeesUpdateComponent } from './fees-update.component';

describe('Component Tests', () => {
  describe('Fees Management Update Component', () => {
    let comp: FeesUpdateComponent;
    let fixture: ComponentFixture<FeesUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let feesService: FeesService;
    let affiliatesService: AffiliatesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FeesUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FeesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FeesUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      feesService = TestBed.inject(FeesService);
      affiliatesService = TestBed.inject(AffiliatesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Affiliates query and add missing value', () => {
        const fees: IFees = { id: 456 };
        const affiliates: IAffiliates = { id: 49496 };
        fees.affiliates = affiliates;

        const affiliatesCollection: IAffiliates[] = [{ id: 64190 }];
        jest.spyOn(affiliatesService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliatesCollection })));
        const additionalAffiliates = [affiliates];
        const expectedCollection: IAffiliates[] = [...additionalAffiliates, ...affiliatesCollection];
        jest.spyOn(affiliatesService, 'addAffiliatesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ fees });
        comp.ngOnInit();

        expect(affiliatesService.query).toHaveBeenCalled();
        expect(affiliatesService.addAffiliatesToCollectionIfMissing).toHaveBeenCalledWith(affiliatesCollection, ...additionalAffiliates);
        expect(comp.affiliatesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const fees: IFees = { id: 456 };
        const affiliates: IAffiliates = { id: 48112 };
        fees.affiliates = affiliates;

        activatedRoute.data = of({ fees });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(fees));
        expect(comp.affiliatesSharedCollection).toContain(affiliates);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Fees>>();
        const fees = { id: 123 };
        jest.spyOn(feesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ fees });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: fees }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(feesService.update).toHaveBeenCalledWith(fees);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Fees>>();
        const fees = new Fees();
        jest.spyOn(feesService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ fees });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: fees }));
        saveSubject.complete();

        // THEN
        expect(feesService.create).toHaveBeenCalledWith(fees);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Fees>>();
        const fees = { id: 123 };
        jest.spyOn(feesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ fees });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(feesService.update).toHaveBeenCalledWith(fees);
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
