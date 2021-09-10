import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICustomersGroups, CustomersGroups } from '../customers-groups.model';

import { CustomersGroupsService } from './customers-groups.service';

describe('Service Tests', () => {
  describe('CustomersGroups Service', () => {
    let service: CustomersGroupsService;
    let httpMock: HttpTestingController;
    let elemDefault: ICustomersGroups;
    let expectedResult: ICustomersGroups | ICustomersGroups[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CustomersGroupsService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        groupName: 'AAAAAAA',
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

      it('should create a CustomersGroups', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CustomersGroups()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CustomersGroups', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            groupName: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CustomersGroups', () => {
        const patchObject = Object.assign(
          {
            groupName: 'BBBBBB',
          },
          new CustomersGroups()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CustomersGroups', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            groupName: 'BBBBBB',
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

      it('should delete a CustomersGroups', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCustomersGroupsToCollectionIfMissing', () => {
        it('should add a CustomersGroups to an empty array', () => {
          const customersGroups: ICustomersGroups = { id: 123 };
          expectedResult = service.addCustomersGroupsToCollectionIfMissing([], customersGroups);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(customersGroups);
        });

        it('should not add a CustomersGroups to an array that contains it', () => {
          const customersGroups: ICustomersGroups = { id: 123 };
          const customersGroupsCollection: ICustomersGroups[] = [
            {
              ...customersGroups,
            },
            { id: 456 },
          ];
          expectedResult = service.addCustomersGroupsToCollectionIfMissing(customersGroupsCollection, customersGroups);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CustomersGroups to an array that doesn't contain it", () => {
          const customersGroups: ICustomersGroups = { id: 123 };
          const customersGroupsCollection: ICustomersGroups[] = [{ id: 456 }];
          expectedResult = service.addCustomersGroupsToCollectionIfMissing(customersGroupsCollection, customersGroups);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(customersGroups);
        });

        it('should add only unique CustomersGroups to an array', () => {
          const customersGroupsArray: ICustomersGroups[] = [{ id: 123 }, { id: 456 }, { id: 93118 }];
          const customersGroupsCollection: ICustomersGroups[] = [{ id: 123 }];
          expectedResult = service.addCustomersGroupsToCollectionIfMissing(customersGroupsCollection, ...customersGroupsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const customersGroups: ICustomersGroups = { id: 123 };
          const customersGroups2: ICustomersGroups = { id: 456 };
          expectedResult = service.addCustomersGroupsToCollectionIfMissing([], customersGroups, customersGroups2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(customersGroups);
          expect(expectedResult).toContain(customersGroups2);
        });

        it('should accept null and undefined values', () => {
          const customersGroups: ICustomersGroups = { id: 123 };
          expectedResult = service.addCustomersGroupsToCollectionIfMissing([], null, customersGroups, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(customersGroups);
        });

        it('should return initial array if no CustomersGroups is added', () => {
          const customersGroupsCollection: ICustomersGroups[] = [{ id: 123 }];
          expectedResult = service.addCustomersGroupsToCollectionIfMissing(customersGroupsCollection, undefined, null);
          expect(expectedResult).toEqual(customersGroupsCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
