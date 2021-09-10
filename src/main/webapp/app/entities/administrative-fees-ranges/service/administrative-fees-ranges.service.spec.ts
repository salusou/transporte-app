import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAdministrativeFeesRanges, AdministrativeFeesRanges } from '../administrative-fees-ranges.model';

import { AdministrativeFeesRangesService } from './administrative-fees-ranges.service';

describe('Service Tests', () => {
  describe('AdministrativeFeesRanges Service', () => {
    let service: AdministrativeFeesRangesService;
    let httpMock: HttpTestingController;
    let elemDefault: IAdministrativeFeesRanges;
    let expectedResult: IAdministrativeFeesRanges | IAdministrativeFeesRanges[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AdministrativeFeesRangesService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        minRange: 0,
        maxRange: 0,
        aliquot: 0,
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

      it('should create a AdministrativeFeesRanges', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AdministrativeFeesRanges()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AdministrativeFeesRanges', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            minRange: 1,
            maxRange: 1,
            aliquot: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a AdministrativeFeesRanges', () => {
        const patchObject = Object.assign(
          {
            maxRange: 1,
          },
          new AdministrativeFeesRanges()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AdministrativeFeesRanges', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            minRange: 1,
            maxRange: 1,
            aliquot: 1,
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

      it('should delete a AdministrativeFeesRanges', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAdministrativeFeesRangesToCollectionIfMissing', () => {
        it('should add a AdministrativeFeesRanges to an empty array', () => {
          const administrativeFeesRanges: IAdministrativeFeesRanges = { id: 123 };
          expectedResult = service.addAdministrativeFeesRangesToCollectionIfMissing([], administrativeFeesRanges);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(administrativeFeesRanges);
        });

        it('should not add a AdministrativeFeesRanges to an array that contains it', () => {
          const administrativeFeesRanges: IAdministrativeFeesRanges = { id: 123 };
          const administrativeFeesRangesCollection: IAdministrativeFeesRanges[] = [
            {
              ...administrativeFeesRanges,
            },
            { id: 456 },
          ];
          expectedResult = service.addAdministrativeFeesRangesToCollectionIfMissing(
            administrativeFeesRangesCollection,
            administrativeFeesRanges
          );
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a AdministrativeFeesRanges to an array that doesn't contain it", () => {
          const administrativeFeesRanges: IAdministrativeFeesRanges = { id: 123 };
          const administrativeFeesRangesCollection: IAdministrativeFeesRanges[] = [{ id: 456 }];
          expectedResult = service.addAdministrativeFeesRangesToCollectionIfMissing(
            administrativeFeesRangesCollection,
            administrativeFeesRanges
          );
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(administrativeFeesRanges);
        });

        it('should add only unique AdministrativeFeesRanges to an array', () => {
          const administrativeFeesRangesArray: IAdministrativeFeesRanges[] = [{ id: 123 }, { id: 456 }, { id: 92228 }];
          const administrativeFeesRangesCollection: IAdministrativeFeesRanges[] = [{ id: 123 }];
          expectedResult = service.addAdministrativeFeesRangesToCollectionIfMissing(
            administrativeFeesRangesCollection,
            ...administrativeFeesRangesArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const administrativeFeesRanges: IAdministrativeFeesRanges = { id: 123 };
          const administrativeFeesRanges2: IAdministrativeFeesRanges = { id: 456 };
          expectedResult = service.addAdministrativeFeesRangesToCollectionIfMissing(
            [],
            administrativeFeesRanges,
            administrativeFeesRanges2
          );
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(administrativeFeesRanges);
          expect(expectedResult).toContain(administrativeFeesRanges2);
        });

        it('should accept null and undefined values', () => {
          const administrativeFeesRanges: IAdministrativeFeesRanges = { id: 123 };
          expectedResult = service.addAdministrativeFeesRangesToCollectionIfMissing([], null, administrativeFeesRanges, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(administrativeFeesRanges);
        });

        it('should return initial array if no AdministrativeFeesRanges is added', () => {
          const administrativeFeesRangesCollection: IAdministrativeFeesRanges[] = [{ id: 123 }];
          expectedResult = service.addAdministrativeFeesRangesToCollectionIfMissing(administrativeFeesRangesCollection, undefined, null);
          expect(expectedResult).toEqual(administrativeFeesRangesCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
