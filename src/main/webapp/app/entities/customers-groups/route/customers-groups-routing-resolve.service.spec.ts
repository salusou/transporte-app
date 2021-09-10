jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICustomersGroups, CustomersGroups } from '../customers-groups.model';
import { CustomersGroupsService } from '../service/customers-groups.service';

import { CustomersGroupsRoutingResolveService } from './customers-groups-routing-resolve.service';

describe('Service Tests', () => {
  describe('CustomersGroups routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CustomersGroupsRoutingResolveService;
    let service: CustomersGroupsService;
    let resultCustomersGroups: ICustomersGroups | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CustomersGroupsRoutingResolveService);
      service = TestBed.inject(CustomersGroupsService);
      resultCustomersGroups = undefined;
    });

    describe('resolve', () => {
      it('should return ICustomersGroups returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCustomersGroups = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCustomersGroups).toEqual({ id: 123 });
      });

      it('should return new ICustomersGroups if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCustomersGroups = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCustomersGroups).toEqual(new CustomersGroups());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CustomersGroups })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCustomersGroups = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCustomersGroups).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
