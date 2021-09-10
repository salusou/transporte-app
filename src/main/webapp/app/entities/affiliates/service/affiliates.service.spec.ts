import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAffiliates, Affiliates } from '../affiliates.model';

import { AffiliatesService } from './affiliates.service';

describe('Service Tests', () => {
  describe('Affiliates Service', () => {
    let service: AffiliatesService;
    let httpMock: HttpTestingController;
    let elemDefault: IAffiliates;
    let expectedResult: IAffiliates | IAffiliates[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AffiliatesService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        branchName: 'AAAAAAA',
        branchNumber: 'AAAAAAA',
        useSameCompanyAddress: false,
        branchPostalCode: 'AAAAAAA',
        branchAddress: 'AAAAAAA',
        branchAddressComplement: 'AAAAAAA',
        branchAddressNumber: 0,
        branchAddressNeighborhood: 'AAAAAAA',
        branchTelephone: 'AAAAAAA',
        branchEmail: 'AAAAAAA',
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

      it('should create a Affiliates', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Affiliates()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Affiliates', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            branchName: 'BBBBBB',
            branchNumber: 'BBBBBB',
            useSameCompanyAddress: true,
            branchPostalCode: 'BBBBBB',
            branchAddress: 'BBBBBB',
            branchAddressComplement: 'BBBBBB',
            branchAddressNumber: 1,
            branchAddressNeighborhood: 'BBBBBB',
            branchTelephone: 'BBBBBB',
            branchEmail: 'BBBBBB',
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

      it('should partial update a Affiliates', () => {
        const patchObject = Object.assign(
          {
            branchNumber: 'BBBBBB',
            branchAddress: 'BBBBBB',
            branchAddressNeighborhood: 'BBBBBB',
            branchTelephone: 'BBBBBB',
            responsibleContact: 'BBBBBB',
          },
          new Affiliates()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Affiliates', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            branchName: 'BBBBBB',
            branchNumber: 'BBBBBB',
            useSameCompanyAddress: true,
            branchPostalCode: 'BBBBBB',
            branchAddress: 'BBBBBB',
            branchAddressComplement: 'BBBBBB',
            branchAddressNumber: 1,
            branchAddressNeighborhood: 'BBBBBB',
            branchTelephone: 'BBBBBB',
            branchEmail: 'BBBBBB',
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

      it('should delete a Affiliates', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAffiliatesToCollectionIfMissing', () => {
        it('should add a Affiliates to an empty array', () => {
          const affiliates: IAffiliates = { id: 123 };
          expectedResult = service.addAffiliatesToCollectionIfMissing([], affiliates);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(affiliates);
        });

        it('should not add a Affiliates to an array that contains it', () => {
          const affiliates: IAffiliates = { id: 123 };
          const affiliatesCollection: IAffiliates[] = [
            {
              ...affiliates,
            },
            { id: 456 },
          ];
          expectedResult = service.addAffiliatesToCollectionIfMissing(affiliatesCollection, affiliates);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Affiliates to an array that doesn't contain it", () => {
          const affiliates: IAffiliates = { id: 123 };
          const affiliatesCollection: IAffiliates[] = [{ id: 456 }];
          expectedResult = service.addAffiliatesToCollectionIfMissing(affiliatesCollection, affiliates);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(affiliates);
        });

        it('should add only unique Affiliates to an array', () => {
          const affiliatesArray: IAffiliates[] = [{ id: 123 }, { id: 456 }, { id: 63379 }];
          const affiliatesCollection: IAffiliates[] = [{ id: 123 }];
          expectedResult = service.addAffiliatesToCollectionIfMissing(affiliatesCollection, ...affiliatesArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const affiliates: IAffiliates = { id: 123 };
          const affiliates2: IAffiliates = { id: 456 };
          expectedResult = service.addAffiliatesToCollectionIfMissing([], affiliates, affiliates2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(affiliates);
          expect(expectedResult).toContain(affiliates2);
        });

        it('should accept null and undefined values', () => {
          const affiliates: IAffiliates = { id: 123 };
          expectedResult = service.addAffiliatesToCollectionIfMissing([], null, affiliates, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(affiliates);
        });

        it('should return initial array if no Affiliates is added', () => {
          const affiliatesCollection: IAffiliates[] = [{ id: 123 }];
          expectedResult = service.addAffiliatesToCollectionIfMissing(affiliatesCollection, undefined, null);
          expect(expectedResult).toEqual(affiliatesCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
