jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IParking, Parking } from '../parking.model';
import { ParkingService } from '../service/parking.service';

import { ParkingRoutingResolveService } from './parking-routing-resolve.service';

describe('Service Tests', () => {
  describe('Parking routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ParkingRoutingResolveService;
    let service: ParkingService;
    let resultParking: IParking | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ParkingRoutingResolveService);
      service = TestBed.inject(ParkingService);
      resultParking = undefined;
    });

    describe('resolve', () => {
      it('should return IParking returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultParking = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultParking).toEqual({ id: 123 });
      });

      it('should return new IParking if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultParking = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultParking).toEqual(new Parking());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Parking })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultParking = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultParking).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
