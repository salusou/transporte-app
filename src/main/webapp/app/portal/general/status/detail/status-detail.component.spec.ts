import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StatusDetailPortalComponent } from './status-detail.component';

describe('Component Tests', () => {
  describe('Status Management Detail Component', () => {
    let comp: StatusDetailPortalComponent;
    let fixture: ComponentFixture<StatusDetailPortalComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [StatusDetailPortalComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ status: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(StatusDetailPortalComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StatusDetailPortalComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load status on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.status).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
