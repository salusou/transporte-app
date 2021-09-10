jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { SupplierBanksInfoService } from '../service/supplier-banks-info.service';

import { SupplierBanksInfoDeleteDialogComponent } from './supplier-banks-info-delete-dialog.component';

describe('Component Tests', () => {
  describe('SupplierBanksInfo Management Delete Component', () => {
    let comp: SupplierBanksInfoDeleteDialogComponent;
    let fixture: ComponentFixture<SupplierBanksInfoDeleteDialogComponent>;
    let service: SupplierBanksInfoService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SupplierBanksInfoDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(SupplierBanksInfoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupplierBanksInfoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(SupplierBanksInfoService);
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
