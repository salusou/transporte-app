jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IVehicleControlHistory, VehicleControlHistory } from '../vehicle-control-history.model';
import { VehicleControlHistoryService } from '../service/vehicle-control-history.service';

import { VehicleControlHistoryRoutingResolveService } from './vehicle-control-history-routing-resolve.service';

describe('Service Tests', () => {
  describe('VehicleControlHistory routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: VehicleControlHistoryRoutingResolveService;
    let service: VehicleControlHistoryService;
    let resultVehicleControlHistory: IVehicleControlHistory | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(VehicleControlHistoryRoutingResolveService);
      service = TestBed.inject(VehicleControlHistoryService);
      resultVehicleControlHistory = undefined;
    });

    describe('resolve', () => {
      it('should return IVehicleControlHistory returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleControlHistory = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVehicleControlHistory).toEqual({ id: 123 });
      });

      it('should return new IVehicleControlHistory if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleControlHistory = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultVehicleControlHistory).toEqual(new VehicleControlHistory());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as VehicleControlHistory })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleControlHistory = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVehicleControlHistory).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
