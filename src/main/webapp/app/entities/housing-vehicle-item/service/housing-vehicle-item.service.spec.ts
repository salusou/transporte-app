import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { StatusType } from 'app/entities/enumerations/status-type.model';
import { VehicleType } from 'app/entities/enumerations/vehicle-type.model';
import { IHousingVehicleItem, HousingVehicleItem } from '../housing-vehicle-item.model';

import { HousingVehicleItemService } from './housing-vehicle-item.service';

describe('Service Tests', () => {
  describe('HousingVehicleItem Service', () => {
    let service: HousingVehicleItemService;
    let httpMock: HttpTestingController;
    let elemDefault: IHousingVehicleItem;
    let expectedResult: IHousingVehicleItem | IHousingVehicleItem[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(HousingVehicleItemService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        housingVehicleItemStatus: StatusType.APPROVED,
        housingVehicleItemPlate: 'AAAAAAA',
        housingVehicleItemType: VehicleType.MOTORBIKE,
        housingVehicleItemFipeCode: 'AAAAAAA',
        housingVehicleItemYear: 'AAAAAAA',
        housingVehicleItemFuel: 'AAAAAAA',
        housingVehicleItemBranch: 'AAAAAAA',
        housingVehicleItemModel: 'AAAAAAA',
        housingVehicleItemFuelAbbreviation: 'AAAAAAA',
        housingVehicleItemReferenceMonth: 'AAAAAAA',
        housingVehicleItemValue: 0,
        housingVehicleItemShippingValue: 0,
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

      it('should create a HousingVehicleItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new HousingVehicleItem()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a HousingVehicleItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            housingVehicleItemStatus: 'BBBBBB',
            housingVehicleItemPlate: 'BBBBBB',
            housingVehicleItemType: 'BBBBBB',
            housingVehicleItemFipeCode: 'BBBBBB',
            housingVehicleItemYear: 'BBBBBB',
            housingVehicleItemFuel: 'BBBBBB',
            housingVehicleItemBranch: 'BBBBBB',
            housingVehicleItemModel: 'BBBBBB',
            housingVehicleItemFuelAbbreviation: 'BBBBBB',
            housingVehicleItemReferenceMonth: 'BBBBBB',
            housingVehicleItemValue: 1,
            housingVehicleItemShippingValue: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a HousingVehicleItem', () => {
        const patchObject = Object.assign(
          {
            housingVehicleItemStatus: 'BBBBBB',
            housingVehicleItemType: 'BBBBBB',
            housingVehicleItemFipeCode: 'BBBBBB',
            housingVehicleItemBranch: 'BBBBBB',
            housingVehicleItemModel: 'BBBBBB',
            housingVehicleItemFuelAbbreviation: 'BBBBBB',
            housingVehicleItemReferenceMonth: 'BBBBBB',
          },
          new HousingVehicleItem()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of HousingVehicleItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            housingVehicleItemStatus: 'BBBBBB',
            housingVehicleItemPlate: 'BBBBBB',
            housingVehicleItemType: 'BBBBBB',
            housingVehicleItemFipeCode: 'BBBBBB',
            housingVehicleItemYear: 'BBBBBB',
            housingVehicleItemFuel: 'BBBBBB',
            housingVehicleItemBranch: 'BBBBBB',
            housingVehicleItemModel: 'BBBBBB',
            housingVehicleItemFuelAbbreviation: 'BBBBBB',
            housingVehicleItemReferenceMonth: 'BBBBBB',
            housingVehicleItemValue: 1,
            housingVehicleItemShippingValue: 1,
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

      it('should delete a HousingVehicleItem', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addHousingVehicleItemToCollectionIfMissing', () => {
        it('should add a HousingVehicleItem to an empty array', () => {
          const housingVehicleItem: IHousingVehicleItem = { id: 123 };
          expectedResult = service.addHousingVehicleItemToCollectionIfMissing([], housingVehicleItem);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(housingVehicleItem);
        });

        it('should not add a HousingVehicleItem to an array that contains it', () => {
          const housingVehicleItem: IHousingVehicleItem = { id: 123 };
          const housingVehicleItemCollection: IHousingVehicleItem[] = [
            {
              ...housingVehicleItem,
            },
            { id: 456 },
          ];
          expectedResult = service.addHousingVehicleItemToCollectionIfMissing(housingVehicleItemCollection, housingVehicleItem);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a HousingVehicleItem to an array that doesn't contain it", () => {
          const housingVehicleItem: IHousingVehicleItem = { id: 123 };
          const housingVehicleItemCollection: IHousingVehicleItem[] = [{ id: 456 }];
          expectedResult = service.addHousingVehicleItemToCollectionIfMissing(housingVehicleItemCollection, housingVehicleItem);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(housingVehicleItem);
        });

        it('should add only unique HousingVehicleItem to an array', () => {
          const housingVehicleItemArray: IHousingVehicleItem[] = [{ id: 123 }, { id: 456 }, { id: 97305 }];
          const housingVehicleItemCollection: IHousingVehicleItem[] = [{ id: 123 }];
          expectedResult = service.addHousingVehicleItemToCollectionIfMissing(housingVehicleItemCollection, ...housingVehicleItemArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const housingVehicleItem: IHousingVehicleItem = { id: 123 };
          const housingVehicleItem2: IHousingVehicleItem = { id: 456 };
          expectedResult = service.addHousingVehicleItemToCollectionIfMissing([], housingVehicleItem, housingVehicleItem2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(housingVehicleItem);
          expect(expectedResult).toContain(housingVehicleItem2);
        });

        it('should accept null and undefined values', () => {
          const housingVehicleItem: IHousingVehicleItem = { id: 123 };
          expectedResult = service.addHousingVehicleItemToCollectionIfMissing([], null, housingVehicleItem, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(housingVehicleItem);
        });

        it('should return initial array if no HousingVehicleItem is added', () => {
          const housingVehicleItemCollection: IHousingVehicleItem[] = [{ id: 123 }];
          expectedResult = service.addHousingVehicleItemToCollectionIfMissing(housingVehicleItemCollection, undefined, null);
          expect(expectedResult).toEqual(housingVehicleItemCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
