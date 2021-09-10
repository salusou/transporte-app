jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEmployeeAttachments, EmployeeAttachments } from '../employee-attachments.model';
import { EmployeeAttachmentsService } from '../service/employee-attachments.service';

import { EmployeeAttachmentsRoutingResolveService } from './employee-attachments-routing-resolve.service';

describe('Service Tests', () => {
  describe('EmployeeAttachments routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: EmployeeAttachmentsRoutingResolveService;
    let service: EmployeeAttachmentsService;
    let resultEmployeeAttachments: IEmployeeAttachments | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(EmployeeAttachmentsRoutingResolveService);
      service = TestBed.inject(EmployeeAttachmentsService);
      resultEmployeeAttachments = undefined;
    });

    describe('resolve', () => {
      it('should return IEmployeeAttachments returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEmployeeAttachments = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEmployeeAttachments).toEqual({ id: 123 });
      });

      it('should return new IEmployeeAttachments if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEmployeeAttachments = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultEmployeeAttachments).toEqual(new EmployeeAttachments());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as EmployeeAttachments })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEmployeeAttachments = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEmployeeAttachments).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
