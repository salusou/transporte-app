import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEmployeeComponentsData, EmployeeComponentsData } from '../employee-components-data.model';

import { EmployeeComponentsDataService } from './employee-components-data.service';

describe('Service Tests', () => {
  describe('EmployeeComponentsData Service', () => {
    let service: EmployeeComponentsDataService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmployeeComponentsData;
    let expectedResult: IEmployeeComponentsData | IEmployeeComponentsData[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EmployeeComponentsDataService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        dataInfo: 'AAAAAAA',
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

      it('should create a EmployeeComponentsData', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EmployeeComponentsData()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EmployeeComponentsData', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            dataInfo: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a EmployeeComponentsData', () => {
        const patchObject = Object.assign({}, new EmployeeComponentsData());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EmployeeComponentsData', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            dataInfo: 'BBBBBB',
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

      it('should delete a EmployeeComponentsData', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEmployeeComponentsDataToCollectionIfMissing', () => {
        it('should add a EmployeeComponentsData to an empty array', () => {
          const employeeComponentsData: IEmployeeComponentsData = { id: 123 };
          expectedResult = service.addEmployeeComponentsDataToCollectionIfMissing([], employeeComponentsData);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(employeeComponentsData);
        });

        it('should not add a EmployeeComponentsData to an array that contains it', () => {
          const employeeComponentsData: IEmployeeComponentsData = { id: 123 };
          const employeeComponentsDataCollection: IEmployeeComponentsData[] = [
            {
              ...employeeComponentsData,
            },
            { id: 456 },
          ];
          expectedResult = service.addEmployeeComponentsDataToCollectionIfMissing(employeeComponentsDataCollection, employeeComponentsData);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a EmployeeComponentsData to an array that doesn't contain it", () => {
          const employeeComponentsData: IEmployeeComponentsData = { id: 123 };
          const employeeComponentsDataCollection: IEmployeeComponentsData[] = [{ id: 456 }];
          expectedResult = service.addEmployeeComponentsDataToCollectionIfMissing(employeeComponentsDataCollection, employeeComponentsData);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(employeeComponentsData);
        });

        it('should add only unique EmployeeComponentsData to an array', () => {
          const employeeComponentsDataArray: IEmployeeComponentsData[] = [{ id: 123 }, { id: 456 }, { id: 73616 }];
          const employeeComponentsDataCollection: IEmployeeComponentsData[] = [{ id: 123 }];
          expectedResult = service.addEmployeeComponentsDataToCollectionIfMissing(
            employeeComponentsDataCollection,
            ...employeeComponentsDataArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const employeeComponentsData: IEmployeeComponentsData = { id: 123 };
          const employeeComponentsData2: IEmployeeComponentsData = { id: 456 };
          expectedResult = service.addEmployeeComponentsDataToCollectionIfMissing([], employeeComponentsData, employeeComponentsData2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(employeeComponentsData);
          expect(expectedResult).toContain(employeeComponentsData2);
        });

        it('should accept null and undefined values', () => {
          const employeeComponentsData: IEmployeeComponentsData = { id: 123 };
          expectedResult = service.addEmployeeComponentsDataToCollectionIfMissing([], null, employeeComponentsData, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(employeeComponentsData);
        });

        it('should return initial array if no EmployeeComponentsData is added', () => {
          const employeeComponentsDataCollection: IEmployeeComponentsData[] = [{ id: 123 }];
          expectedResult = service.addEmployeeComponentsDataToCollectionIfMissing(employeeComponentsDataCollection, undefined, null);
          expect(expectedResult).toEqual(employeeComponentsDataCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
