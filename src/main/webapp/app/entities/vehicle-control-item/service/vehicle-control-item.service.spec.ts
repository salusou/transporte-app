import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { StatusType } from 'app/entities/enumerations/status-type.model';
import { VehicleType } from 'app/entities/enumerations/vehicle-type.model';
import { IVehicleControlItem, VehicleControlItem } from '../vehicle-control-item.model';

import { VehicleControlItemService } from './vehicle-control-item.service';

describe('Service Tests', () => {
  describe('VehicleControlItem Service', () => {
    let service: VehicleControlItemService;
    let httpMock: HttpTestingController;
    let elemDefault: IVehicleControlItem;
    let expectedResult: IVehicleControlItem | IVehicleControlItem[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(VehicleControlItemService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        vehicleControlStatus: StatusType.APPROVED,
        vehicleControlItemPlate: 'AAAAAAA',
        vehicleControlItemType: VehicleType.MOTORBIKE,
        vehicleControlItemFipeCode: 'AAAAAAA',
        vehicleControlItemYear: 'AAAAAAA',
        vehicleControlItemFuel: 'AAAAAAA',
        vehicleControlItemBranch: 'AAAAAAA',
        vehicleControlItemModel: 'AAAAAAA',
        vehicleControlItemFuelAbbreviation: 'AAAAAAA',
        vehicleControlItemReferenceMonth: 'AAAAAAA',
        vehicleControlItemValue: 0,
        vehicleControlItemShippingValue: 0,
        vehicleControlItemCTE: 'AAAAAAA',
        vehicleControlItemCTEDate: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            vehicleControlItemCTEDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a VehicleControlItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            vehicleControlItemCTEDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleControlItemCTEDate: currentDate,
          },
          returnedFromService
        );

        service.create(new VehicleControlItem()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a VehicleControlItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            vehicleControlStatus: 'BBBBBB',
            vehicleControlItemPlate: 'BBBBBB',
            vehicleControlItemType: 'BBBBBB',
            vehicleControlItemFipeCode: 'BBBBBB',
            vehicleControlItemYear: 'BBBBBB',
            vehicleControlItemFuel: 'BBBBBB',
            vehicleControlItemBranch: 'BBBBBB',
            vehicleControlItemModel: 'BBBBBB',
            vehicleControlItemFuelAbbreviation: 'BBBBBB',
            vehicleControlItemReferenceMonth: 'BBBBBB',
            vehicleControlItemValue: 1,
            vehicleControlItemShippingValue: 1,
            vehicleControlItemCTE: 'BBBBBB',
            vehicleControlItemCTEDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleControlItemCTEDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a VehicleControlItem', () => {
        const patchObject = Object.assign(
          {
            vehicleControlStatus: 'BBBBBB',
            vehicleControlItemPlate: 'BBBBBB',
            vehicleControlItemType: 'BBBBBB',
            vehicleControlItemYear: 'BBBBBB',
            vehicleControlItemBranch: 'BBBBBB',
            vehicleControlItemModel: 'BBBBBB',
            vehicleControlItemReferenceMonth: 'BBBBBB',
            vehicleControlItemShippingValue: 1,
            vehicleControlItemCTE: 'BBBBBB',
            vehicleControlItemCTEDate: currentDate.format(DATE_FORMAT),
          },
          new VehicleControlItem()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            vehicleControlItemCTEDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of VehicleControlItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            vehicleControlStatus: 'BBBBBB',
            vehicleControlItemPlate: 'BBBBBB',
            vehicleControlItemType: 'BBBBBB',
            vehicleControlItemFipeCode: 'BBBBBB',
            vehicleControlItemYear: 'BBBBBB',
            vehicleControlItemFuel: 'BBBBBB',
            vehicleControlItemBranch: 'BBBBBB',
            vehicleControlItemModel: 'BBBBBB',
            vehicleControlItemFuelAbbreviation: 'BBBBBB',
            vehicleControlItemReferenceMonth: 'BBBBBB',
            vehicleControlItemValue: 1,
            vehicleControlItemShippingValue: 1,
            vehicleControlItemCTE: 'BBBBBB',
            vehicleControlItemCTEDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleControlItemCTEDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a VehicleControlItem', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addVehicleControlItemToCollectionIfMissing', () => {
        it('should add a VehicleControlItem to an empty array', () => {
          const vehicleControlItem: IVehicleControlItem = { id: 123 };
          expectedResult = service.addVehicleControlItemToCollectionIfMissing([], vehicleControlItem);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(vehicleControlItem);
        });

        it('should not add a VehicleControlItem to an array that contains it', () => {
          const vehicleControlItem: IVehicleControlItem = { id: 123 };
          const vehicleControlItemCollection: IVehicleControlItem[] = [
            {
              ...vehicleControlItem,
            },
            { id: 456 },
          ];
          expectedResult = service.addVehicleControlItemToCollectionIfMissing(vehicleControlItemCollection, vehicleControlItem);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a VehicleControlItem to an array that doesn't contain it", () => {
          const vehicleControlItem: IVehicleControlItem = { id: 123 };
          const vehicleControlItemCollection: IVehicleControlItem[] = [{ id: 456 }];
          expectedResult = service.addVehicleControlItemToCollectionIfMissing(vehicleControlItemCollection, vehicleControlItem);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(vehicleControlItem);
        });

        it('should add only unique VehicleControlItem to an array', () => {
          const vehicleControlItemArray: IVehicleControlItem[] = [{ id: 123 }, { id: 456 }, { id: 62781 }];
          const vehicleControlItemCollection: IVehicleControlItem[] = [{ id: 123 }];
          expectedResult = service.addVehicleControlItemToCollectionIfMissing(vehicleControlItemCollection, ...vehicleControlItemArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const vehicleControlItem: IVehicleControlItem = { id: 123 };
          const vehicleControlItem2: IVehicleControlItem = { id: 456 };
          expectedResult = service.addVehicleControlItemToCollectionIfMissing([], vehicleControlItem, vehicleControlItem2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(vehicleControlItem);
          expect(expectedResult).toContain(vehicleControlItem2);
        });

        it('should accept null and undefined values', () => {
          const vehicleControlItem: IVehicleControlItem = { id: 123 };
          expectedResult = service.addVehicleControlItemToCollectionIfMissing([], null, vehicleControlItem, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(vehicleControlItem);
        });

        it('should return initial array if no VehicleControlItem is added', () => {
          const vehicleControlItemCollection: IVehicleControlItem[] = [{ id: 123 }];
          expectedResult = service.addVehicleControlItemToCollectionIfMissing(vehicleControlItemCollection, undefined, null);
          expect(expectedResult).toEqual(vehicleControlItemCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
