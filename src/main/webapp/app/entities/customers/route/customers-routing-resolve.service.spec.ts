jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICustomers, Customers } from '../customers.model';
import { CustomersService } from '../service/customers.service';

import { CustomersRoutingResolveService } from './customers-routing-resolve.service';

describe('Service Tests', () => {
  describe('Customers routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CustomersRoutingResolveService;
    let service: CustomersService;
    let resultCustomers: ICustomers | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CustomersRoutingResolveService);
      service = TestBed.inject(CustomersService);
      resultCustomers = undefined;
    });

    describe('resolve', () => {
      it('should return ICustomers returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCustomers = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCustomers).toEqual({ id: 123 });
      });

      it('should return new ICustomers if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCustomers = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCustomers).toEqual(new Customers());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Customers })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCustomers = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCustomers).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
