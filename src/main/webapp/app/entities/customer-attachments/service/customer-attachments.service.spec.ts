import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICustomerAttachments, CustomerAttachments } from '../customer-attachments.model';

import { CustomerAttachmentsService } from './customer-attachments.service';

describe('Service Tests', () => {
  describe('CustomerAttachments Service', () => {
    let service: CustomerAttachmentsService;
    let httpMock: HttpTestingController;
    let elemDefault: ICustomerAttachments;
    let expectedResult: ICustomerAttachments | ICustomerAttachments[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CustomerAttachmentsService);
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

      it('should create a CustomerAttachments', () => {
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

        service.create(new CustomerAttachments()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CustomerAttachments', () => {
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

      it('should partial update a CustomerAttachments', () => {
        const patchObject = Object.assign(
          {
            attachUrl: 'BBBBBB',
            attachedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          new CustomerAttachments()
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

      it('should return a list of CustomerAttachments', () => {
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

      it('should delete a CustomerAttachments', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCustomerAttachmentsToCollectionIfMissing', () => {
        it('should add a CustomerAttachments to an empty array', () => {
          const customerAttachments: ICustomerAttachments = { id: 123 };
          expectedResult = service.addCustomerAttachmentsToCollectionIfMissing([], customerAttachments);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(customerAttachments);
        });

        it('should not add a CustomerAttachments to an array that contains it', () => {
          const customerAttachments: ICustomerAttachments = { id: 123 };
          const customerAttachmentsCollection: ICustomerAttachments[] = [
            {
              ...customerAttachments,
            },
            { id: 456 },
          ];
          expectedResult = service.addCustomerAttachmentsToCollectionIfMissing(customerAttachmentsCollection, customerAttachments);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CustomerAttachments to an array that doesn't contain it", () => {
          const customerAttachments: ICustomerAttachments = { id: 123 };
          const customerAttachmentsCollection: ICustomerAttachments[] = [{ id: 456 }];
          expectedResult = service.addCustomerAttachmentsToCollectionIfMissing(customerAttachmentsCollection, customerAttachments);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(customerAttachments);
        });

        it('should add only unique CustomerAttachments to an array', () => {
          const customerAttachmentsArray: ICustomerAttachments[] = [{ id: 123 }, { id: 456 }, { id: 73892 }];
          const customerAttachmentsCollection: ICustomerAttachments[] = [{ id: 123 }];
          expectedResult = service.addCustomerAttachmentsToCollectionIfMissing(customerAttachmentsCollection, ...customerAttachmentsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const customerAttachments: ICustomerAttachments = { id: 123 };
          const customerAttachments2: ICustomerAttachments = { id: 456 };
          expectedResult = service.addCustomerAttachmentsToCollectionIfMissing([], customerAttachments, customerAttachments2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(customerAttachments);
          expect(expectedResult).toContain(customerAttachments2);
        });

        it('should accept null and undefined values', () => {
          const customerAttachments: ICustomerAttachments = { id: 123 };
          expectedResult = service.addCustomerAttachmentsToCollectionIfMissing([], null, customerAttachments, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(customerAttachments);
        });

        it('should return initial array if no CustomerAttachments is added', () => {
          const customerAttachmentsCollection: ICustomerAttachments[] = [{ id: 123 }];
          expectedResult = service.addCustomerAttachmentsToCollectionIfMissing(customerAttachmentsCollection, undefined, null);
          expect(expectedResult).toEqual(customerAttachmentsCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
