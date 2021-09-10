import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IFees, Fees } from '../fees.model';

import { FeesService } from './fees.service';

describe('Service Tests', () => {
  describe('Fees Service', () => {
    let service: FeesService;
    let httpMock: HttpTestingController;
    let elemDefault: IFees;
    let expectedResult: IFees | IFees[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FeesService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        feeDate: currentDate,
        feeDriverCommission: 0,
        feeFinancialCost: 0,
        feeTaxes: 0,
        feeDescriptions: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            feeDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Fees', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            feeDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            feeDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Fees()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Fees', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            feeDate: currentDate.format(DATE_FORMAT),
            feeDriverCommission: 1,
            feeFinancialCost: 1,
            feeTaxes: 1,
            feeDescriptions: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            feeDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Fees', () => {
        const patchObject = Object.assign(
          {
            feeDate: currentDate.format(DATE_FORMAT),
            feeDriverCommission: 1,
          },
          new Fees()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            feeDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Fees', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            feeDate: currentDate.format(DATE_FORMAT),
            feeDriverCommission: 1,
            feeFinancialCost: 1,
            feeTaxes: 1,
            feeDescriptions: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            feeDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Fees', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addFeesToCollectionIfMissing', () => {
        it('should add a Fees to an empty array', () => {
          const fees: IFees = { id: 123 };
          expectedResult = service.addFeesToCollectionIfMissing([], fees);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(fees);
        });

        it('should not add a Fees to an array that contains it', () => {
          const fees: IFees = { id: 123 };
          const feesCollection: IFees[] = [
            {
              ...fees,
            },
            { id: 456 },
          ];
          expectedResult = service.addFeesToCollectionIfMissing(feesCollection, fees);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Fees to an array that doesn't contain it", () => {
          const fees: IFees = { id: 123 };
          const feesCollection: IFees[] = [{ id: 456 }];
          expectedResult = service.addFeesToCollectionIfMissing(feesCollection, fees);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(fees);
        });

        it('should add only unique Fees to an array', () => {
          const feesArray: IFees[] = [{ id: 123 }, { id: 456 }, { id: 89546 }];
          const feesCollection: IFees[] = [{ id: 123 }];
          expectedResult = service.addFeesToCollectionIfMissing(feesCollection, ...feesArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const fees: IFees = { id: 123 };
          const fees2: IFees = { id: 456 };
          expectedResult = service.addFeesToCollectionIfMissing([], fees, fees2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(fees);
          expect(expectedResult).toContain(fees2);
        });

        it('should accept null and undefined values', () => {
          const fees: IFees = { id: 123 };
          expectedResult = service.addFeesToCollectionIfMissing([], null, fees, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(fees);
        });

        it('should return initial array if no Fees is added', () => {
          const feesCollection: IFees[] = [{ id: 123 }];
          expectedResult = service.addFeesToCollectionIfMissing(feesCollection, undefined, null);
          expect(expectedResult).toEqual(feesCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
