jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ISupplierContacts, SupplierContacts } from '../supplier-contacts.model';
import { SupplierContactsService } from '../service/supplier-contacts.service';

import { SupplierContactsRoutingResolveService } from './supplier-contacts-routing-resolve.service';

describe('Service Tests', () => {
  describe('SupplierContacts routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: SupplierContactsRoutingResolveService;
    let service: SupplierContactsService;
    let resultSupplierContacts: ISupplierContacts | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(SupplierContactsRoutingResolveService);
      service = TestBed.inject(SupplierContactsService);
      resultSupplierContacts = undefined;
    });

    describe('resolve', () => {
      it('should return ISupplierContacts returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSupplierContacts = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultSupplierContacts).toEqual({ id: 123 });
      });

      it('should return new ISupplierContacts if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSupplierContacts = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultSupplierContacts).toEqual(new SupplierContacts());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SupplierContacts })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSupplierContacts = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultSupplierContacts).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
