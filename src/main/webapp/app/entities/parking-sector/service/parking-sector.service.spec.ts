import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IParkingSector, ParkingSector } from '../parking-sector.model';

import { ParkingSectorService } from './parking-sector.service';

describe('Service Tests', () => {
  describe('ParkingSector Service', () => {
    let service: ParkingSectorService;
    let httpMock: HttpTestingController;
    let elemDefault: IParkingSector;
    let expectedResult: IParkingSector | IParkingSector[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ParkingSectorService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        active: false,
        sectorName: 'AAAAAAA',
        parkingSpace: 0,
        parkingNumbersBegin: 0,
        parkingNumbersFinal: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ParkingSector', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ParkingSector()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ParkingSector', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            active: true,
            sectorName: 'BBBBBB',
            parkingSpace: 1,
            parkingNumbersBegin: 1,
            parkingNumbersFinal: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ParkingSector', () => {
        const patchObject = Object.assign(
          {
            active: true,
            parkingNumbersBegin: 1,
            parkingNumbersFinal: 1,
          },
          new ParkingSector()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ParkingSector', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            active: true,
            sectorName: 'BBBBBB',
            parkingSpace: 1,
            parkingNumbersBegin: 1,
            parkingNumbersFinal: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ParkingSector', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addParkingSectorToCollectionIfMissing', () => {
        it('should add a ParkingSector to an empty array', () => {
          const parkingSector: IParkingSector = { id: 123 };
          expectedResult = service.addParkingSectorToCollectionIfMissing([], parkingSector);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(parkingSector);
        });

        it('should not add a ParkingSector to an array that contains it', () => {
          const parkingSector: IParkingSector = { id: 123 };
          const parkingSectorCollection: IParkingSector[] = [
            {
              ...parkingSector,
            },
            { id: 456 },
          ];
          expectedResult = service.addParkingSectorToCollectionIfMissing(parkingSectorCollection, parkingSector);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ParkingSector to an array that doesn't contain it", () => {
          const parkingSector: IParkingSector = { id: 123 };
          const parkingSectorCollection: IParkingSector[] = [{ id: 456 }];
          expectedResult = service.addParkingSectorToCollectionIfMissing(parkingSectorCollection, parkingSector);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(parkingSector);
        });

        it('should add only unique ParkingSector to an array', () => {
          const parkingSectorArray: IParkingSector[] = [{ id: 123 }, { id: 456 }, { id: 94058 }];
          const parkingSectorCollection: IParkingSector[] = [{ id: 123 }];
          expectedResult = service.addParkingSectorToCollectionIfMissing(parkingSectorCollection, ...parkingSectorArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const parkingSector: IParkingSector = { id: 123 };
          const parkingSector2: IParkingSector = { id: 456 };
          expectedResult = service.addParkingSectorToCollectionIfMissing([], parkingSector, parkingSector2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(parkingSector);
          expect(expectedResult).toContain(parkingSector2);
        });

        it('should accept null and undefined values', () => {
          const parkingSector: IParkingSector = { id: 123 };
          expectedResult = service.addParkingSectorToCollectionIfMissing([], null, parkingSector, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(parkingSector);
        });

        it('should return initial array if no ParkingSector is added', () => {
          const parkingSectorCollection: IParkingSector[] = [{ id: 123 }];
          expectedResult = service.addParkingSectorToCollectionIfMissing(parkingSectorCollection, undefined, null);
          expect(expectedResult).toEqual(parkingSectorCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
