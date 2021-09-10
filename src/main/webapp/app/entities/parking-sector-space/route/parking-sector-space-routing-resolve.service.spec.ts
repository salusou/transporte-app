jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IParkingSectorSpace, ParkingSectorSpace } from '../parking-sector-space.model';
import { ParkingSectorSpaceService } from '../service/parking-sector-space.service';

import { ParkingSectorSpaceRoutingResolveService } from './parking-sector-space-routing-resolve.service';

describe('Service Tests', () => {
  describe('ParkingSectorSpace routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ParkingSectorSpaceRoutingResolveService;
    let service: ParkingSectorSpaceService;
    let resultParkingSectorSpace: IParkingSectorSpace | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ParkingSectorSpaceRoutingResolveService);
      service = TestBed.inject(ParkingSectorSpaceService);
      resultParkingSectorSpace = undefined;
    });

    describe('resolve', () => {
      it('should return IParkingSectorSpace returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultParkingSectorSpace = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultParkingSectorSpace).toEqual({ id: 123 });
      });

      it('should return new IParkingSectorSpace if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultParkingSectorSpace = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultParkingSectorSpace).toEqual(new ParkingSectorSpace());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ParkingSectorSpace })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultParkingSectorSpace = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultParkingSectorSpace).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
