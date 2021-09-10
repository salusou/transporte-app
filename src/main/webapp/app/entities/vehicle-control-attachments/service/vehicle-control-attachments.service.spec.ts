import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IVehicleControlAttachments, VehicleControlAttachments } from '../vehicle-control-attachments.model';

import { VehicleControlAttachmentsService } from './vehicle-control-attachments.service';

describe('Service Tests', () => {
  describe('VehicleControlAttachments Service', () => {
    let service: VehicleControlAttachmentsService;
    let httpMock: HttpTestingController;
    let elemDefault: IVehicleControlAttachments;
    let expectedResult: IVehicleControlAttachments | IVehicleControlAttachments[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(VehicleControlAttachmentsService);
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

      it('should create a VehicleControlAttachments', () => {
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

        service.create(new VehicleControlAttachments()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a VehicleControlAttachments', () => {
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

      it('should partial update a VehicleControlAttachments', () => {
        const patchObject = Object.assign(
          {
            attachUrl: 'BBBBBB',
          },
          new VehicleControlAttachments()
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

      it('should return a list of VehicleControlAttachments', () => {
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

      it('should delete a VehicleControlAttachments', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addVehicleControlAttachmentsToCollectionIfMissing', () => {
        it('should add a VehicleControlAttachments to an empty array', () => {
          const vehicleControlAttachments: IVehicleControlAttachments = { id: 123 };
          expectedResult = service.addVehicleControlAttachmentsToCollectionIfMissing([], vehicleControlAttachments);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(vehicleControlAttachments);
        });

        it('should not add a VehicleControlAttachments to an array that contains it', () => {
          const vehicleControlAttachments: IVehicleControlAttachments = { id: 123 };
          const vehicleControlAttachmentsCollection: IVehicleControlAttachments[] = [
            {
              ...vehicleControlAttachments,
            },
            { id: 456 },
          ];
          expectedResult = service.addVehicleControlAttachmentsToCollectionIfMissing(
            vehicleControlAttachmentsCollection,
            vehicleControlAttachments
          );
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a VehicleControlAttachments to an array that doesn't contain it", () => {
          const vehicleControlAttachments: IVehicleControlAttachments = { id: 123 };
          const vehicleControlAttachmentsCollection: IVehicleControlAttachments[] = [{ id: 456 }];
          expectedResult = service.addVehicleControlAttachmentsToCollectionIfMissing(
            vehicleControlAttachmentsCollection,
            vehicleControlAttachments
          );
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(vehicleControlAttachments);
        });

        it('should add only unique VehicleControlAttachments to an array', () => {
          const vehicleControlAttachmentsArray: IVehicleControlAttachments[] = [{ id: 123 }, { id: 456 }, { id: 44533 }];
          const vehicleControlAttachmentsCollection: IVehicleControlAttachments[] = [{ id: 123 }];
          expectedResult = service.addVehicleControlAttachmentsToCollectionIfMissing(
            vehicleControlAttachmentsCollection,
            ...vehicleControlAttachmentsArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const vehicleControlAttachments: IVehicleControlAttachments = { id: 123 };
          const vehicleControlAttachments2: IVehicleControlAttachments = { id: 456 };
          expectedResult = service.addVehicleControlAttachmentsToCollectionIfMissing(
            [],
            vehicleControlAttachments,
            vehicleControlAttachments2
          );
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(vehicleControlAttachments);
          expect(expectedResult).toContain(vehicleControlAttachments2);
        });

        it('should accept null and undefined values', () => {
          const vehicleControlAttachments: IVehicleControlAttachments = { id: 123 };
          expectedResult = service.addVehicleControlAttachmentsToCollectionIfMissing([], null, vehicleControlAttachments, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(vehicleControlAttachments);
        });

        it('should return initial array if no VehicleControlAttachments is added', () => {
          const vehicleControlAttachmentsCollection: IVehicleControlAttachments[] = [{ id: 123 }];
          expectedResult = service.addVehicleControlAttachmentsToCollectionIfMissing(vehicleControlAttachmentsCollection, undefined, null);
          expect(expectedResult).toEqual(vehicleControlAttachmentsCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
