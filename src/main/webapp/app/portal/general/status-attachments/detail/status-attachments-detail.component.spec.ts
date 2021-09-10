import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StatusAttachmentsDetailPortalComponent } from './status-attachments-detail.component';

describe('Component Tests', () => {
  describe('StatusAttachments Management Detail Component', () => {
    let comp: StatusAttachmentsDetailPortalComponent;
    let fixture: ComponentFixture<StatusAttachmentsDetailPortalComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [StatusAttachmentsDetailPortalComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ statusAttachments: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(StatusAttachmentsDetailPortalComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StatusAttachmentsDetailPortalComponent);
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
