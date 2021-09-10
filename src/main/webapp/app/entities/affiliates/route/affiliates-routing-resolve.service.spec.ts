jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAffiliates, Affiliates } from '../affiliates.model';
import { AffiliatesService } from '../service/affiliates.service';

import { AffiliatesRoutingResolveService } from './affiliates-routing-resolve.service';

describe('Service Tests', () => {
  describe('Affiliates routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AffiliatesRoutingResolveService;
    let service: AffiliatesService;
    let resultAffiliates: IAffiliates | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AffiliatesRoutingResolveService);
      service = TestBed.inject(AffiliatesService);
      resultAffiliates = undefined;
    });

    describe('resolve', () => {
      it('should return IAffiliates returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAffiliates = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAffiliates).toEqual({ id: 123 });
      });

      it('should return new IAffiliates if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAffiliates = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAffiliates).toEqual(new Affiliates());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Affiliates })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAffiliates = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAffiliates).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
