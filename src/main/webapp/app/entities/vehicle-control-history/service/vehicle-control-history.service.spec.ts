import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IVehicleControlHistory, VehicleControlHistory } from '../vehicle-control-history.model';

import { VehicleControlHistoryService } from './vehicle-control-history.service';

describe('Service Tests', () => {
  describe('VehicleControlHistory Service', () => {
    let service: VehicleControlHistoryService;
    let httpMock: HttpTestingController;
    let elemDefault: IVehicleControlHistory;
    let expectedResult: IVehicleControlHistory | IVehicleControlHistory[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(VehicleControlHistoryService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        vehicleControlHistoryDate: currentDate,
        vehicleControlHistoryDescription: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            vehicleControlHistoryDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a VehicleControlHistory', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            vehicleControlHistoryDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleControlHistoryDate: currentDate,
          },
          returnedFromService
        );

        service.create(new VehicleControlHistory()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a VehicleControlHistory', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            vehicleControlHistoryDate: currentDate.format(DATE_TIME_FORMAT),
            vehicleControlHistoryDescription: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleControlHistoryDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a VehicleControlHistory', () => {
        const patchObject = Object.assign(
          {
            vehicleControlHistoryDate: currentDate.format(DATE_TIME_FORMAT),
          },
          new VehicleControlHistory()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            vehicleControlHistoryDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of VehicleControlHistory', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            vehicleControlHistoryDate: currentDate.format(DATE_TIME_FORMAT),
            vehicleControlHistoryDescription: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleControlHistoryDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a VehicleControlHistory', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addVehicleControlHistoryToCollectionIfMissing', () => {
        it('should add a VehicleControlHistory to an empty array', () => {
          const vehicleControlHistory: IVehicleControlHistory = { id: 123 };
          expectedResult = service.addVehicleControlHistoryToCollectionIfMissing([], vehicleControlHistory);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(vehicleControlHistory);
        });

        it('should not add a VehicleControlHistory to an array that contains it', () => {
          const vehicleControlHistory: IVehicleControlHistory = { id: 123 };
          const vehicleControlHistoryCollection: IVehicleControlHistory[] = [
            {
              ...vehicleControlHistory,
            },
            { id: 456 },
          ];
          expectedResult = service.addVehicleControlHistoryToCollectionIfMissing(vehicleControlHistoryCollection, vehicleControlHistory);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a VehicleControlHistory to an array that doesn't contain it", () => {
          const vehicleControlHistory: IVehicleControlHistory = { id: 123 };
          const vehicleControlHistoryCollection: IVehicleControlHistory[] = [{ id: 456 }];
          expectedResult = service.addVehicleControlHistoryToCollectionIfMissing(vehicleControlHistoryCollection, vehicleControlHistory);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(vehicleControlHistory);
        });

        it('should add only unique VehicleControlHistory to an array', () => {
          const vehicleControlHistoryArray: IVehicleControlHistory[] = [{ id: 123 }, { id: 456 }, { id: 72225 }];
          const vehicleControlHistoryCollection: IVehicleControlHistory[] = [{ id: 123 }];
          expectedResult = service.addVehicleControlHistoryToCollectionIfMissing(
            vehicleControlHistoryCollection,
            ...vehicleControlHistoryArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const vehicleControlHistory: IVehicleControlHistory = { id: 123 };
          const vehicleControlHistory2: IVehicleControlHistory = { id: 456 };
          expectedResult = service.addVehicleControlHistoryToCollectionIfMissing([], vehicleControlHistory, vehicleControlHistory2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(vehicleControlHistory);
          expect(expectedResult).toContain(vehicleControlHistory2);
        });

        it('should accept null and undefined values', () => {
          const vehicleControlHistory: IVehicleControlHistory = { id: 123 };
          expectedResult = service.addVehicleControlHistoryToCollectionIfMissing([], null, vehicleControlHistory, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(vehicleControlHistory);
        });

        it('should return initial array if no VehicleControlHistory is added', () => {
          const vehicleControlHistoryCollection: IVehicleControlHistory[] = [{ id: 123 }];
          expectedResult = service.addVehicleControlHistoryToCollectionIfMissing(vehicleControlHistoryCollection, undefined, null);
          expect(expectedResult).toEqual(vehicleControlHistoryCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
