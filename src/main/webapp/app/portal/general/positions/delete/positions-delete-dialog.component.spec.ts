import { PositionsService } from '../../../../entities/positions/service/positions.service';

jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { PositionsDeleteDialogPortalComponent } from './positions-delete-dialog.component';

describe('Component Tests', () => {
  describe('Positions Management Delete Component', () => {
    let comp: PositionsDeleteDialogPortalComponent;
    let fixture: ComponentFixture<PositionsDeleteDialogPortalComponent>;
    let service: PositionsService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PositionsDeleteDialogPortalComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(PositionsDeleteDialogPortalComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PositionsDeleteDialogPortalComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(PositionsService);
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
