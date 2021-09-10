import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ServiceProvidedDetailComponent } from './service-provided-detail.component';

describe('Component Tests', () => {
  describe('ServiceProvided Management Detail Component', () => {
    let comp: ServiceProvidedDetailComponent;
    let fixture: ComponentFixture<ServiceProvidedDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ServiceProvidedDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ serviceProvided: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ServiceProvidedDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceProvidedDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load serviceProvided on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceProvided).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
