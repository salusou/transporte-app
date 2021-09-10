jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAdministrativeFeesRanges, AdministrativeFeesRanges } from '../administrative-fees-ranges.model';
import { AdministrativeFeesRangesService } from '../service/administrative-fees-ranges.service';

import { AdministrativeFeesRangesRoutingResolveService } from './administrative-fees-ranges-routing-resolve.service';

describe('Service Tests', () => {
  describe('AdministrativeFeesRanges routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AdministrativeFeesRangesRoutingResolveService;
    let service: AdministrativeFeesRangesService;
    let resultAdministrativeFeesRanges: IAdministrativeFeesRanges | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AdministrativeFeesRangesRoutingResolveService);
      service = TestBed.inject(AdministrativeFeesRangesService);
      resultAdministrativeFeesRanges = undefined;
    });

    describe('resolve', () => {
      it('should return IAdministrativeFeesRanges returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAdministrativeFeesRanges = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAdministrativeFeesRanges).toEqual({ id: 123 });
      });

      it('should return new IAdministrativeFeesRanges if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAdministrativeFeesRanges = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAdministrativeFeesRanges).toEqual(new AdministrativeFeesRanges());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AdministrativeFeesRanges })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAdministrativeFeesRanges = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAdministrativeFeesRanges).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
