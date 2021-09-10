import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICompanies, Companies } from '../companies.model';

import { CompaniesService } from './companies.service';

describe('Service Tests', () => {
  describe('Companies Service', () => {
    let service: CompaniesService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompanies;
    let expectedResult: ICompanies | ICompanies[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CompaniesService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        companyName: 'AAAAAAA',
        tradeName: 'AAAAAAA',
        companyNumber: 'AAAAAAA',
        postalCode: 'AAAAAAA',
        companyAddress: 'AAAAAAA',
        companyAddressComplement: 'AAAAAAA',
        companyAddressNumber: 0,
        companyAddressNeighborhood: 'AAAAAAA',
        companyTelephone: 'AAAAAAA',
        companyEmail: 'AAAAAAA',
        responsibleContact: 'AAAAAAA',
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

      it('should create a Companies', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Companies()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Companies', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            companyName: 'BBBBBB',
            tradeName: 'BBBBBB',
            companyNumber: 'BBBBBB',
            postalCode: 'BBBBBB',
            companyAddress: 'BBBBBB',
            companyAddressComplement: 'BBBBBB',
            companyAddressNumber: 1,
            companyAddressNeighborhood: 'BBBBBB',
            companyTelephone: 'BBBBBB',
            companyEmail: 'BBBBBB',
            responsibleContact: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Companies', () => {
        const patchObject = Object.assign(
          {
            companyName: 'BBBBBB',
            tradeName: 'BBBBBB',
            companyNumber: 'BBBBBB',
            companyAddressNumber: 1,
            companyAddressNeighborhood: 'BBBBBB',
            companyTelephone: 'BBBBBB',
            companyEmail: 'BBBBBB',
            responsibleContact: 'BBBBBB',
          },
          new Companies()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Companies', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            companyName: 'BBBBBB',
            tradeName: 'BBBBBB',
            companyNumber: 'BBBBBB',
            postalCode: 'BBBBBB',
            companyAddress: 'BBBBBB',
            companyAddressComplement: 'BBBBBB',
            companyAddressNumber: 1,
            companyAddressNeighborhood: 'BBBBBB',
            companyTelephone: 'BBBBBB',
            companyEmail: 'BBBBBB',
            responsibleContact: 'BBBBBB',
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

      it('should delete a Companies', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCompaniesToCollectionIfMissing', () => {
        it('should add a Companies to an empty array', () => {
          const companies: ICompanies = { id: 123 };
          expectedResult = service.addCompaniesToCollectionIfMissing([], companies);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(companies);
        });

        it('should not add a Companies to an array that contains it', () => {
          const companies: ICompanies = { id: 123 };
          const companiesCollection: ICompanies[] = [
            {
              ...companies,
            },
            { id: 456 },
          ];
          expectedResult = service.addCompaniesToCollectionIfMissing(companiesCollection, companies);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Companies to an array that doesn't contain it", () => {
          const companies: ICompanies = { id: 123 };
          const companiesCollection: ICompanies[] = [{ id: 456 }];
          expectedResult = service.addCompaniesToCollectionIfMissing(companiesCollection, companies);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(companies);
        });

        it('should add only unique Companies to an array', () => {
          const companiesArray: ICompanies[] = [{ id: 123 }, { id: 456 }, { id: 76620 }];
          const companiesCollection: ICompanies[] = [{ id: 123 }];
          expectedResult = service.addCompaniesToCollectionIfMissing(companiesCollection, ...companiesArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const companies: ICompanies = { id: 123 };
          const companies2: ICompanies = { id: 456 };
          expectedResult = service.addCompaniesToCollectionIfMissing([], companies, companies2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(companies);
          expect(expectedResult).toContain(companies2);
        });

        it('should accept null and undefined values', () => {
          const companies: ICompanies = { id: 123 };
          expectedResult = service.addCompaniesToCollectionIfMissing([], null, companies, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(companies);
        });

        it('should return initial array if no Companies is added', () => {
          const companiesCollection: ICompanies[] = [{ id: 123 }];
          expectedResult = service.addCompaniesToCollectionIfMissing(companiesCollection, undefined, null);
          expect(expectedResult).toEqual(companiesCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
