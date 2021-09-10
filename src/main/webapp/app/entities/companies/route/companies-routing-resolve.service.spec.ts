jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICompanies, Companies } from '../companies.model';
import { CompaniesService } from '../service/companies.service';

import { CompaniesRoutingResolveService } from './companies-routing-resolve.service';

describe('Service Tests', () => {
  describe('Companies routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CompaniesRoutingResolveService;
    let service: CompaniesService;
    let resultCompanies: ICompanies | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CompaniesRoutingResolveService);
      service = TestBed.inject(CompaniesService);
      resultCompanies = undefined;
    });

    describe('resolve', () => {
      it('should return ICompanies returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCompanies = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCompanies).toEqual({ id: 123 });
      });

      it('should return new ICompanies if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCompanies = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCompanies).toEqual(new Companies());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Companies })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCompanies = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCompanies).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
