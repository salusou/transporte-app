import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmployeesDetailComponent } from './employees-detail.component';

describe('Component Tests', () => {
  describe('Employees Management Detail Component', () => {
    let comp: EmployeesDetailComponent;
    let fixture: ComponentFixture<EmployeesDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EmployeesDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ employees: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EmployeesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load employees on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employees).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
