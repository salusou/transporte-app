jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IVehicleInspections, VehicleInspections } from '../vehicle-inspections.model';
import { VehicleInspectionsService } from '../service/vehicle-inspections.service';

import { VehicleInspectionsRoutingResolveService } from './vehicle-inspections-routing-resolve.service';

describe('Service Tests', () => {
  describe('VehicleInspections routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: VehicleInspectionsRoutingResolveService;
    let service: VehicleInspectionsService;
    let resultVehicleInspections: IVehicleInspections | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(VehicleInspectionsRoutingResolveService);
      service = TestBed.inject(VehicleInspectionsService);
      resultVehicleInspections = undefined;
    });

    describe('resolve', () => {
      it('should return IVehicleInspections returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleInspections = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVehicleInspections).toEqual({ id: 123 });
      });

      it('should return new IVehicleInspections if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleInspections = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultVehicleInspections).toEqual(new VehicleInspections());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as VehicleInspections })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleInspections = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVehicleInspections).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
