import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICompaniesXUsers, CompaniesXUsers } from '../companies-x-users.model';

import { CompaniesXUsersService } from './companies-x-users.service';

describe('Service Tests', () => {
  describe('CompaniesXUsers Service', () => {
    let service: CompaniesXUsersService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompaniesXUsers;
    let expectedResult: ICompaniesXUsers | ICompaniesXUsers[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CompaniesXUsersService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
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

      it('should create a CompaniesXUsers', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CompaniesXUsers()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CompaniesXUsers', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CompaniesXUsers', () => {
        const patchObject = Object.assign({}, new CompaniesXUsers());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CompaniesXUsers', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
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

      it('should delete a CompaniesXUsers', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCompaniesXUsersToCollectionIfMissing', () => {
        it('should add a CompaniesXUsers to an empty array', () => {
          const companiesXUsers: ICompaniesXUsers = { id: 123 };
          expectedResult = service.addCompaniesXUsersToCollectionIfMissing([], companiesXUsers);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(companiesXUsers);
        });

        it('should not add a CompaniesXUsers to an array that contains it', () => {
          const companiesXUsers: ICompaniesXUsers = { id: 123 };
          const companiesXUsersCollection: ICompaniesXUsers[] = [
            {
              ...companiesXUsers,
            },
            { id: 456 },
          ];
          expectedResult = service.addCompaniesXUsersToCollectionIfMissing(companiesXUsersCollection, companiesXUsers);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CompaniesXUsers to an array that doesn't contain it", () => {
          const companiesXUsers: ICompaniesXUsers = { id: 123 };
          const companiesXUsersCollection: ICompaniesXUsers[] = [{ id: 456 }];
          expectedResult = service.addCompaniesXUsersToCollectionIfMissing(companiesXUsersCollection, companiesXUsers);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(companiesXUsers);
        });

        it('should add only unique CompaniesXUsers to an array', () => {
          const companiesXUsersArray: ICompaniesXUsers[] = [{ id: 123 }, { id: 456 }, { id: 39900 }];
          const companiesXUsersCollection: ICompaniesXUsers[] = [{ id: 123 }];
          expectedResult = service.addCompaniesXUsersToCollectionIfMissing(companiesXUsersCollection, ...companiesXUsersArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const companiesXUsers: ICompaniesXUsers = { id: 123 };
          const companiesXUsers2: ICompaniesXUsers = { id: 456 };
          expectedResult = service.addCompaniesXUsersToCollectionIfMissing([], companiesXUsers, companiesXUsers2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(companiesXUsers);
          expect(expectedResult).toContain(companiesXUsers2);
        });

        it('should accept null and undefined values', () => {
          const companiesXUsers: ICompaniesXUsers = { id: 123 };
          expectedResult = service.addCompaniesXUsersToCollectionIfMissing([], null, companiesXUsers, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(companiesXUsers);
        });

        it('should return initial array if no CompaniesXUsers is added', () => {
          const companiesXUsersCollection: ICompaniesXUsers[] = [{ id: 123 }];
          expectedResult = service.addCompaniesXUsersToCollectionIfMissing(companiesXUsersCollection, undefined, null);
          expect(expectedResult).toEqual(companiesXUsersCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
