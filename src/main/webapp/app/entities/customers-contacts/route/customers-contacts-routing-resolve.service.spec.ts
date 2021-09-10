jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICustomersContacts, CustomersContacts } from '../customers-contacts.model';
import { CustomersContactsService } from '../service/customers-contacts.service';

import { CustomersContactsRoutingResolveService } from './customers-contacts-routing-resolve.service';

describe('Service Tests', () => {
  describe('CustomersContacts routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CustomersContactsRoutingResolveService;
    let service: CustomersContactsService;
    let resultCustomersContacts: ICustomersContacts | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CustomersContactsRoutingResolveService);
      service = TestBed.inject(CustomersContactsService);
      resultCustomersContacts = undefined;
    });

    describe('resolve', () => {
      it('should return ICustomersContacts returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCustomersContacts = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCustomersContacts).toEqual({ id: 123 });
      });

      it('should return new ICustomersContacts if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCustomersContacts = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCustomersContacts).toEqual(new CustomersContacts());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CustomersContacts })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCustomersContacts = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCustomersContacts).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
