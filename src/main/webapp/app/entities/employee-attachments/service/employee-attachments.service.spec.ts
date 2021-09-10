import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEmployeeAttachments, EmployeeAttachments } from '../employee-attachments.model';

import { EmployeeAttachmentsService } from './employee-attachments.service';

describe('Service Tests', () => {
  describe('EmployeeAttachments Service', () => {
    let service: EmployeeAttachmentsService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmployeeAttachments;
    let expectedResult: IEmployeeAttachments | IEmployeeAttachments[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EmployeeAttachmentsService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        attachImageContentType: 'image/png',
        attachImage: 'AAAAAAA',
        attachUrl: 'AAAAAAA',
        attachDescription: 'AAAAAAA',
        attachedDate: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            attachedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EmployeeAttachments', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            attachedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            attachedDate: currentDate,
          },
          returnedFromService
        );

        service.create(new EmployeeAttachments()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EmployeeAttachments', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            attachImage: 'BBBBBB',
            attachUrl: 'BBBBBB',
            attachDescription: 'BBBBBB',
            attachedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            attachedDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a EmployeeAttachments', () => {
        const patchObject = Object.assign(
          {
            attachImage: 'BBBBBB',
            attachUrl: 'BBBBBB',
            attachDescription: 'BBBBBB',
          },
          new EmployeeAttachments()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            attachedDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EmployeeAttachments', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            attachImage: 'BBBBBB',
            attachUrl: 'BBBBBB',
            attachDescription: 'BBBBBB',
            attachedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            attachedDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EmployeeAttachments', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEmployeeAttachmentsToCollectionIfMissing', () => {
        it('should add a EmployeeAttachments to an empty array', () => {
          const employeeAttachments: IEmployeeAttachments = { id: 123 };
          expectedResult = service.addEmployeeAttachmentsToCollectionIfMissing([], employeeAttachments);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(employeeAttachments);
        });

        it('should not add a EmployeeAttachments to an array that contains it', () => {
          const employeeAttachments: IEmployeeAttachments = { id: 123 };
          const employeeAttachmentsCollection: IEmployeeAttachments[] = [
            {
              ...employeeAttachments,
            },
            { id: 456 },
          ];
          expectedResult = service.addEmployeeAttachmentsToCollectionIfMissing(employeeAttachmentsCollection, employeeAttachments);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a EmployeeAttachments to an array that doesn't contain it", () => {
          const employeeAttachments: IEmployeeAttachments = { id: 123 };
          const employeeAttachmentsCollection: IEmployeeAttachments[] = [{ id: 456 }];
          expectedResult = service.addEmployeeAttachmentsToCollectionIfMissing(employeeAttachmentsCollection, employeeAttachments);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(employeeAttachments);
        });

        it('should add only unique EmployeeAttachments to an array', () => {
          const employeeAttachmentsArray: IEmployeeAttachments[] = [{ id: 123 }, { id: 456 }, { id: 52467 }];
          const employeeAttachmentsCollection: IEmployeeAttachments[] = [{ id: 123 }];
          expectedResult = service.addEmployeeAttachmentsToCollectionIfMissing(employeeAttachmentsCollection, ...employeeAttachmentsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const employeeAttachments: IEmployeeAttachments = { id: 123 };
          const employeeAttachments2: IEmployeeAttachments = { id: 456 };
          expectedResult = service.addEmployeeAttachmentsToCollectionIfMissing([], employeeAttachments, employeeAttachments2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(employeeAttachments);
          expect(expectedResult).toContain(employeeAttachments2);
        });

        it('should accept null and undefined values', () => {
          const employeeAttachments: IEmployeeAttachments = { id: 123 };
          expectedResult = service.addEmployeeAttachmentsToCollectionIfMissing([], null, employeeAttachments, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(employeeAttachments);
        });

        it('should return initial array if no EmployeeAttachments is added', () => {
          const employeeAttachmentsCollection: IEmployeeAttachments[] = [{ id: 123 }];
          expectedResult = service.addEmployeeAttachmentsToCollectionIfMissing(employeeAttachmentsCollection, undefined, null);
          expect(expectedResult).toEqual(employeeAttachmentsCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
