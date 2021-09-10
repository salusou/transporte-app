import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IVehicleControls, VehicleControls } from '../vehicle-controls.model';

import { VehicleControlsService } from './vehicle-controls.service';

describe('Service Tests', () => {
  describe('VehicleControls Service', () => {
    let service: VehicleControlsService;
    let httpMock: HttpTestingController;
    let elemDefault: IVehicleControls;
    let expectedResult: IVehicleControls | IVehicleControls[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(VehicleControlsService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        vehicleControlAuthorizedOrder: 'AAAAAAA',
        vehicleControlRequest: 'AAAAAAA',
        vehicleControlSinister: 'AAAAAAA',
        vehicleControlDate: currentDate,
        vehicleControlKm: 0,
        vehicleControlPlate: 'AAAAAAA',
        vehicleControlAmount: 0,
        vehicleControlPrice: 0,
        vehicleControlMaximumDeliveryDate: currentDate,
        vehicleControlCollectionForecast: currentDate,
        vehicleControlCollectionDeliveryForecast: currentDate,
        vehicleControlDateCollected: currentDate,
        vehicleControlDeliveryDate: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            vehicleControlDate: currentDate.format(DATE_FORMAT),
            vehicleControlMaximumDeliveryDate: currentDate.format(DATE_FORMAT),
            vehicleControlCollectionForecast: currentDate.format(DATE_FORMAT),
            vehicleControlCollectionDeliveryForecast: currentDate.format(DATE_FORMAT),
            vehicleControlDateCollected: currentDate.format(DATE_FORMAT),
            vehicleControlDeliveryDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a VehicleControls', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            vehicleControlDate: currentDate.format(DATE_FORMAT),
            vehicleControlMaximumDeliveryDate: currentDate.format(DATE_FORMAT),
            vehicleControlCollectionForecast: currentDate.format(DATE_FORMAT),
            vehicleControlCollectionDeliveryForecast: currentDate.format(DATE_FORMAT),
            vehicleControlDateCollected: currentDate.format(DATE_FORMAT),
            vehicleControlDeliveryDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleControlDate: currentDate,
            vehicleControlMaximumDeliveryDate: currentDate,
            vehicleControlCollectionForecast: currentDate,
            vehicleControlCollectionDeliveryForecast: currentDate,
            vehicleControlDateCollected: currentDate,
            vehicleControlDeliveryDate: currentDate,
          },
          returnedFromService
        );

        service.create(new VehicleControls()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a VehicleControls', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            vehicleControlAuthorizedOrder: 'BBBBBB',
            vehicleControlRequest: 'BBBBBB',
            vehicleControlSinister: 'BBBBBB',
            vehicleControlDate: currentDate.format(DATE_FORMAT),
            vehicleControlKm: 1,
            vehicleControlPlate: 'BBBBBB',
            vehicleControlAmount: 1,
            vehicleControlPrice: 1,
            vehicleControlMaximumDeliveryDate: currentDate.format(DATE_FORMAT),
            vehicleControlCollectionForecast: currentDate.format(DATE_FORMAT),
            vehicleControlCollectionDeliveryForecast: currentDate.format(DATE_FORMAT),
            vehicleControlDateCollected: currentDate.format(DATE_FORMAT),
            vehicleControlDeliveryDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleControlDate: currentDate,
            vehicleControlMaximumDeliveryDate: currentDate,
            vehicleControlCollectionForecast: currentDate,
            vehicleControlCollectionDeliveryForecast: currentDate,
            vehicleControlDateCollected: currentDate,
            vehicleControlDeliveryDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a VehicleControls', () => {
        const patchObject = Object.assign(
          {
            vehicleControlAuthorizedOrder: 'BBBBBB',
            vehicleControlSinister: 'BBBBBB',
            vehicleControlPlate: 'BBBBBB',
            vehicleControlAmount: 1,
            vehicleControlMaximumDeliveryDate: currentDate.format(DATE_FORMAT),
            vehicleControlCollectionDeliveryForecast: currentDate.format(DATE_FORMAT),
            vehicleControlDateCollected: currentDate.format(DATE_FORMAT),
          },
          new VehicleControls()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            vehicleControlDate: currentDate,
            vehicleControlMaximumDeliveryDate: currentDate,
            vehicleControlCollectionForecast: currentDate,
            vehicleControlCollectionDeliveryForecast: currentDate,
            vehicleControlDateCollected: currentDate,
            vehicleControlDeliveryDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of VehicleControls', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            vehicleControlAuthorizedOrder: 'BBBBBB',
            vehicleControlRequest: 'BBBBBB',
            vehicleControlSinister: 'BBBBBB',
            vehicleControlDate: currentDate.format(DATE_FORMAT),
            vehicleControlKm: 1,
            vehicleControlPlate: 'BBBBBB',
            vehicleControlAmount: 1,
            vehicleControlPrice: 1,
            vehicleControlMaximumDeliveryDate: currentDate.format(DATE_FORMAT),
            vehicleControlCollectionForecast: currentDate.format(DATE_FORMAT),
            vehicleControlCollectionDeliveryForecast: currentDate.format(DATE_FORMAT),
            vehicleControlDateCollected: currentDate.format(DATE_FORMAT),
            vehicleControlDeliveryDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleControlDate: currentDate,
            vehicleControlMaximumDeliveryDate: currentDate,
            vehicleControlCollectionForecast: currentDate,
            vehicleControlCollectionDeliveryForecast: currentDate,
            vehicleControlDateCollected: currentDate,
            vehicleControlDeliveryDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a VehicleControls', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addVehicleControlsToCollectionIfMissing', () => {
        it('should add a VehicleControls to an empty array', () => {
          const vehicleControls: IVehicleControls = { id: 123 };
          expectedResult = service.addVehicleControlsToCollectionIfMissing([], vehicleControls);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(vehicleControls);
        });

        it('should not add a VehicleControls to an array that contains it', () => {
          const vehicleControls: IVehicleControls = { id: 123 };
          const vehicleControlsCollection: IVehicleControls[] = [
            {
              ...vehicleControls,
            },
            { id: 456 },
          ];
          expectedResult = service.addVehicleControlsToCollectionIfMissing(vehicleControlsCollection, vehicleControls);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a VehicleControls to an array that doesn't contain it", () => {
          const vehicleControls: IVehicleControls = { id: 123 };
          const vehicleControlsCollection: IVehicleControls[] = [{ id: 456 }];
          expectedResult = service.addVehicleControlsToCollectionIfMissing(vehicleControlsCollection, vehicleControls);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(vehicleControls);
        });

        it('should add only unique VehicleControls to an array', () => {
          const vehicleControlsArray: IVehicleControls[] = [{ id: 123 }, { id: 456 }, { id: 89364 }];
          const vehicleControlsCollection: IVehicleControls[] = [{ id: 123 }];
          expectedResult = service.addVehicleControlsToCollectionIfMissing(vehicleControlsCollection, ...vehicleControlsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const vehicleControls: IVehicleControls = { id: 123 };
          const vehicleControls2: IVehicleControls = { id: 456 };
          expectedResult = service.addVehicleControlsToCollectionIfMissing([], vehicleControls, vehicleControls2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(vehicleControls);
          expect(expectedResult).toContain(vehicleControls2);
        });

        it('should accept null and undefined values', () => {
          const vehicleControls: IVehicleControls = { id: 123 };
          expectedResult = service.addVehicleControlsToCollectionIfMissing([], null, vehicleControls, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(vehicleControls);
        });

        it('should return initial array if no VehicleControls is added', () => {
          const vehicleControlsCollection: IVehicleControls[] = [{ id: 123 }];
          expectedResult = service.addVehicleControlsToCollectionIfMissing(vehicleControlsCollection, undefined, null);
          expect(expectedResult).toEqual(vehicleControlsCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
