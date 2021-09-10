import { StatusAttachmentsService } from '../../../../entities/status-attachments/service/status-attachments.service';

jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { IStatusAttachments, StatusAttachments } from '../status-attachments.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';
import { StatusAttachmentsUpdatePortalComponent } from './status-attachments-update.component';

describe('Component Tests', () => {
  describe('StatusAttachments Management Update Component', () => {
    let comp: StatusAttachmentsUpdatePortalComponent;
    let fixture: ComponentFixture<StatusAttachmentsUpdatePortalComponent>;
    let activatedRoute: ActivatedRoute;
    let statusAttachmentsService: StatusAttachmentsService;
    let affiliatesService: AffiliatesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [StatusAttachmentsUpdatePortalComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(StatusAttachmentsUpdatePortalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StatusAttachmentsUpdatePortalComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      statusAttachmentsService = TestBed.inject(StatusAttachmentsService);
      affiliatesService = TestBed.inject(AffiliatesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Affiliates query and add missing value', () => {
        const statusAttachments: IStatusAttachments = { id: 456 };
        const affiliates: IAffiliates = { id: 88582 };
        statusAttachments.affiliates = affiliates;

        const affiliatesCollection: IAffiliates[] = [{ id: 71933 }];
        jest.spyOn(affiliatesService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliatesCollection })));
        const additionalAffiliates = [affiliates];
        const expectedCollection: IAffiliates[] = [...additionalAffiliates, ...affiliatesCollection];
        jest.spyOn(affiliatesService, 'addAffiliatesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ statusAttachments });
        comp.ngOnInit();

        expect(affiliatesService.query).toHaveBeenCalled();
        expect(affiliatesService.addAffiliatesToCollectionIfMissing).toHaveBeenCalledWith(affiliatesCollection, ...additionalAffiliates);
        expect(comp.affiliatesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const statusAttachments: IStatusAttachments = { id: 456 };
        const affiliates: IAffiliates = { id: 21132 };
        statusAttachments.affiliates = affiliates;

        activatedRoute.data = of({ statusAttachments });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(statusAttachments));
        expect(comp.affiliatesSharedCollection).toContain(affiliates);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<StatusAttachments>>();
        const statusAttachments = { id: 123 };
        jest.spyOn(statusAttachmentsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ statusAttachments });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: statusAttachments }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(statusAttachmentsService.update).toHaveBeenCalledWith(statusAttachments);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<StatusAttachments>>();
        const statusAttachments = new StatusAttachments();
        jest.spyOn(statusAttachmentsService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ statusAttachments });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: statusAttachments }));
        saveSubject.complete();

        // THEN
        expect(statusAttachmentsService.create).toHaveBeenCalledWith(statusAttachments);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<StatusAttachments>>();
        const statusAttachments = { id: 123 };
        jest.spyOn(statusAttachmentsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ statusAttachments });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(statusAttachmentsService.update).toHaveBeenCalledWith(statusAttachments);
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
