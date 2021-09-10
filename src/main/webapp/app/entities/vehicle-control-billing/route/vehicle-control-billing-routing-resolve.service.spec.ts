jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IVehicleControlBilling, VehicleControlBilling } from '../vehicle-control-billing.model';
import { VehicleControlBillingService } from '../service/vehicle-control-billing.service';

import { VehicleControlBillingRoutingResolveService } from './vehicle-control-billing-routing-resolve.service';

describe('Service Tests', () => {
  describe('VehicleControlBilling routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: VehicleControlBillingRoutingResolveService;
    let service: VehicleControlBillingService;
    let resultVehicleControlBilling: IVehicleControlBilling | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(VehicleControlBillingRoutingResolveService);
      service = TestBed.inject(VehicleControlBillingService);
      resultVehicleControlBilling = undefined;
    });

    describe('resolve', () => {
      it('should return IVehicleControlBilling returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleControlBilling = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVehicleControlBilling).toEqual({ id: 123 });
      });

      it('should return new IVehicleControlBilling if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleControlBilling = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultVehicleControlBilling).toEqual(new VehicleControlBilling());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as VehicleControlBilling })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleControlBilling = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVehicleControlBilling).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
