jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IVehicleControlItem, VehicleControlItem } from '../vehicle-control-item.model';
import { VehicleControlItemService } from '../service/vehicle-control-item.service';

import { VehicleControlItemRoutingResolveService } from './vehicle-control-item-routing-resolve.service';

describe('Service Tests', () => {
  describe('VehicleControlItem routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: VehicleControlItemRoutingResolveService;
    let service: VehicleControlItemService;
    let resultVehicleControlItem: IVehicleControlItem | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(VehicleControlItemRoutingResolveService);
      service = TestBed.inject(VehicleControlItemService);
      resultVehicleControlItem = undefined;
    });

    describe('resolve', () => {
      it('should return IVehicleControlItem returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleControlItem = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVehicleControlItem).toEqual({ id: 123 });
      });

      it('should return new IVehicleControlItem if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleControlItem = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultVehicleControlItem).toEqual(new VehicleControlItem());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as VehicleControlItem })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleControlItem = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVehicleControlItem).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
