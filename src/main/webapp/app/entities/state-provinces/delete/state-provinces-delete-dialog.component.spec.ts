jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { StateProvincesService } from '../service/state-provinces.service';

import { StateProvincesDeleteDialogComponent } from './state-provinces-delete-dialog.component';

describe('Component Tests', () => {
  describe('StateProvinces Management Delete Component', () => {
    let comp: StateProvincesDeleteDialogComponent;
    let fixture: ComponentFixture<StateProvincesDeleteDialogComponent>;
    let service: StateProvincesService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [StateProvincesDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(StateProvincesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StateProvincesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(StateProvincesService);
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
