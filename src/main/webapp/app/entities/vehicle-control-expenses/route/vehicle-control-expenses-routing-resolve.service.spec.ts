jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IVehicleControlExpenses, VehicleControlExpenses } from '../vehicle-control-expenses.model';
import { VehicleControlExpensesService } from '../service/vehicle-control-expenses.service';

import { VehicleControlExpensesRoutingResolveService } from './vehicle-control-expenses-routing-resolve.service';

describe('Service Tests', () => {
  describe('VehicleControlExpenses routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: VehicleControlExpensesRoutingResolveService;
    let service: VehicleControlExpensesService;
    let resultVehicleControlExpenses: IVehicleControlExpenses | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(VehicleControlExpensesRoutingResolveService);
      service = TestBed.inject(VehicleControlExpensesService);
      resultVehicleControlExpenses = undefined;
    });

    describe('resolve', () => {
      it('should return IVehicleControlExpenses returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleControlExpenses = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVehicleControlExpenses).toEqual({ id: 123 });
      });

      it('should return new IVehicleControlExpenses if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleControlExpenses = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultVehicleControlExpenses).toEqual(new VehicleControlExpenses());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as VehicleControlExpenses })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleControlExpenses = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVehicleControlExpenses).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
