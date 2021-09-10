import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IVehicleControlBilling, VehicleControlBilling } from '../vehicle-control-billing.model';

import { VehicleControlBillingService } from './vehicle-control-billing.service';

describe('Service Tests', () => {
  describe('VehicleControlBilling Service', () => {
    let service: VehicleControlBillingService;
    let httpMock: HttpTestingController;
    let elemDefault: IVehicleControlBilling;
    let expectedResult: IVehicleControlBilling | IVehicleControlBilling[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(VehicleControlBillingService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        vehicleControlBillingDate: currentDate,
        vehicleControlBillingExpirationDate: currentDate,
        vehicleControlBillingPaymentDate: currentDate,
        vehicleControlBillingSellerCommission: false,
        vehicleControlBillingDriverCommission: false,
        vehicleControlBillingAmount: 0,
        vehicleControlBillingTotalValue: 0,
        vehicleControlBillingInsuranceDiscount: 0,
        vehicleControlBillingCustomerBank: 'AAAAAAA',
        vehicleControlBillingAnticipate: false,
        vehicleControlBillingManifest: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            vehicleControlBillingDate: currentDate.format(DATE_FORMAT),
            vehicleControlBillingExpirationDate: currentDate.format(DATE_FORMAT),
            vehicleControlBillingPaymentDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a VehicleControlBilling', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            vehicleControlBillingDate: currentDate.format(DATE_FORMAT),
            vehicleControlBillingExpirationDate: currentDate.format(DATE_FORMAT),
            vehicleControlBillingPaymentDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleControlBillingDate: currentDate,
            vehicleControlBillingExpirationDate: currentDate,
            vehicleControlBillingPaymentDate: currentDate,
          },
          returnedFromService
        );

        service.create(new VehicleControlBilling()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a VehicleControlBilling', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            vehicleControlBillingDate: currentDate.format(DATE_FORMAT),
            vehicleControlBillingExpirationDate: currentDate.format(DATE_FORMAT),
            vehicleControlBillingPaymentDate: currentDate.format(DATE_FORMAT),
            vehicleControlBillingSellerCommission: true,
            vehicleControlBillingDriverCommission: true,
            vehicleControlBillingAmount: 1,
            vehicleControlBillingTotalValue: 1,
            vehicleControlBillingInsuranceDiscount: 1,
            vehicleControlBillingCustomerBank: 'BBBBBB',
            vehicleControlBillingAnticipate: true,
            vehicleControlBillingManifest: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleControlBillingDate: currentDate,
            vehicleControlBillingExpirationDate: currentDate,
            vehicleControlBillingPaymentDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a VehicleControlBilling', () => {
        const patchObject = Object.assign(
          {
            vehicleControlBillingDate: currentDate.format(DATE_FORMAT),
            vehicleControlBillingExpirationDate: currentDate.format(DATE_FORMAT),
            vehicleControlBillingDriverCommission: true,
            vehicleControlBillingCustomerBank: 'BBBBBB',
            vehicleControlBillingAnticipate: true,
            vehicleControlBillingManifest: 'BBBBBB',
          },
          new VehicleControlBilling()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            vehicleControlBillingDate: currentDate,
            vehicleControlBillingExpirationDate: currentDate,
            vehicleControlBillingPaymentDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of VehicleControlBilling', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            vehicleControlBillingDate: currentDate.format(DATE_FORMAT),
            vehicleControlBillingExpirationDate: currentDate.format(DATE_FORMAT),
            vehicleControlBillingPaymentDate: currentDate.format(DATE_FORMAT),
            vehicleControlBillingSellerCommission: true,
            vehicleControlBillingDriverCommission: true,
            vehicleControlBillingAmount: 1,
            vehicleControlBillingTotalValue: 1,
            vehicleControlBillingInsuranceDiscount: 1,
            vehicleControlBillingCustomerBank: 'BBBBBB',
            vehicleControlBillingAnticipate: true,
            vehicleControlBillingManifest: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleControlBillingDate: currentDate,
            vehicleControlBillingExpirationDate: currentDate,
            vehicleControlBillingPaymentDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a VehicleControlBilling', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addVehicleControlBillingToCollectionIfMissing', () => {
        it('should add a VehicleControlBilling to an empty array', () => {
          const vehicleControlBilling: IVehicleControlBilling = { id: 123 };
          expectedResult = service.addVehicleControlBillingToCollectionIfMissing([], vehicleControlBilling);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(vehicleControlBilling);
        });

        it('should not add a VehicleControlBilling to an array that contains it', () => {
          const vehicleControlBilling: IVehicleControlBilling = { id: 123 };
          const vehicleControlBillingCollection: IVehicleControlBilling[] = [
            {
              ...vehicleControlBilling,
            },
            { id: 456 },
          ];
          expectedResult = service.addVehicleControlBillingToCollectionIfMissing(vehicleControlBillingCollection, vehicleControlBilling);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a VehicleControlBilling to an array that doesn't contain it", () => {
          const vehicleControlBilling: IVehicleControlBilling = { id: 123 };
          const vehicleControlBillingCollection: IVehicleControlBilling[] = [{ id: 456 }];
          expectedResult = service.addVehicleControlBillingToCollectionIfMissing(vehicleControlBillingCollection, vehicleControlBilling);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(vehicleControlBilling);
        });

        it('should add only unique VehicleControlBilling to an array', () => {
          const vehicleControlBillingArray: IVehicleControlBilling[] = [{ id: 123 }, { id: 456 }, { id: 7764 }];
          const vehicleControlBillingCollection: IVehicleControlBilling[] = [{ id: 123 }];
          expectedResult = service.addVehicleControlBillingToCollectionIfMissing(
            vehicleControlBillingCollection,
            ...vehicleControlBillingArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const vehicleControlBilling: IVehicleControlBilling = { id: 123 };
          const vehicleControlBilling2: IVehicleControlBilling = { id: 456 };
          expectedResult = service.addVehicleControlBillingToCollectionIfMissing([], vehicleControlBilling, vehicleControlBilling2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(vehicleControlBilling);
          expect(expectedResult).toContain(vehicleControlBilling2);
        });

        it('should accept null and undefined values', () => {
          const vehicleControlBilling: IVehicleControlBilling = { id: 123 };
          expectedResult = service.addVehicleControlBillingToCollectionIfMissing([], null, vehicleControlBilling, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(vehicleControlBilling);
        });

        it('should return initial array if no VehicleControlBilling is added', () => {
          const vehicleControlBillingCollection: IVehicleControlBilling[] = [{ id: 123 }];
          expectedResult = service.addVehicleControlBillingToCollectionIfMissing(vehicleControlBillingCollection, undefined, null);
          expect(expectedResult).toEqual(vehicleControlBillingCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
