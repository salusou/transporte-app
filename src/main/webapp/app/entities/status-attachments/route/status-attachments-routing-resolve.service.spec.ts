jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IStatusAttachments, StatusAttachments } from '../status-attachments.model';
import { StatusAttachmentsService } from '../service/status-attachments.service';

import { StatusAttachmentsRoutingResolveService } from './status-attachments-routing-resolve.service';

describe('Service Tests', () => {
  describe('StatusAttachments routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: StatusAttachmentsRoutingResolveService;
    let service: StatusAttachmentsService;
    let resultStatusAttachments: IStatusAttachments | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(StatusAttachmentsRoutingResolveService);
      service = TestBed.inject(StatusAttachmentsService);
      resultStatusAttachments = undefined;
    });

    describe('resolve', () => {
      it('should return IStatusAttachments returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultStatusAttachments = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultStatusAttachments).toEqual({ id: 123 });
      });

      it('should return new IStatusAttachments if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultStatusAttachments = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultStatusAttachments).toEqual(new StatusAttachments());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as StatusAttachments })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultStatusAttachments = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultStatusAttachments).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
