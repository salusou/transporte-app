import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { AttachmentType } from 'app/entities/enumerations/attachment-type.model';
import { IStatusAttachments, StatusAttachments } from '../status-attachments.model';

import { StatusAttachmentsService } from './status-attachments.service';

describe('Service Tests', () => {
  describe('StatusAttachments Service', () => {
    let service: StatusAttachmentsService;
    let httpMock: HttpTestingController;
    let elemDefault: IStatusAttachments;
    let expectedResult: IStatusAttachments | IStatusAttachments[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(StatusAttachmentsService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        statusName: 'AAAAAAA',
        statusDescriptions: 'AAAAAAA',
        statusObs: 'AAAAAAA',
        attachmentType: AttachmentType.SUPPLIERS,
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

      it('should create a StatusAttachments', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new StatusAttachments()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a StatusAttachments', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            statusName: 'BBBBBB',
            statusDescriptions: 'BBBBBB',
            statusObs: 'BBBBBB',
            attachmentType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a StatusAttachments', () => {
        const patchObject = Object.assign(
          {
            statusObs: 'BBBBBB',
            attachmentType: 'BBBBBB',
          },
          new StatusAttachments()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of StatusAttachments', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            statusName: 'BBBBBB',
            statusDescriptions: 'BBBBBB',
            statusObs: 'BBBBBB',
            attachmentType: 'BBBBBB',
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

      it('should delete a StatusAttachments', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addStatusAttachmentsToCollectionIfMissing', () => {
        it('should add a StatusAttachments to an empty array', () => {
          const statusAttachments: IStatusAttachments = { id: 123 };
          expectedResult = service.addStatusAttachmentsToCollectionIfMissing([], statusAttachments);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(statusAttachments);
        });

        it('should not add a StatusAttachments to an array that contains it', () => {
          const statusAttachments: IStatusAttachments = { id: 123 };
          const statusAttachmentsCollection: IStatusAttachments[] = [
            {
              ...statusAttachments,
            },
            { id: 456 },
          ];
          expectedResult = service.addStatusAttachmentsToCollectionIfMissing(statusAttachmentsCollection, statusAttachments);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a StatusAttachments to an array that doesn't contain it", () => {
          const statusAttachments: IStatusAttachments = { id: 123 };
          const statusAttachmentsCollection: IStatusAttachments[] = [{ id: 456 }];
          expectedResult = service.addStatusAttachmentsToCollectionIfMissing(statusAttachmentsCollection, statusAttachments);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(statusAttachments);
        });

        it('should add only unique StatusAttachments to an array', () => {
          const statusAttachmentsArray: IStatusAttachments[] = [{ id: 123 }, { id: 456 }, { id: 71720 }];
          const statusAttachmentsCollection: IStatusAttachments[] = [{ id: 123 }];
          expectedResult = service.addStatusAttachmentsToCollectionIfMissing(statusAttachmentsCollection, ...statusAttachmentsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const statusAttachments: IStatusAttachments = { id: 123 };
          const statusAttachments2: IStatusAttachments = { id: 456 };
          expectedResult = service.addStatusAttachmentsToCollectionIfMissing([], statusAttachments, statusAttachments2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(statusAttachments);
          expect(expectedResult).toContain(statusAttachments2);
        });

        it('should accept null and undefined values', () => {
          const statusAttachments: IStatusAttachments = { id: 123 };
          expectedResult = service.addStatusAttachmentsToCollectionIfMissing([], null, statusAttachments, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(statusAttachments);
        });

        it('should return initial array if no StatusAttachments is added', () => {
          const statusAttachmentsCollection: IStatusAttachments[] = [{ id: 123 }];
          expectedResult = service.addStatusAttachmentsToCollectionIfMissing(statusAttachmentsCollection, undefined, null);
          expect(expectedResult).toEqual(statusAttachmentsCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
