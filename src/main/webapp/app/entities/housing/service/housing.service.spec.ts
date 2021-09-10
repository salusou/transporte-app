import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IHousing, Housing } from '../housing.model';

import { HousingService } from './housing.service';

describe('Service Tests', () => {
  describe('Housing Service', () => {
    let service: HousingService;
    let httpMock: HttpTestingController;
    let elemDefault: IHousing;
    let expectedResult: IHousing | IHousing[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(HousingService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        housingDate: currentDate,
        housingEntranceDate: currentDate,
        housingExit: currentDate,
        housingReceiptNumber: 0,
        housingDailyPrice: 0,
        housingDescription: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            housingDate: currentDate.format(DATE_FORMAT),
            housingEntranceDate: currentDate.format(DATE_TIME_FORMAT),
            housingExit: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Housing', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            housingDate: currentDate.format(DATE_FORMAT),
            housingEntranceDate: currentDate.format(DATE_TIME_FORMAT),
            housingExit: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            housingDate: currentDate,
            housingEntranceDate: currentDate,
            housingExit: currentDate,
          },
          returnedFromService
        );

        service.create(new Housing()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Housing', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            housingDate: currentDate.format(DATE_FORMAT),
            housingEntranceDate: currentDate.format(DATE_TIME_FORMAT),
            housingExit: currentDate.format(DATE_TIME_FORMAT),
            housingReceiptNumber: 1,
            housingDailyPrice: 1,
            housingDescription: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            housingDate: currentDate,
            housingEntranceDate: currentDate,
            housingExit: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Housing', () => {
        const patchObject = Object.assign(
          {
            housingDate: currentDate.format(DATE_FORMAT),
            housingEntranceDate: currentDate.format(DATE_TIME_FORMAT),
            housingDailyPrice: 1,
          },
          new Housing()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            housingDate: currentDate,
            housingEntranceDate: currentDate,
            housingExit: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Housing', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            housingDate: currentDate.format(DATE_FORMAT),
            housingEntranceDate: currentDate.format(DATE_TIME_FORMAT),
            housingExit: currentDate.format(DATE_TIME_FORMAT),
            housingReceiptNumber: 1,
            housingDailyPrice: 1,
            housingDescription: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            housingDate: currentDate,
            housingEntranceDate: currentDate,
            housingExit: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Housing', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addHousingToCollectionIfMissing', () => {
        it('should add a Housing to an empty array', () => {
          const housing: IHousing = { id: 123 };
          expectedResult = service.addHousingToCollectionIfMissing([], housing);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(housing);
        });

        it('should not add a Housing to an array that contains it', () => {
          const housing: IHousing = { id: 123 };
          const housingCollection: IHousing[] = [
            {
              ...housing,
            },
            { id: 456 },
          ];
          expectedResult = service.addHousingToCollectionIfMissing(housingCollection, housing);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Housing to an array that doesn't contain it", () => {
          const housing: IHousing = { id: 123 };
          const housingCollection: IHousing[] = [{ id: 456 }];
          expectedResult = service.addHousingToCollectionIfMissing(housingCollection, housing);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(housing);
        });

        it('should add only unique Housing to an array', () => {
          const housingArray: IHousing[] = [{ id: 123 }, { id: 456 }, { id: 84737 }];
          const housingCollection: IHousing[] = [{ id: 123 }];
          expectedResult = service.addHousingToCollectionIfMissing(housingCollection, ...housingArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const housing: IHousing = { id: 123 };
          const housing2: IHousing = { id: 456 };
          expectedResult = service.addHousingToCollectionIfMissing([], housing, housing2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(housing);
          expect(expectedResult).toContain(housing2);
        });

        it('should accept null and undefined values', () => {
          const housing: IHousing = { id: 123 };
          expectedResult = service.addHousingToCollectionIfMissing([], null, housing, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(housing);
        });

        it('should return initial array if no Housing is added', () => {
          const housingCollection: IHousing[] = [{ id: 123 }];
          expectedResult = service.addHousingToCollectionIfMissing(housingCollection, undefined, null);
          expect(expectedResult).toEqual(housingCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
