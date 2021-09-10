import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmployeeComponentsDataDetailComponent } from './employee-components-data-detail.component';

describe('Component Tests', () => {
  describe('EmployeeComponentsData Management Detail Component', () => {
    let comp: EmployeeComponentsDataDetailComponent;
    let fixture: ComponentFixture<EmployeeComponentsDataDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EmployeeComponentsDataDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ employeeComponentsData: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EmployeeComponentsDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeeComponentsDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load employeeComponentsData on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employeeComponentsData).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
