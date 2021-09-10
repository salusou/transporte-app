import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISuppliers, Suppliers } from '../suppliers.model';

import { SuppliersService } from './suppliers.service';

describe('Service Tests', () => {
  describe('Suppliers Service', () => {
    let service: SuppliersService;
    let httpMock: HttpTestingController;
    let elemDefault: ISuppliers;
    let expectedResult: ISuppliers | ISuppliers[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(SuppliersService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        supplierName: 'AAAAAAA',
        supplierNumber: 'AAAAAAA',
        supplierPostalCode: 'AAAAAAA',
        supplierAddress: 'AAAAAAA',
        supplierAddressComplement: 'AAAAAAA',
        supplierAddressNumber: 0,
        supplierAddressNeighborhood: 'AAAAAAA',
        supplierTelephone: 'AAAAAAA',
        supplierEmail: 'AAAAAAA',
        supplierContactName: 'AAAAAAA',
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

      it('should create a Suppliers', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Suppliers()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Suppliers', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            supplierName: 'BBBBBB',
            supplierNumber: 'BBBBBB',
            supplierPostalCode: 'BBBBBB',
            supplierAddress: 'BBBBBB',
            supplierAddressComplement: 'BBBBBB',
            supplierAddressNumber: 1,
            supplierAddressNeighborhood: 'BBBBBB',
            supplierTelephone: 'BBBBBB',
            supplierEmail: 'BBBBBB',
            supplierContactName: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Suppliers', () => {
        const patchObject = Object.assign(
          {
            supplierAddress: 'BBBBBB',
            supplierAddressComplement: 'BBBBBB',
            supplierAddressNumber: 1,
            supplierAddressNeighborhood: 'BBBBBB',
            supplierEmail: 'BBBBBB',
            supplierContactName: 'BBBBBB',
          },
          new Suppliers()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Suppliers', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            supplierName: 'BBBBBB',
            supplierNumber: 'BBBBBB',
            supplierPostalCode: 'BBBBBB',
            supplierAddress: 'BBBBBB',
            supplierAddressComplement: 'BBBBBB',
            supplierAddressNumber: 1,
            supplierAddressNeighborhood: 'BBBBBB',
            supplierTelephone: 'BBBBBB',
            supplierEmail: 'BBBBBB',
            supplierContactName: 'BBBBBB',
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

      it('should delete a Suppliers', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addSuppliersToCollectionIfMissing', () => {
        it('should add a Suppliers to an empty array', () => {
          const suppliers: ISuppliers = { id: 123 };
          expectedResult = service.addSuppliersToCollectionIfMissing([], suppliers);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(suppliers);
        });

        it('should not add a Suppliers to an array that contains it', () => {
          const suppliers: ISuppliers = { id: 123 };
          const suppliersCollection: ISuppliers[] = [
            {
              ...suppliers,
            },
            { id: 456 },
          ];
          expectedResult = service.addSuppliersToCollectionIfMissing(suppliersCollection, suppliers);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Suppliers to an array that doesn't contain it", () => {
          const suppliers: ISuppliers = { id: 123 };
          const suppliersCollection: ISuppliers[] = [{ id: 456 }];
          expectedResult = service.addSuppliersToCollectionIfMissing(suppliersCollection, suppliers);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(suppliers);
        });

        it('should add only unique Suppliers to an array', () => {
          const suppliersArray: ISuppliers[] = [{ id: 123 }, { id: 456 }, { id: 41854 }];
          const suppliersCollection: ISuppliers[] = [{ id: 123 }];
          expectedResult = service.addSuppliersToCollectionIfMissing(suppliersCollection, ...suppliersArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const suppliers: ISuppliers = { id: 123 };
          const suppliers2: ISuppliers = { id: 456 };
          expectedResult = service.addSuppliersToCollectionIfMissing([], suppliers, suppliers2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(suppliers);
          expect(expectedResult).toContain(suppliers2);
        });

        it('should accept null and undefined values', () => {
          const suppliers: ISuppliers = { id: 123 };
          expectedResult = service.addSuppliersToCollectionIfMissing([], null, suppliers, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(suppliers);
        });

        it('should return initial array if no Suppliers is added', () => {
          const suppliersCollection: ISuppliers[] = [{ id: 123 }];
          expectedResult = service.addSuppliersToCollectionIfMissing(suppliersCollection, undefined, null);
          expect(expectedResult).toEqual(suppliersCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
