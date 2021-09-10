import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { InspectionStatus } from 'app/entities/enumerations/inspection-status.model';
import { FuelLevel } from 'app/entities/enumerations/fuel-level.model';
import { VehicleStatus } from 'app/entities/enumerations/vehicle-status.model';
import { IVehicleInspections, VehicleInspections } from '../vehicle-inspections.model';

import { VehicleInspectionsService } from './vehicle-inspections.service';

describe('Service Tests', () => {
  describe('VehicleInspections Service', () => {
    let service: VehicleInspectionsService;
    let httpMock: HttpTestingController;
    let elemDefault: IVehicleInspections;
    let expectedResult: IVehicleInspections | IVehicleInspections[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(VehicleInspectionsService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        vehicleInspectionDate: currentDate,
        vehicleInspectionStatus: InspectionStatus.ARRIVAL,
        vehicleInspectionModel: 'AAAAAAA',
        vehicleInspectionLicensePlate: 'AAAAAAA',
        vehicleInspectionKm: 0,
        vehicleInspectionLicenseYear: 0,
        vehicleInspectionHasManual: false,
        vehicleInspectionHasExtraKey: false,
        vehicleInspectionHasStickers: false,
        vehicleInspectionGas: FuelLevel.RESERVE,
        vehicleInspectionRearView: false,
        vehicleInspectionHorn: false,
        vehicleInspectionWindshieldWiper: false,
        vehicleInspectionSquirt: false,
        vehicleInspectionInternalLight: VehicleStatus.DIRTY,
        vehicleInspectionPanelLight: VehicleStatus.DIRTY,
        vehicleInspectionHighLight: VehicleStatus.DIRTY,
        vehicleInspectionLowLight: VehicleStatus.DIRTY,
        vehicleInspectionTaillight: VehicleStatus.DIRTY,
        vehicleInspectionIndicator: VehicleStatus.DIRTY,
        vehicleInspectionBeacons: VehicleStatus.DIRTY,
        vehicleInspectionBreakLight: VehicleStatus.DIRTY,
        vehicleInspectionPlateLight: VehicleStatus.DIRTY,
        vehicleInspectionSpeedometer: VehicleStatus.DIRTY,
        vehicleInspectionTemperature: VehicleStatus.DIRTY,
        vehicleInspectionTires: VehicleStatus.DIRTY,
        vehicleInspectionStep: VehicleStatus.DIRTY,
        vehicleInspectionFireExtinguisher: VehicleStatus.DIRTY,
        vehicleInspectionSeatBelts: VehicleStatus.DIRTY,
        vehicleInspectionMonkey: VehicleStatus.DIRTY,
        vehicleInspectionTireIron: VehicleStatus.DIRTY,
        vehicleInspectionRadiatorCap: VehicleStatus.DIRTY,
        vehicleInspectionTriangle: VehicleStatus.DIRTY,
        vehicleInspectionServiceBrake: VehicleStatus.DIRTY,
        vehicleInspectionParkingBrake: VehicleStatus.DIRTY,
        vehicleInspectionOilLeaks: VehicleStatus.DIRTY,
        vehicleInspectionGlassActuator: VehicleStatus.DIRTY,
        vehicleInspectionVehicleCleaning: VehicleStatus.DIRTY,
        vehicleInspectionSeatState: VehicleStatus.DIRTY,
        vehicleInspectionExhausts: VehicleStatus.DIRTY,
        vehicleInspectionsObs: 'AAAAAAA',
        vehicleInspectionsSignedUrl: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            vehicleInspectionDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a VehicleInspections', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            vehicleInspectionDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleInspectionDate: currentDate,
          },
          returnedFromService
        );

        service.create(new VehicleInspections()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a VehicleInspections', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            vehicleInspectionDate: currentDate.format(DATE_TIME_FORMAT),
            vehicleInspectionStatus: 'BBBBBB',
            vehicleInspectionModel: 'BBBBBB',
            vehicleInspectionLicensePlate: 'BBBBBB',
            vehicleInspectionKm: 1,
            vehicleInspectionLicenseYear: 1,
            vehicleInspectionHasManual: true,
            vehicleInspectionHasExtraKey: true,
            vehicleInspectionHasStickers: true,
            vehicleInspectionGas: 'BBBBBB',
            vehicleInspectionRearView: true,
            vehicleInspectionHorn: true,
            vehicleInspectionWindshieldWiper: true,
            vehicleInspectionSquirt: true,
            vehicleInspectionInternalLight: 'BBBBBB',
            vehicleInspectionPanelLight: 'BBBBBB',
            vehicleInspectionHighLight: 'BBBBBB',
            vehicleInspectionLowLight: 'BBBBBB',
            vehicleInspectionTaillight: 'BBBBBB',
            vehicleInspectionIndicator: 'BBBBBB',
            vehicleInspectionBeacons: 'BBBBBB',
            vehicleInspectionBreakLight: 'BBBBBB',
            vehicleInspectionPlateLight: 'BBBBBB',
            vehicleInspectionSpeedometer: 'BBBBBB',
            vehicleInspectionTemperature: 'BBBBBB',
            vehicleInspectionTires: 'BBBBBB',
            vehicleInspectionStep: 'BBBBBB',
            vehicleInspectionFireExtinguisher: 'BBBBBB',
            vehicleInspectionSeatBelts: 'BBBBBB',
            vehicleInspectionMonkey: 'BBBBBB',
            vehicleInspectionTireIron: 'BBBBBB',
            vehicleInspectionRadiatorCap: 'BBBBBB',
            vehicleInspectionTriangle: 'BBBBBB',
            vehicleInspectionServiceBrake: 'BBBBBB',
            vehicleInspectionParkingBrake: 'BBBBBB',
            vehicleInspectionOilLeaks: 'BBBBBB',
            vehicleInspectionGlassActuator: 'BBBBBB',
            vehicleInspectionVehicleCleaning: 'BBBBBB',
            vehicleInspectionSeatState: 'BBBBBB',
            vehicleInspectionExhausts: 'BBBBBB',
            vehicleInspectionsObs: 'BBBBBB',
            vehicleInspectionsSignedUrl: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleInspectionDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a VehicleInspections', () => {
        const patchObject = Object.assign(
          {
            vehicleInspectionStatus: 'BBBBBB',
            vehicleInspectionLicensePlate: 'BBBBBB',
            vehicleInspectionLicenseYear: 1,
            vehicleInspectionHasExtraKey: true,
            vehicleInspectionRearView: true,
            vehicleInspectionInternalLight: 'BBBBBB',
            vehicleInspectionPanelLight: 'BBBBBB',
            vehicleInspectionHighLight: 'BBBBBB',
            vehicleInspectionIndicator: 'BBBBBB',
            vehicleInspectionBeacons: 'BBBBBB',
            vehicleInspectionSeatBelts: 'BBBBBB',
            vehicleInspectionMonkey: 'BBBBBB',
            vehicleInspectionTireIron: 'BBBBBB',
            vehicleInspectionTriangle: 'BBBBBB',
            vehicleInspectionServiceBrake: 'BBBBBB',
            vehicleInspectionParkingBrake: 'BBBBBB',
            vehicleInspectionOilLeaks: 'BBBBBB',
            vehicleInspectionExhausts: 'BBBBBB',
          },
          new VehicleInspections()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            vehicleInspectionDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of VehicleInspections', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            vehicleInspectionDate: currentDate.format(DATE_TIME_FORMAT),
            vehicleInspectionStatus: 'BBBBBB',
            vehicleInspectionModel: 'BBBBBB',
            vehicleInspectionLicensePlate: 'BBBBBB',
            vehicleInspectionKm: 1,
            vehicleInspectionLicenseYear: 1,
            vehicleInspectionHasManual: true,
            vehicleInspectionHasExtraKey: true,
            vehicleInspectionHasStickers: true,
            vehicleInspectionGas: 'BBBBBB',
            vehicleInspectionRearView: true,
            vehicleInspectionHorn: true,
            vehicleInspectionWindshieldWiper: true,
            vehicleInspectionSquirt: true,
            vehicleInspectionInternalLight: 'BBBBBB',
            vehicleInspectionPanelLight: 'BBBBBB',
            vehicleInspectionHighLight: 'BBBBBB',
            vehicleInspectionLowLight: 'BBBBBB',
            vehicleInspectionTaillight: 'BBBBBB',
            vehicleInspectionIndicator: 'BBBBBB',
            vehicleInspectionBeacons: 'BBBBBB',
            vehicleInspectionBreakLight: 'BBBBBB',
            vehicleInspectionPlateLight: 'BBBBBB',
            vehicleInspectionSpeedometer: 'BBBBBB',
            vehicleInspectionTemperature: 'BBBBBB',
            vehicleInspectionTires: 'BBBBBB',
            vehicleInspectionStep: 'BBBBBB',
            vehicleInspectionFireExtinguisher: 'BBBBBB',
            vehicleInspectionSeatBelts: 'BBBBBB',
            vehicleInspectionMonkey: 'BBBBBB',
            vehicleInspectionTireIron: 'BBBBBB',
            vehicleInspectionRadiatorCap: 'BBBBBB',
            vehicleInspectionTriangle: 'BBBBBB',
            vehicleInspectionServiceBrake: 'BBBBBB',
            vehicleInspectionParkingBrake: 'BBBBBB',
            vehicleInspectionOilLeaks: 'BBBBBB',
            vehicleInspectionGlassActuator: 'BBBBBB',
            vehicleInspectionVehicleCleaning: 'BBBBBB',
            vehicleInspectionSeatState: 'BBBBBB',
            vehicleInspectionExhausts: 'BBBBBB',
            vehicleInspectionsObs: 'BBBBBB',
            vehicleInspectionsSignedUrl: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vehicleInspectionDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a VehicleInspections', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addVehicleInspectionsToCollectionIfMissing', () => {
        it('should add a VehicleInspections to an empty array', () => {
          const vehicleInspections: IVehicleInspections = { id: 123 };
          expectedResult = service.addVehicleInspectionsToCollectionIfMissing([], vehicleInspections);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(vehicleInspections);
        });

        it('should not add a VehicleInspections to an array that contains it', () => {
          const vehicleInspections: IVehicleInspections = { id: 123 };
          const vehicleInspectionsCollection: IVehicleInspections[] = [
            {
              ...vehicleInspections,
            },
            { id: 456 },
          ];
          expectedResult = service.addVehicleInspectionsToCollectionIfMissing(vehicleInspectionsCollection, vehicleInspections);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a VehicleInspections to an array that doesn't contain it", () => {
          const vehicleInspections: IVehicleInspections = { id: 123 };
          const vehicleInspectionsCollection: IVehicleInspections[] = [{ id: 456 }];
          expectedResult = service.addVehicleInspectionsToCollectionIfMissing(vehicleInspectionsCollection, vehicleInspections);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(vehicleInspections);
        });

        it('should add only unique VehicleInspections to an array', () => {
          const vehicleInspectionsArray: IVehicleInspections[] = [{ id: 123 }, { id: 456 }, { id: 787 }];
          const vehicleInspectionsCollection: IVehicleInspections[] = [{ id: 123 }];
          expectedResult = service.addVehicleInspectionsToCollectionIfMissing(vehicleInspectionsCollection, ...vehicleInspectionsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const vehicleInspections: IVehicleInspections = { id: 123 };
          const vehicleInspections2: IVehicleInspections = { id: 456 };
          expectedResult = service.addVehicleInspectionsToCollectionIfMissing([], vehicleInspections, vehicleInspections2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(vehicleInspections);
          expect(expectedResult).toContain(vehicleInspections2);
        });

        it('should accept null and undefined values', () => {
          const vehicleInspections: IVehicleInspections = { id: 123 };
          expectedResult = service.addVehicleInspectionsToCollectionIfMissing([], null, vehicleInspections, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(vehicleInspections);
        });

        it('should return initial array if no VehicleInspections is added', () => {
          const vehicleInspectionsCollection: IVehicleInspections[] = [{ id: 123 }];
          expectedResult = service.addVehicleInspectionsToCollectionIfMissing(vehicleInspectionsCollection, undefined, null);
          expect(expectedResult).toEqual(vehicleInspectionsCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
