import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICustomersContacts, CustomersContacts } from '../customers-contacts.model';

import { CustomersContactsService } from './customers-contacts.service';

describe('Service Tests', () => {
  describe('CustomersContacts Service', () => {
    let service: CustomersContactsService;
    let httpMock: HttpTestingController;
    let elemDefault: ICustomersContacts;
    let expectedResult: ICustomersContacts | ICustomersContacts[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CustomersContactsService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        contactName: 'AAAAAAA',
        contactTelephone: 'AAAAAAA',
        contactEmail: 'AAAAAAA',
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

      it('should create a CustomersContacts', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CustomersContacts()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CustomersContacts', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            contactName: 'BBBBBB',
            contactTelephone: 'BBBBBB',
            contactEmail: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CustomersContacts', () => {
        const patchObject = Object.assign(
          {
            contactTelephone: 'BBBBBB',
            contactEmail: 'BBBBBB',
          },
          new CustomersContacts()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CustomersContacts', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            contactName: 'BBBBBB',
            contactTelephone: 'BBBBBB',
            contactEmail: 'BBBBBB',
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

      it('should delete a CustomersContacts', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCustomersContactsToCollectionIfMissing', () => {
        it('should add a CustomersContacts to an empty array', () => {
          const customersContacts: ICustomersContacts = { id: 123 };
          expectedResult = service.addCustomersContactsToCollectionIfMissing([], customersContacts);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(customersContacts);
        });

        it('should not add a CustomersContacts to an array that contains it', () => {
          const customersContacts: ICustomersContacts = { id: 123 };
          const customersContactsCollection: ICustomersContacts[] = [
            {
              ...customersContacts,
            },
            { id: 456 },
          ];
          expectedResult = service.addCustomersContactsToCollectionIfMissing(customersContactsCollection, customersContacts);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CustomersContacts to an array that doesn't contain it", () => {
          const customersContacts: ICustomersContacts = { id: 123 };
          const customersContactsCollection: ICustomersContacts[] = [{ id: 456 }];
          expectedResult = service.addCustomersContactsToCollectionIfMissing(customersContactsCollection, customersContacts);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(customersContacts);
        });

        it('should add only unique CustomersContacts to an array', () => {
          const customersContactsArray: ICustomersContacts[] = [{ id: 123 }, { id: 456 }, { id: 60574 }];
          const customersContactsCollection: ICustomersContacts[] = [{ id: 123 }];
          expectedResult = service.addCustomersContactsToCollectionIfMissing(customersContactsCollection, ...customersContactsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const customersContacts: ICustomersContacts = { id: 123 };
          const customersContacts2: ICustomersContacts = { id: 456 };
          expectedResult = service.addCustomersContactsToCollectionIfMissing([], customersContacts, customersContacts2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(customersContacts);
          expect(expectedResult).toContain(customersContacts2);
        });

        it('should accept null and undefined values', () => {
          const customersContacts: ICustomersContacts = { id: 123 };
          expectedResult = service.addCustomersContactsToCollectionIfMissing([], null, customersContacts, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(customersContacts);
        });

        it('should return initial array if no CustomersContacts is added', () => {
          const customersContactsCollection: ICustomersContacts[] = [{ id: 123 }];
          expectedResult = service.addCustomersContactsToCollectionIfMissing(customersContactsCollection, undefined, null);
          expect(expectedResult).toEqual(customersContactsCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
