jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IHousing, Housing } from '../housing.model';
import { HousingService } from '../service/housing.service';

import { HousingRoutingResolveService } from './housing-routing-resolve.service';

describe('Service Tests', () => {
  describe('Housing routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: HousingRoutingResolveService;
    let service: HousingService;
    let resultHousing: IHousing | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(HousingRoutingResolveService);
      service = TestBed.inject(HousingService);
      resultHousing = undefined;
    });

    describe('resolve', () => {
      it('should return IHousing returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHousing = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHousing).toEqual({ id: 123 });
      });

      it('should return new IHousing if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHousing = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultHousing).toEqual(new Housing());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Housing })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHousing = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHousing).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
