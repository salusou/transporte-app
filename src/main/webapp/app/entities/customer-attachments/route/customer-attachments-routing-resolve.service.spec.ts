jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICustomerAttachments, CustomerAttachments } from '../customer-attachments.model';
import { CustomerAttachmentsService } from '../service/customer-attachments.service';

import { CustomerAttachmentsRoutingResolveService } from './customer-attachments-routing-resolve.service';

describe('Service Tests', () => {
  describe('CustomerAttachments routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CustomerAttachmentsRoutingResolveService;
    let service: CustomerAttachmentsService;
    let resultCustomerAttachments: ICustomerAttachments | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CustomerAttachmentsRoutingResolveService);
      service = TestBed.inject(CustomerAttachmentsService);
      resultCustomerAttachments = undefined;
    });

    describe('resolve', () => {
      it('should return ICustomerAttachments returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCustomerAttachments = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCustomerAttachments).toEqual({ id: 123 });
      });

      it('should return new ICustomerAttachments if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCustomerAttachments = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCustomerAttachments).toEqual(new CustomerAttachments());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CustomerAttachments })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCustomerAttachments = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCustomerAttachments).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
