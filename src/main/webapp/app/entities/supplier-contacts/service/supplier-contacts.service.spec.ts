import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISupplierContacts, SupplierContacts } from '../supplier-contacts.model';

import { SupplierContactsService } from './supplier-contacts.service';

describe('Service Tests', () => {
  describe('SupplierContacts Service', () => {
    let service: SupplierContactsService;
    let httpMock: HttpTestingController;
    let elemDefault: ISupplierContacts;
    let expectedResult: ISupplierContacts | ISupplierContacts[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(SupplierContactsService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        supplierContactName: 'AAAAAAA',
        supplierContactPhone: 'AAAAAAA',
        supplierContactEmail: 'AAAAAAA',
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

      it('should create a SupplierContacts', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SupplierContacts()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SupplierContacts', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            supplierContactName: 'BBBBBB',
            supplierContactPhone: 'BBBBBB',
            supplierContactEmail: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a SupplierContacts', () => {
        const patchObject = Object.assign(
          {
            supplierContactName: 'BBBBBB',
            supplierContactPhone: 'BBBBBB',
          },
          new SupplierContacts()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SupplierContacts', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            supplierContactName: 'BBBBBB',
            supplierContactPhone: 'BBBBBB',
            supplierContactEmail: 'BBBBBB',
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

      it('should delete a SupplierContacts', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addSupplierContactsToCollectionIfMissing', () => {
        it('should add a SupplierContacts to an empty array', () => {
          const supplierContacts: ISupplierContacts = { id: 123 };
          expectedResult = service.addSupplierContactsToCollectionIfMissing([], supplierContacts);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(supplierContacts);
        });

        it('should not add a SupplierContacts to an array that contains it', () => {
          const supplierContacts: ISupplierContacts = { id: 123 };
          const supplierContactsCollection: ISupplierContacts[] = [
            {
              ...supplierContacts,
            },
            { id: 456 },
          ];
          expectedResult = service.addSupplierContactsToCollectionIfMissing(supplierContactsCollection, supplierContacts);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a SupplierContacts to an array that doesn't contain it", () => {
          const supplierContacts: ISupplierContacts = { id: 123 };
          const supplierContactsCollection: ISupplierContacts[] = [{ id: 456 }];
          expectedResult = service.addSupplierContactsToCollectionIfMissing(supplierContactsCollection, supplierContacts);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(supplierContacts);
        });

        it('should add only unique SupplierContacts to an array', () => {
          const supplierContactsArray: ISupplierContacts[] = [{ id: 123 }, { id: 456 }, { id: 18202 }];
          const supplierContactsCollection: ISupplierContacts[] = [{ id: 123 }];
          expectedResult = service.addSupplierContactsToCollectionIfMissing(supplierContactsCollection, ...supplierContactsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const supplierContacts: ISupplierContacts = { id: 123 };
          const supplierContacts2: ISupplierContacts = { id: 456 };
          expectedResult = service.addSupplierContactsToCollectionIfMissing([], supplierContacts, supplierContacts2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(supplierContacts);
          expect(expectedResult).toContain(supplierContacts2);
        });

        it('should accept null and undefined values', () => {
          const supplierContacts: ISupplierContacts = { id: 123 };
          expectedResult = service.addSupplierContactsToCollectionIfMissing([], null, supplierContacts, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(supplierContacts);
        });

        it('should return initial array if no SupplierContacts is added', () => {
          const supplierContactsCollection: ISupplierContacts[] = [{ id: 123 }];
          expectedResult = service.addSupplierContactsToCollectionIfMissing(supplierContactsCollection, undefined, null);
          expect(expectedResult).toEqual(supplierContactsCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
