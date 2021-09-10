import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICustomers, Customers } from '../customers.model';

import { CustomersService } from './customers.service';

describe('Service Tests', () => {
  describe('Customers Service', () => {
    let service: CustomersService;
    let httpMock: HttpTestingController;
    let elemDefault: ICustomers;
    let expectedResult: ICustomers | ICustomers[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CustomersService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        customerName: 'AAAAAAA',
        active: false,
        customerNumber: 'AAAAAAA',
        customerPostalCode: 'AAAAAAA',
        customerAddress: 'AAAAAAA',
        customerAddressComplement: 'AAAAAAA',
        customerAddressNumber: 0,
        customerAddressNeighborhood: 'AAAAAAA',
        customerTelephone: 'AAAAAAA',
        paymentTerm: 0,
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

      it('should create a Customers', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Customers()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Customers', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            customerName: 'BBBBBB',
            active: true,
            customerNumber: 'BBBBBB',
            customerPostalCode: 'BBBBBB',
            customerAddress: 'BBBBBB',
            customerAddressComplement: 'BBBBBB',
            customerAddressNumber: 1,
            customerAddressNeighborhood: 'BBBBBB',
            customerTelephone: 'BBBBBB',
            paymentTerm: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Customers', () => {
        const patchObject = Object.assign(
          {
            active: true,
            customerNumber: 'BBBBBB',
            customerPostalCode: 'BBBBBB',
            customerAddressComplement: 'BBBBBB',
            customerAddressNumber: 1,
            customerAddressNeighborhood: 'BBBBBB',
            customerTelephone: 'BBBBBB',
          },
          new Customers()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Customers', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            customerName: 'BBBBBB',
            active: true,
            customerNumber: 'BBBBBB',
            customerPostalCode: 'BBBBBB',
            customerAddress: 'BBBBBB',
            customerAddressComplement: 'BBBBBB',
            customerAddressNumber: 1,
            customerAddressNeighborhood: 'BBBBBB',
            customerTelephone: 'BBBBBB',
            paymentTerm: 1,
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

      it('should delete a Customers', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCustomersToCollectionIfMissing', () => {
        it('should add a Customers to an empty array', () => {
          const customers: ICustomers = { id: 123 };
          expectedResult = service.addCustomersToCollectionIfMissing([], customers);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(customers);
        });

        it('should not add a Customers to an array that contains it', () => {
          const customers: ICustomers = { id: 123 };
          const customersCollection: ICustomers[] = [
            {
              ...customers,
            },
            { id: 456 },
          ];
          expectedResult = service.addCustomersToCollectionIfMissing(customersCollection, customers);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Customers to an array that doesn't contain it", () => {
          const customers: ICustomers = { id: 123 };
          const customersCollection: ICustomers[] = [{ id: 456 }];
          expectedResult = service.addCustomersToCollectionIfMissing(customersCollection, customers);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(customers);
        });

        it('should add only unique Customers to an array', () => {
          const customersArray: ICustomers[] = [{ id: 123 }, { id: 456 }, { id: 26376 }];
          const customersCollection: ICustomers[] = [{ id: 123 }];
          expectedResult = service.addCustomersToCollectionIfMissing(customersCollection, ...customersArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const customers: ICustomers = { id: 123 };
          const customers2: ICustomers = { id: 456 };
          expectedResult = service.addCustomersToCollectionIfMissing([], customers, customers2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(customers);
          expect(expectedResult).toContain(customers2);
        });

        it('should accept null and undefined values', () => {
          const customers: ICustomers = { id: 123 };
          expectedResult = service.addCustomersToCollectionIfMissing([], null, customers, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(customers);
        });

        it('should return initial array if no Customers is added', () => {
          const customersCollection: ICustomers[] = [{ id: 123 }];
          expectedResult = service.addCustomersToCollectionIfMissing(customersCollection, undefined, null);
          expect(expectedResult).toEqual(customersCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
