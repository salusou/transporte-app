import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IVehicleLocationStatus, VehicleLocationStatus } from '../vehicle-location-status.model';

import { VehicleLocationStatusService } from './vehicle-location-status.service';

describe('Service Tests', () => {
  describe('VehicleLocationStatus Service', () => {
    let service: VehicleLocationStatusService;
    let httpMock: HttpTestingController;
    let elemDefault: IVehicleLocationStatus;
    let expectedResult: IVehicleLocationStatus | IVehicleLocationStatus[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(VehicleLocationStatusService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        vehicleLocationStatusDate: currentDate,
        vehicleLocationStatusDescription: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            vehicleLocationStatusDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a VehicleLocationStatus', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            vehicleLocationStatusDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleLocationStatusDate: currentDate,
          },
          returnedFromService
        );

        service.create(new VehicleLocationStatus()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a VehicleLocationStatus', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            vehicleLocationStatusDate: currentDate.format(DATE_TIME_FORMAT),
            vehicleLocationStatusDescription: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleLocationStatusDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a VehicleLocationStatus', () => {
        const patchObject = Object.assign(
          {
            vehicleLocationStatusDate: currentDate.format(DATE_TIME_FORMAT),
            vehicleLocationStatusDescription: 'BBBBBB',
          },
          new VehicleLocationStatus()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            vehicleLocationStatusDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of VehicleLocationStatus', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            vehicleLocationStatusDate: currentDate.format(DATE_TIME_FORMAT),
            vehicleLocationStatusDescription: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleLocationStatusDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a VehicleLocationStatus', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addVehicleLocationStatusToCollectionIfMissing', () => {
        it('should add a VehicleLocationStatus to an empty array', () => {
          const vehicleLocationStatus: IVehicleLocationStatus = { id: 123 };
          expectedResult = service.addVehicleLocationStatusToCollectionIfMissing([], vehicleLocationStatus);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(vehicleLocationStatus);
        });

        it('should not add a VehicleLocationStatus to an array that contains it', () => {
          const vehicleLocationStatus: IVehicleLocationStatus = { id: 123 };
          const vehicleLocationStatusCollection: IVehicleLocationStatus[] = [
            {
              ...vehicleLocationStatus,
            },
            { id: 456 },
          ];
          expectedResult = service.addVehicleLocationStatusToCollectionIfMissing(vehicleLocationStatusCollection, vehicleLocationStatus);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a VehicleLocationStatus to an array that doesn't contain it", () => {
          const vehicleLocationStatus: IVehicleLocationStatus = { id: 123 };
          const vehicleLocationStatusCollection: IVehicleLocationStatus[] = [{ id: 456 }];
          expectedResult = service.addVehicleLocationStatusToCollectionIfMissing(vehicleLocationStatusCollection, vehicleLocationStatus);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(vehicleLocationStatus);
        });

        it('should add only unique VehicleLocationStatus to an array', () => {
          const vehicleLocationStatusArray: IVehicleLocationStatus[] = [{ id: 123 }, { id: 456 }, { id: 27692 }];
          const vehicleLocationStatusCollection: IVehicleLocationStatus[] = [{ id: 123 }];
          expectedResult = service.addVehicleLocationStatusToCollectionIfMissing(
            vehicleLocationStatusCollection,
            ...vehicleLocationStatusArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const vehicleLocationStatus: IVehicleLocationStatus = { id: 123 };
          const vehicleLocationStatus2: IVehicleLocationStatus = { id: 456 };
          expectedResult = service.addVehicleLocationStatusToCollectionIfMissing([], vehicleLocationStatus, vehicleLocationStatus2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(vehicleLocationStatus);
          expect(expectedResult).toContain(vehicleLocationStatus2);
        });

        it('should accept null and undefined values', () => {
          const vehicleLocationStatus: IVehicleLocationStatus = { id: 123 };
          expectedResult = service.addVehicleLocationStatusToCollectionIfMissing([], null, vehicleLocationStatus, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(vehicleLocationStatus);
        });

        it('should return initial array if no VehicleLocationStatus is added', () => {
          const vehicleLocationStatusCollection: IVehicleLocationStatus[] = [{ id: 123 }];
          expectedResult = service.addVehicleLocationStatusToCollectionIfMissing(vehicleLocationStatusCollection, undefined, null);
          expect(expectedResult).toEqual(vehicleLocationStatusCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
