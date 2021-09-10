jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEmployeeComponentsData, EmployeeComponentsData } from '../employee-components-data.model';
import { EmployeeComponentsDataService } from '../service/employee-components-data.service';

import { EmployeeComponentsDataRoutingResolveService } from './employee-components-data-routing-resolve.service';

describe('Service Tests', () => {
  describe('EmployeeComponentsData routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: EmployeeComponentsDataRoutingResolveService;
    let service: EmployeeComponentsDataService;
    let resultEmployeeComponentsData: IEmployeeComponentsData | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(EmployeeComponentsDataRoutingResolveService);
      service = TestBed.inject(EmployeeComponentsDataService);
      resultEmployeeComponentsData = undefined;
    });

    describe('resolve', () => {
      it('should return IEmployeeComponentsData returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEmployeeComponentsData = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEmployeeComponentsData).toEqual({ id: 123 });
      });

      it('should return new IEmployeeComponentsData if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEmployeeComponentsData = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultEmployeeComponentsData).toEqual(new EmployeeComponentsData());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as EmployeeComponentsData })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEmployeeComponentsData = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEmployeeComponentsData).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
