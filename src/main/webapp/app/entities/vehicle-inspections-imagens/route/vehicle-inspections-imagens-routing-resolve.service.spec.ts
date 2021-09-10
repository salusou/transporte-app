jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IVehicleInspectionsImagens, VehicleInspectionsImagens } from '../vehicle-inspections-imagens.model';
import { VehicleInspectionsImagensService } from '../service/vehicle-inspections-imagens.service';

import { VehicleInspectionsImagensRoutingResolveService } from './vehicle-inspections-imagens-routing-resolve.service';

describe('Service Tests', () => {
  describe('VehicleInspectionsImagens routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: VehicleInspectionsImagensRoutingResolveService;
    let service: VehicleInspectionsImagensService;
    let resultVehicleInspectionsImagens: IVehicleInspectionsImagens | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(VehicleInspectionsImagensRoutingResolveService);
      service = TestBed.inject(VehicleInspectionsImagensService);
      resultVehicleInspectionsImagens = undefined;
    });

    describe('resolve', () => {
      it('should return IVehicleInspectionsImagens returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleInspectionsImagens = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVehicleInspectionsImagens).toEqual({ id: 123 });
      });

      it('should return new IVehicleInspectionsImagens if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleInspectionsImagens = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultVehicleInspectionsImagens).toEqual(new VehicleInspectionsImagens());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as VehicleInspectionsImagens })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVehicleInspectionsImagens = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVehicleInspectionsImagens).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
