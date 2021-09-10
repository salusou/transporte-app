jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IVehicleControlAttachments, VehicleControlAttachments } from '../vehicle-control-attachments.model';
import { VehicleControlAttachmentsService } from '../service/vehicle-control-attachments.service';

import { VehicleControlAttachmentsRoutingResolveService } from './vehicle-control-attachments-routing-resolve.service';

describe('Service Tests', () => {
  describe('VehicleControlAttachments routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: VehicleControlAttachmentsRoutingResolveService;
    let service: VehicleControlAttachmentsService;
    let resultVehicleControlAttachments: IVehicleControlAttachments | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(VehicleControlAttachmentsRoutingResolveService);
      service = TestBed.inject(VehicleControlAttachmentsService);
      resultVehicleControlAttachments = undefined;
    });

    describe('resolve', () => {
      it('should return IVehicleControlAttachments returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleControlAttachments = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVehicleControlAttachments).toEqual({ id: 123 });
      });

      it('should return new IVehicleControlAttachments if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleControlAttachments = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultVehicleControlAttachments).toEqual(new VehicleControlAttachments());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as VehicleControlAttachments })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleControlAttachments = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVehicleControlAttachments).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
