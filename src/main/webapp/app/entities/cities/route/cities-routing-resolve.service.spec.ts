jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICities, Cities } from '../cities.model';
import { CitiesService } from '../service/cities.service';

import { CitiesRoutingResolveService } from './cities-routing-resolve.service';

describe('Service Tests', () => {
  describe('Cities routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CitiesRoutingResolveService;
    let service: CitiesService;
    let resultCities: ICities | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CitiesRoutingResolveService);
      service = TestBed.inject(CitiesService);
      resultCities = undefined;
    });

    describe('resolve', () => {
      it('should return ICities returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCities = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCities).toEqual({ id: 123 });
      });

      it('should return new ICities if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCities = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCities).toEqual(new Cities());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Cities })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCities = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCities).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
