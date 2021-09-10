jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFees, Fees } from '../fees.model';
import { FeesService } from '../service/fees.service';

import { FeesRoutingResolveService } from './fees-routing-resolve.service';

describe('Service Tests', () => {
  describe('Fees routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: FeesRoutingResolveService;
    let service: FeesService;
    let resultFees: IFees | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(FeesRoutingResolveService);
      service = TestBed.inject(FeesService);
      resultFees = undefined;
    });

    describe('resolve', () => {
      it('should return IFees returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFees = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFees).toEqual({ id: 123 });
      });

      it('should return new IFees if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFees = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultFees).toEqual(new Fees());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Fees })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFees = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFees).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
