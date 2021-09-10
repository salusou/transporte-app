jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IVehicleControls, VehicleControls } from '../vehicle-controls.model';
import { VehicleControlsService } from '../service/vehicle-controls.service';

import { VehicleControlsRoutingResolveService } from './vehicle-controls-routing-resolve.service';

describe('Service Tests', () => {
  describe('VehicleControls routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: VehicleControlsRoutingResolveService;
    let service: VehicleControlsService;
    let resultVehicleControls: IVehicleControls | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(VehicleControlsRoutingResolveService);
      service = TestBed.inject(VehicleControlsService);
      resultVehicleControls = undefined;
    });

    describe('resolve', () => {
      it('should return IVehicleControls returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleControls = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVehicleControls).toEqual({ id: 123 });
      });

      it('should return new IVehicleControls if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleControls = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultVehicleControls).toEqual(new VehicleControls());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as VehicleControls })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleControls = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVehicleControls).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
