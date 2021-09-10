import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ParkingSpaceStatus } from 'app/entities/enumerations/parking-space-status.model';
import { IParkingSectorSpace, ParkingSectorSpace } from '../parking-sector-space.model';

import { ParkingSectorSpaceService } from './parking-sector-space.service';

describe('Service Tests', () => {
  describe('ParkingSectorSpace Service', () => {
    let service: ParkingSectorSpaceService;
    let httpMock: HttpTestingController;
    let elemDefault: IParkingSectorSpace;
    let expectedResult: IParkingSectorSpace | IParkingSectorSpace[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ParkingSectorSpaceService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        parkingNumber: 0,
        parkingStatus: ParkingSpaceStatus.Free,
        parkingEntryDate: currentDate,
        parkingDepartureDate: currentDate,
        parkingHousingItemId: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            parkingEntryDate: currentDate.format(DATE_FORMAT),
            parkingDepartureDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ParkingSectorSpace', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            parkingEntryDate: currentDate.format(DATE_FORMAT),
            parkingDepartureDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            parkingEntryDate: currentDate,
            parkingDepartureDate: currentDate,
          },
          returnedFromService
        );

        service.create(new ParkingSectorSpace()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ParkingSectorSpace', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            parkingNumber: 1,
            parkingStatus: 'BBBBBB',
            parkingEntryDate: currentDate.format(DATE_FORMAT),
            parkingDepartureDate: currentDate.format(DATE_FORMAT),
            parkingHousingItemId: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            parkingEntryDate: currentDate,
            parkingDepartureDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ParkingSectorSpace', () => {
        const patchObject = Object.assign(
          {
            parkingDepartureDate: currentDate.format(DATE_FORMAT),
          },
          new ParkingSectorSpace()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            parkingEntryDate: currentDate,
            parkingDepartureDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ParkingSectorSpace', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            parkingNumber: 1,
            parkingStatus: 'BBBBBB',
            parkingEntryDate: currentDate.format(DATE_FORMAT),
            parkingDepartureDate: currentDate.format(DATE_FORMAT),
            parkingHousingItemId: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            parkingEntryDate: currentDate,
            parkingDepartureDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ParkingSectorSpace', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addParkingSectorSpaceToCollectionIfMissing', () => {
        it('should add a ParkingSectorSpace to an empty array', () => {
          const parkingSectorSpace: IParkingSectorSpace = { id: 123 };
          expectedResult = service.addParkingSectorSpaceToCollectionIfMissing([], parkingSectorSpace);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(parkingSectorSpace);
        });

        it('should not add a ParkingSectorSpace to an array that contains it', () => {
          const parkingSectorSpace: IParkingSectorSpace = { id: 123 };
          const parkingSectorSpaceCollection: IParkingSectorSpace[] = [
            {
              ...parkingSectorSpace,
            },
            { id: 456 },
          ];
          expectedResult = service.addParkingSectorSpaceToCollectionIfMissing(parkingSectorSpaceCollection, parkingSectorSpace);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ParkingSectorSpace to an array that doesn't contain it", () => {
          const parkingSectorSpace: IParkingSectorSpace = { id: 123 };
          const parkingSectorSpaceCollection: IParkingSectorSpace[] = [{ id: 456 }];
          expectedResult = service.addParkingSectorSpaceToCollectionIfMissing(parkingSectorSpaceCollection, parkingSectorSpace);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(parkingSectorSpace);
        });

        it('should add only unique ParkingSectorSpace to an array', () => {
          const parkingSectorSpaceArray: IParkingSectorSpace[] = [{ id: 123 }, { id: 456 }, { id: 54280 }];
          const parkingSectorSpaceCollection: IParkingSectorSpace[] = [{ id: 123 }];
          expectedResult = service.addParkingSectorSpaceToCollectionIfMissing(parkingSectorSpaceCollection, ...parkingSectorSpaceArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const parkingSectorSpace: IParkingSectorSpace = { id: 123 };
          const parkingSectorSpace2: IParkingSectorSpace = { id: 456 };
          expectedResult = service.addParkingSectorSpaceToCollectionIfMissing([], parkingSectorSpace, parkingSectorSpace2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(parkingSectorSpace);
          expect(expectedResult).toContain(parkingSectorSpace2);
        });

        it('should accept null and undefined values', () => {
          const parkingSectorSpace: IParkingSectorSpace = { id: 123 };
          expectedResult = service.addParkingSectorSpaceToCollectionIfMissing([], null, parkingSectorSpace, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(parkingSectorSpace);
        });

        it('should return initial array if no ParkingSectorSpace is added', () => {
          const parkingSectorSpaceCollection: IParkingSectorSpace[] = [{ id: 123 }];
          expectedResult = service.addParkingSectorSpaceToCollectionIfMissing(parkingSectorSpaceCollection, undefined, null);
          expect(expectedResult).toEqual(parkingSectorSpaceCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
