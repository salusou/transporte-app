import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IInsurances, Insurances } from '../insurances.model';

import { InsurancesService } from './insurances.service';

describe('Service Tests', () => {
  describe('Insurances Service', () => {
    let service: InsurancesService;
    let httpMock: HttpTestingController;
    let elemDefault: IInsurances;
    let expectedResult: IInsurances | IInsurances[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(InsurancesService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        insurancesPercent: 0,
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

      it('should create a Insurances', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Insurances()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Insurances', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            insurancesPercent: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Insurances', () => {
        const patchObject = Object.assign({}, new Insurances());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Insurances', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            insurancesPercent: 1,
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

      it('should delete a Insurances', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addInsurancesToCollectionIfMissing', () => {
        it('should add a Insurances to an empty array', () => {
          const insurances: IInsurances = { id: 123 };
          expectedResult = service.addInsurancesToCollectionIfMissing([], insurances);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(insurances);
        });

        it('should not add a Insurances to an array that contains it', () => {
          const insurances: IInsurances = { id: 123 };
          const insurancesCollection: IInsurances[] = [
            {
              ...insurances,
            },
            { id: 456 },
          ];
          expectedResult = service.addInsurancesToCollectionIfMissing(insurancesCollection, insurances);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Insurances to an array that doesn't contain it", () => {
          const insurances: IInsurances = { id: 123 };
          const insurancesCollection: IInsurances[] = [{ id: 456 }];
          expectedResult = service.addInsurancesToCollectionIfMissing(insurancesCollection, insurances);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(insurances);
        });

        it('should add only unique Insurances to an array', () => {
          const insurancesArray: IInsurances[] = [{ id: 123 }, { id: 456 }, { id: 50698 }];
          const insurancesCollection: IInsurances[] = [{ id: 123 }];
          expectedResult = service.addInsurancesToCollectionIfMissing(insurancesCollection, ...insurancesArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const insurances: IInsurances = { id: 123 };
          const insurances2: IInsurances = { id: 456 };
          expectedResult = service.addInsurancesToCollectionIfMissing([], insurances, insurances2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(insurances);
          expect(expectedResult).toContain(insurances2);
        });

        it('should accept null and undefined values', () => {
          const insurances: IInsurances = { id: 123 };
          expectedResult = service.addInsurancesToCollectionIfMissing([], null, insurances, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(insurances);
        });

        it('should return initial array if no Insurances is added', () => {
          const insurancesCollection: IInsurances[] = [{ id: 123 }];
          expectedResult = service.addInsurancesToCollectionIfMissing(insurancesCollection, undefined, null);
          expect(expectedResult).toEqual(insurancesCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
