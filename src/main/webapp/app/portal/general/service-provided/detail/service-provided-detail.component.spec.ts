import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ServiceProvidedDetailPortalComponent } from './service-provided-detail.component';

describe('Component Tests', () => {
  describe('ServiceProvided Management Detail Component', () => {
    let comp: ServiceProvidedDetailPortalComponent;
    let fixture: ComponentFixture<ServiceProvidedDetailPortalComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ServiceProvidedDetailPortalComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ serviceProvided: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ServiceProvidedDetailPortalComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceProvidedDetailPortalComponent);
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
