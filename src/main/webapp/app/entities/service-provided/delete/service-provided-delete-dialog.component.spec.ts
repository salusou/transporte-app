jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ServiceProvidedService } from '../service/service-provided.service';

import { ServiceProvidedDeleteDialogComponent } from './service-provided-delete-dialog.component';

describe('Component Tests', () => {
  describe('ServiceProvided Management Delete Component', () => {
    let comp: ServiceProvidedDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceProvidedDeleteDialogComponent>;
    let service: ServiceProvidedService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ServiceProvidedDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(ServiceProvidedDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceProvidedDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ServiceProvidedService);
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
