jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IHousingVehicleItem, HousingVehicleItem } from '../housing-vehicle-item.model';
import { HousingVehicleItemService } from '../service/housing-vehicle-item.service';

import { HousingVehicleItemRoutingResolveService } from './housing-vehicle-item-routing-resolve.service';

describe('Service Tests', () => {
  describe('HousingVehicleItem routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: HousingVehicleItemRoutingResolveService;
    let service: HousingVehicleItemService;
    let resultHousingVehicleItem: IHousingVehicleItem | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(HousingVehicleItemRoutingResolveService);
      service = TestBed.inject(HousingVehicleItemService);
      resultHousingVehicleItem = undefined;
    });

    describe('resolve', () => {
      it('should return IHousingVehicleItem returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHousingVehicleItem = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHousingVehicleItem).toEqual({ id: 123 });
      });

      it('should return new IHousingVehicleItem if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHousingVehicleItem = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultHousingVehicleItem).toEqual(new HousingVehicleItem());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as HousingVehicleItem })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHousingVehicleItem = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHousingVehicleItem).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
