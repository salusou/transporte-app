import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISupplierBanksInfo, SupplierBanksInfo } from '../supplier-banks-info.model';

import { SupplierBanksInfoService } from './supplier-banks-info.service';

describe('Service Tests', () => {
  describe('SupplierBanksInfo Service', () => {
    let service: SupplierBanksInfoService;
    let httpMock: HttpTestingController;
    let elemDefault: ISupplierBanksInfo;
    let expectedResult: ISupplierBanksInfo | ISupplierBanksInfo[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(SupplierBanksInfoService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        supplierBankCode: 0,
        supplierBankName: 'AAAAAAA',
        supplierBankBranchCode: 'AAAAAAA',
        supplierBankAccountNumber: 'AAAAAAA',
        supplierBankUserName: 'AAAAAAA',
        supplierBankPixKey: 'AAAAAAA',
        supplierBankUserNumber: 'AAAAAAA',
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

      it('should create a SupplierBanksInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SupplierBanksInfo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SupplierBanksInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            supplierBankCode: 1,
            supplierBankName: 'BBBBBB',
            supplierBankBranchCode: 'BBBBBB',
            supplierBankAccountNumber: 'BBBBBB',
            supplierBankUserName: 'BBBBBB',
            supplierBankPixKey: 'BBBBBB',
            supplierBankUserNumber: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a SupplierBanksInfo', () => {
        const patchObject = Object.assign(
          {
            supplierBankName: 'BBBBBB',
            supplierBankBranchCode: 'BBBBBB',
            supplierBankPixKey: 'BBBBBB',
          },
          new SupplierBanksInfo()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SupplierBanksInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            supplierBankCode: 1,
            supplierBankName: 'BBBBBB',
            supplierBankBranchCode: 'BBBBBB',
            supplierBankAccountNumber: 'BBBBBB',
            supplierBankUserName: 'BBBBBB',
            supplierBankPixKey: 'BBBBBB',
            supplierBankUserNumber: 'BBBBBB',
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

      it('should delete a SupplierBanksInfo', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addSupplierBanksInfoToCollectionIfMissing', () => {
        it('should add a SupplierBanksInfo to an empty array', () => {
          const supplierBanksInfo: ISupplierBanksInfo = { id: 123 };
          expectedResult = service.addSupplierBanksInfoToCollectionIfMissing([], supplierBanksInfo);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(supplierBanksInfo);
        });

        it('should not add a SupplierBanksInfo to an array that contains it', () => {
          const supplierBanksInfo: ISupplierBanksInfo = { id: 123 };
          const supplierBanksInfoCollection: ISupplierBanksInfo[] = [
            {
              ...supplierBanksInfo,
            },
            { id: 456 },
          ];
          expectedResult = service.addSupplierBanksInfoToCollectionIfMissing(supplierBanksInfoCollection, supplierBanksInfo);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a SupplierBanksInfo to an array that doesn't contain it", () => {
          const supplierBanksInfo: ISupplierBanksInfo = { id: 123 };
          const supplierBanksInfoCollection: ISupplierBanksInfo[] = [{ id: 456 }];
          expectedResult = service.addSupplierBanksInfoToCollectionIfMissing(supplierBanksInfoCollection, supplierBanksInfo);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(supplierBanksInfo);
        });

        it('should add only unique SupplierBanksInfo to an array', () => {
          const supplierBanksInfoArray: ISupplierBanksInfo[] = [{ id: 123 }, { id: 456 }, { id: 1612 }];
          const supplierBanksInfoCollection: ISupplierBanksInfo[] = [{ id: 123 }];
          expectedResult = service.addSupplierBanksInfoToCollectionIfMissing(supplierBanksInfoCollection, ...supplierBanksInfoArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const supplierBanksInfo: ISupplierBanksInfo = { id: 123 };
          const supplierBanksInfo2: ISupplierBanksInfo = { id: 456 };
          expectedResult = service.addSupplierBanksInfoToCollectionIfMissing([], supplierBanksInfo, supplierBanksInfo2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(supplierBanksInfo);
          expect(expectedResult).toContain(supplierBanksInfo2);
        });

        it('should accept null and undefined values', () => {
          const supplierBanksInfo: ISupplierBanksInfo = { id: 123 };
          expectedResult = service.addSupplierBanksInfoToCollectionIfMissing([], null, supplierBanksInfo, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(supplierBanksInfo);
        });

        it('should return initial array if no SupplierBanksInfo is added', () => {
          const supplierBanksInfoCollection: ISupplierBanksInfo[] = [{ id: 123 }];
          expectedResult = service.addSupplierBanksInfoToCollectionIfMissing(supplierBanksInfoCollection, undefined, null);
          expect(expectedResult).toEqual(supplierBanksInfoCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
