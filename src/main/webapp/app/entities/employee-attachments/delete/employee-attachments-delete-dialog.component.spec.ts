jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { EmployeeAttachmentsService } from '../service/employee-attachments.service';

import { EmployeeAttachmentsDeleteDialogComponent } from './employee-attachments-delete-dialog.component';

describe('Component Tests', () => {
  describe('EmployeeAttachments Management Delete Component', () => {
    let comp: EmployeeAttachmentsDeleteDialogComponent;
    let fixture: ComponentFixture<EmployeeAttachmentsDeleteDialogComponent>;
    let service: EmployeeAttachmentsService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EmployeeAttachmentsDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(EmployeeAttachmentsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeeAttachmentsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(EmployeeAttachmentsService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({})));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        jest.spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
