import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StatusAttachmentsDetailComponent } from './status-attachments-detail.component';

describe('Component Tests', () => {
  describe('StatusAttachments Management Detail Component', () => {
    let comp: StatusAttachmentsDetailComponent;
    let fixture: ComponentFixture<StatusAttachmentsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [StatusAttachmentsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ statusAttachments: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(StatusAttachmentsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StatusAttachmentsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load statusAttachments on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.statusAttachments).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
