jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IVehicleLocationStatus, VehicleLocationStatus } from '../vehicle-location-status.model';
import { VehicleLocationStatusService } from '../service/vehicle-location-status.service';

import { VehicleLocationStatusRoutingResolveService } from './vehicle-location-status-routing-resolve.service';

describe('Service Tests', () => {
  describe('VehicleLocationStatus routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: VehicleLocationStatusRoutingResolveService;
    let service: VehicleLocationStatusService;
    let resultVehicleLocationStatus: IVehicleLocationStatus | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(VehicleLocationStatusRoutingResolveService);
      service = TestBed.inject(VehicleLocationStatusService);
      resultVehicleLocationStatus = undefined;
    });

    describe('resolve', () => {
      it('should return IVehicleLocationStatus returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleLocationStatus = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVehicleLocationStatus).toEqual({ id: 123 });
      });

      it('should return new IVehicleLocationStatus if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleLocationStatus = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultVehicleLocationStatus).toEqual(new VehicleLocationStatus());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as VehicleLocationStatus })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleLocationStatus = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVehicleLocationStatus).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
