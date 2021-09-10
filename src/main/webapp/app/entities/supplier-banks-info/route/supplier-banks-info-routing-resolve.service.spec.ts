jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ISupplierBanksInfo, SupplierBanksInfo } from '../supplier-banks-info.model';
import { SupplierBanksInfoService } from '../service/supplier-banks-info.service';

import { SupplierBanksInfoRoutingResolveService } from './supplier-banks-info-routing-resolve.service';

describe('Service Tests', () => {
  describe('SupplierBanksInfo routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: SupplierBanksInfoRoutingResolveService;
    let service: SupplierBanksInfoService;
    let resultSupplierBanksInfo: ISupplierBanksInfo | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(SupplierBanksInfoRoutingResolveService);
      service = TestBed.inject(SupplierBanksInfoService);
      resultSupplierBanksInfo = undefined;
    });

    describe('resolve', () => {
      it('should return ISupplierBanksInfo returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSupplierBanksInfo = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultSupplierBanksInfo).toEqual({ id: 123 });
      });

      it('should return new ISupplierBanksInfo if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSupplierBanksInfo = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultSupplierBanksInfo).toEqual(new SupplierBanksInfo());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SupplierBanksInfo })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSupplierBanksInfo = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultSupplierBanksInfo).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
