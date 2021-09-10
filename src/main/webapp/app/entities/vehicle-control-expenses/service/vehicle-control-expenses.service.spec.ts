import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { DriverType } from 'app/entities/enumerations/driver-type.model';
import { IVehicleControlExpenses, VehicleControlExpenses } from '../vehicle-control-expenses.model';

import { VehicleControlExpensesService } from './vehicle-control-expenses.service';

describe('Service Tests', () => {
  describe('VehicleControlExpenses Service', () => {
    let service: VehicleControlExpensesService;
    let httpMock: HttpTestingController;
    let elemDefault: IVehicleControlExpenses;
    let expectedResult: IVehicleControlExpenses | IVehicleControlExpenses[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(VehicleControlExpensesService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        vehicleControlExpensesDescription: 'AAAAAAA',
        vehicleControlExpensesDriverType: DriverType.EXTERNAL,
        vehicleControlExpensesPurchaseOrder: 'AAAAAAA',
        vehicleControlExpensesDueDate: currentDate,
        vehicleControlExpensesPaymentDate: currentDate,
        vehicleControlExpensesBillingTotalValue: 0,
        vehicleControlExpensesDriverCommission: false,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            vehicleControlExpensesDueDate: currentDate.format(DATE_FORMAT),
            vehicleControlExpensesPaymentDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a VehicleControlExpenses', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            vehicleControlExpensesDueDate: currentDate.format(DATE_FORMAT),
            vehicleControlExpensesPaymentDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleControlExpensesDueDate: currentDate,
            vehicleControlExpensesPaymentDate: currentDate,
          },
          returnedFromService
        );

        service.create(new VehicleControlExpenses()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a VehicleControlExpenses', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            vehicleControlExpensesDescription: 'BBBBBB',
            vehicleControlExpensesDriverType: 'BBBBBB',
            vehicleControlExpensesPurchaseOrder: 'BBBBBB',
            vehicleControlExpensesDueDate: currentDate.format(DATE_FORMAT),
            vehicleControlExpensesPaymentDate: currentDate.format(DATE_FORMAT),
            vehicleControlExpensesBillingTotalValue: 1,
            vehicleControlExpensesDriverCommission: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleControlExpensesDueDate: currentDate,
            vehicleControlExpensesPaymentDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a VehicleControlExpenses', () => {
        const patchObject = Object.assign(
          {
            vehicleControlExpensesDescription: 'BBBBBB',
            vehicleControlExpensesPurchaseOrder: 'BBBBBB',
            vehicleControlExpensesDueDate: currentDate.format(DATE_FORMAT),
            vehicleControlExpensesBillingTotalValue: 1,
          },
          new VehicleControlExpenses()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            vehicleControlExpensesDueDate: currentDate,
            vehicleControlExpensesPaymentDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of VehicleControlExpenses', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            vehicleControlExpensesDescription: 'BBBBBB',
            vehicleControlExpensesDriverType: 'BBBBBB',
            vehicleControlExpensesPurchaseOrder: 'BBBBBB',
            vehicleControlExpensesDueDate: currentDate.format(DATE_FORMAT),
            vehicleControlExpensesPaymentDate: currentDate.format(DATE_FORMAT),
            vehicleControlExpensesBillingTotalValue: 1,
            vehicleControlExpensesDriverCommission: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleControlExpensesDueDate: currentDate,
            vehicleControlExpensesPaymentDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a VehicleControlExpenses', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addVehicleControlExpensesToCollectionIfMissing', () => {
        it('should add a VehicleControlExpenses to an empty array', () => {
          const vehicleControlExpenses: IVehicleControlExpenses = { id: 123 };
          expectedResult = service.addVehicleControlExpensesToCollectionIfMissing([], vehicleControlExpenses);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(vehicleControlExpenses);
        });

        it('should not add a VehicleControlExpenses to an array that contains it', () => {
          const vehicleControlExpenses: IVehicleControlExpenses = { id: 123 };
          const vehicleControlExpensesCollection: IVehicleControlExpenses[] = [
            {
              ...vehicleControlExpenses,
            },
            { id: 456 },
          ];
          expectedResult = service.addVehicleControlExpensesToCollectionIfMissing(vehicleControlExpensesCollection, vehicleControlExpenses);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a VehicleControlExpenses to an array that doesn't contain it", () => {
          const vehicleControlExpenses: IVehicleControlExpenses = { id: 123 };
          const vehicleControlExpensesCollection: IVehicleControlExpenses[] = [{ id: 456 }];
          expectedResult = service.addVehicleControlExpensesToCollectionIfMissing(vehicleControlExpensesCollection, vehicleControlExpenses);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(vehicleControlExpenses);
        });

        it('should add only unique VehicleControlExpenses to an array', () => {
          const vehicleControlExpensesArray: IVehicleControlExpenses[] = [{ id: 123 }, { id: 456 }, { id: 34082 }];
          const vehicleControlExpensesCollection: IVehicleControlExpenses[] = [{ id: 123 }];
          expectedResult = service.addVehicleControlExpensesToCollectionIfMissing(
            vehicleControlExpensesCollection,
            ...vehicleControlExpensesArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const vehicleControlExpenses: IVehicleControlExpenses = { id: 123 };
          const vehicleControlExpenses2: IVehicleControlExpenses = { id: 456 };
          expectedResult = service.addVehicleControlExpensesToCollectionIfMissing([], vehicleControlExpenses, vehicleControlExpenses2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(vehicleControlExpenses);
          expect(expectedResult).toContain(vehicleControlExpenses2);
        });

        it('should accept null and undefined values', () => {
          const vehicleControlExpenses: IVehicleControlExpenses = { id: 123 };
          expectedResult = service.addVehicleControlExpensesToCollectionIfMissing([], null, vehicleControlExpenses, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(vehicleControlExpenses);
        });

        it('should return initial array if no VehicleControlExpenses is added', () => {
          const vehicleControlExpensesCollection: IVehicleControlExpenses[] = [{ id: 123 }];
          expectedResult = service.addVehicleControlExpensesToCollectionIfMissing(vehicleControlExpensesCollection, undefined, null);
          expect(expectedResult).toEqual(vehicleControlExpensesCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
