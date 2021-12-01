jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICompaniesXUsers, CompaniesXUsers } from '../companies-x-users.model';
import { CompaniesXUsersService } from '../service/companies-x-users.service';

import { CompaniesXUsersRoutingResolveService } from './companies-x-users-routing-resolve.service';

describe('Service Tests', () => {
  describe('CompaniesXUsers routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CompaniesXUsersRoutingResolveService;
    let service: CompaniesXUsersService;
    let resultCompaniesXUsers: ICompaniesXUsers | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CompaniesXUsersRoutingResolveService);
      service = TestBed.inject(CompaniesXUsersService);
      resultCompaniesXUsers = undefined;
    });

    describe('resolve', () => {
      it('should return ICompaniesXUsers returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCompaniesXUsers = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCompaniesXUsers).toEqual({ id: 123 });
      });

      it('should return new ICompaniesXUsers if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCompaniesXUsers = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCompaniesXUsers).toEqual(new CompaniesXUsers());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CompaniesXUsers })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCompaniesXUsers = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCompaniesXUsers).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
