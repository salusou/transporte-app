jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IInsurances, Insurances } from '../insurances.model';
import { InsurancesService } from '../service/insurances.service';

import { InsurancesRoutingResolveService } from './insurances-routing-resolve.service';

describe('Service Tests', () => {
  describe('Insurances routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: InsurancesRoutingResolveService;
    let service: InsurancesService;
    let resultInsurances: IInsurances | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(InsurancesRoutingResolveService);
      service = TestBed.inject(InsurancesService);
      resultInsurances = undefined;
    });

    describe('resolve', () => {
      it('should return IInsurances returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultInsurances = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultInsurances).toEqual({ id: 123 });
      });

      it('should return new IInsurances if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultInsurances = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultInsurances).toEqual(new Insurances());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Insurances })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultInsurances = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultInsurances).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
