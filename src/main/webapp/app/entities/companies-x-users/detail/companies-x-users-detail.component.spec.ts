import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CompaniesXUsersDetailComponent } from './companies-x-users-detail.component';

describe('Component Tests', () => {
  describe('CompaniesXUsers Management Detail Component', () => {
    let comp: CompaniesXUsersDetailComponent;
    let fixture: ComponentFixture<CompaniesXUsersDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CompaniesXUsersDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ companiesXUsers: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CompaniesXUsersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompaniesXUsersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load companiesXUsers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.companiesXUsers).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
