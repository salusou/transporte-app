jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPositions, Positions } from '../positions.model';
import { PositionsService } from '../service/positions.service';

import { PositionsRoutingResolveService } from './positions-routing-resolve.service';

describe('Service Tests', () => {
  describe('Positions routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: PositionsRoutingResolveService;
    let service: PositionsService;
    let resultPositions: IPositions | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(PositionsRoutingResolveService);
      service = TestBed.inject(PositionsService);
      resultPositions = undefined;
    });

    describe('resolve', () => {
      it('should return IPositions returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPositions = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPositions).toEqual({ id: 123 });
      });

      it('should return new IPositions if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPositions = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultPositions).toEqual(new Positions());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Positions })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPositions = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPositions).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
