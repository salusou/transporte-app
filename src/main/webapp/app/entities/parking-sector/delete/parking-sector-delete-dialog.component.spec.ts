jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ParkingSectorService } from '../service/parking-sector.service';

import { ParkingSectorDeleteDialogComponent } from './parking-sector-delete-dialog.component';

describe('Component Tests', () => {
  describe('ParkingSector Management Delete Component', () => {
    let comp: ParkingSectorDeleteDialogComponent;
    let fixture: ComponentFixture<ParkingSectorDeleteDialogComponent>;
    let service: ParkingSectorService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ParkingSectorDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(ParkingSectorDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParkingSectorDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ParkingSectorService);
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
