import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CustomersGroupsDetailComponent } from './customers-groups-detail.component';

describe('Component Tests', () => {
  describe('CustomersGroups Management Detail Component', () => {
    let comp: CustomersGroupsDetailComponent;
    let fixture: ComponentFixture<CustomersGroupsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CustomersGroupsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ customersGroups: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CustomersGroupsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomersGroupsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load customersGroups on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customersGroups).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
