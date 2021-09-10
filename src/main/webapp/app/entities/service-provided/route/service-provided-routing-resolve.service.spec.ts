jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IServiceProvided, ServiceProvided } from '../service-provided.model';
import { ServiceProvidedService } from '../service/service-provided.service';

import { ServiceProvidedRoutingResolveService } from './service-provided-routing-resolve.service';

describe('Service Tests', () => {
  describe('ServiceProvided routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ServiceProvidedRoutingResolveService;
    let service: ServiceProvidedService;
    let resultServiceProvided: IServiceProvided | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ServiceProvidedRoutingResolveService);
      service = TestBed.inject(ServiceProvidedService);
      resultServiceProvided = undefined;
    });

    describe('resolve', () => {
      it('should return IServiceProvided returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultServiceProvided = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultServiceProvided).toEqual({ id: 123 });
      });

      it('should return new IServiceProvided if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultServiceProvided = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultServiceProvided).toEqual(new ServiceProvided());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ServiceProvided })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultServiceProvided = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultServiceProvided).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
