import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DataUtils } from 'app/core/util/data-util.service';

import { CustomerAttachmentsDetailComponent } from './customer-attachments-detail.component';

describe('Component Tests', () => {
  describe('CustomerAttachments Management Detail Component', () => {
    let comp: CustomerAttachmentsDetailComponent;
    let fixture: ComponentFixture<CustomerAttachmentsDetailComponent>;
    let dataUtils: DataUtils;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CustomerAttachmentsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ customerAttachments: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CustomerAttachmentsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerAttachmentsDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = TestBed.inject(DataUtils);
      jest.spyOn(window, 'open').mockImplementation(() => null);
    });

    describe('OnInit', () => {
      it('Should load customerAttachments on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customerAttachments).toEqual(expect.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from DataUtils', () => {
        // GIVEN
        jest.spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from DataUtils', () => {
        // GIVEN
        jest.spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeBase64, fakeContentType);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeBase64, fakeContentType);
      });
    });
  });
});
