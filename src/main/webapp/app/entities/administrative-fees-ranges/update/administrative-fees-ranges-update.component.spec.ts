jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AdministrativeFeesRangesService } from '../service/administrative-fees-ranges.service';
import { IAdministrativeFeesRanges, AdministrativeFeesRanges } from '../administrative-fees-ranges.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';

import { AdministrativeFeesRangesUpdateComponent } from './administrative-fees-ranges-update.component';

describe('Component Tests', () => {
  describe('AdministrativeFeesRanges Management Update Component', () => {
    let comp: AdministrativeFeesRangesUpdateComponent;
    let fixture: ComponentFixture<AdministrativeFeesRangesUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let administrativeFeesRangesService: AdministrativeFeesRangesService;
    let affiliatesService: AffiliatesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AdministrativeFeesRangesUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AdministrativeFeesRangesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdministrativeFeesRangesUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      administrativeFeesRangesService = TestBed.inject(AdministrativeFeesRangesService);
      affiliatesService = TestBed.inject(AffiliatesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Affiliates query and add missing value', () => {
        const administrativeFeesRanges: IAdministrativeFeesRanges = { id: 456 };
        const affiliates: IAffiliates = { id: 69875 };
        administrativeFeesRanges.affiliates = affiliates;

        const affiliatesCollection: IAffiliates[] = [{ id: 40884 }];
        jest.spyOn(affiliatesService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliatesCollection })));
        const additionalAffiliates = [affiliates];
        const expectedCollection: IAffiliates[] = [...additionalAffiliates, ...affiliatesCollection];
        jest.spyOn(affiliatesService, 'addAffiliatesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ administrativeFeesRanges });
        comp.ngOnInit();

        expect(affiliatesService.query).toHaveBeenCalled();
        expect(affiliatesService.addAffiliatesToCollectionIfMissing).toHaveBeenCalledWith(affiliatesCollection, ...additionalAffiliates);
        expect(comp.affiliatesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const administrativeFeesRanges: IAdministrativeFeesRanges = { id: 456 };
        const affiliates: IAffiliates = { id: 38254 };
        administrativeFeesRanges.affiliates = affiliates;

        activatedRoute.data = of({ administrativeFeesRanges });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(administrativeFeesRanges));
        expect(comp.affiliatesSharedCollection).toContain(affiliates);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AdministrativeFeesRanges>>();
        const administrativeFeesRanges = { id: 123 };
        jest.spyOn(administrativeFeesRangesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ administrativeFeesRanges });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: administrativeFeesRanges }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(administrativeFeesRangesService.update).toHaveBeenCalledWith(administrativeFeesRanges);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AdministrativeFeesRanges>>();
        const administrativeFeesRanges = new AdministrativeFeesRanges();
        jest.spyOn(administrativeFeesRangesService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ administrativeFeesRanges });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: administrativeFeesRanges }));
        saveSubject.complete();

        // THEN
        expect(administrativeFeesRangesService.create).toHaveBeenCalledWith(administrativeFeesRanges);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AdministrativeFeesRanges>>();
        const administrativeFeesRanges = { id: 123 };
        jest.spyOn(administrativeFeesRangesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ administrativeFeesRanges });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(administrativeFeesRangesService.update).toHaveBeenCalledWith(administrativeFeesRanges);
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
