jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { VehicleLocationStatusService } from '../service/vehicle-location-status.service';

import { VehicleLocationStatusDeleteDialogComponent } from './vehicle-location-status-delete-dialog.component';

describe('Component Tests', () => {
  describe('VehicleLocationStatus Management Delete Component', () => {
    let comp: VehicleLocationStatusDeleteDialogComponent;
    let fixture: ComponentFixture<VehicleLocationStatusDeleteDialogComponent>;
    let service: VehicleLocationStatusService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VehicleLocationStatusDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(VehicleLocationStatusDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VehicleLocationStatusDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(VehicleLocationStatusService);
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
