import * as dayjs from 'dayjs';
import { IVehicleInspectionsImagens } from 'app/entities/vehicle-inspections-imagens/vehicle-inspections-imagens.model';
import { IVehicleControlItem } from 'app/entities/vehicle-control-item/vehicle-control-item.model';
import { InspectionStatus } from 'app/entities/enumerations/inspection-status.model';
import { FuelLevel } from 'app/entities/enumerations/fuel-level.model';
import { VehicleStatus } from 'app/entities/enumerations/vehicle-status.model';

export interface IVehicleInspections {
  id?: number;
  vehicleInspectionDate?: dayjs.Dayjs;
  vehicleInspectionStatus?: InspectionStatus;
  vehicleInspectionModel?: string;
  vehicleInspectionLicensePlate?: string;
  vehicleInspectionKm?: number | null;
  vehicleInspectionLicenseYear?: number | null;
  vehicleInspectionHasManual?: boolean | null;
  vehicleInspectionHasExtraKey?: boolean | null;
  vehicleInspectionHasStickers?: boolean | null;
  vehicleInspectionGas?: FuelLevel | null;
  vehicleInspectionRearView?: boolean | null;
  vehicleInspectionHorn?: boolean | null;
  vehicleInspectionWindshieldWiper?: boolean | null;
  vehicleInspectionSquirt?: boolean | null;
  vehicleInspectionInternalLight?: VehicleStatus | null;
  vehicleInspectionPanelLight?: VehicleStatus | null;
  vehicleInspectionHighLight?: VehicleStatus | null;
  vehicleInspectionLowLight?: VehicleStatus | null;
  vehicleInspectionTaillight?: VehicleStatus | null;
  vehicleInspectionIndicator?: VehicleStatus | null;
  vehicleInspectionBeacons?: VehicleStatus | null;
  vehicleInspectionBreakLight?: VehicleStatus | null;
  vehicleInspectionPlateLight?: VehicleStatus | null;
  vehicleInspectionSpeedometer?: VehicleStatus | null;
  vehicleInspectionTemperature?: VehicleStatus | null;
  vehicleInspectionTires?: VehicleStatus | null;
  vehicleInspectionStep?: VehicleStatus | null;
  vehicleInspectionFireExtinguisher?: VehicleStatus | null;
  vehicleInspectionSeatBelts?: VehicleStatus | null;
  vehicleInspectionMonkey?: VehicleStatus | null;
  vehicleInspectionTireIron?: VehicleStatus | null;
  vehicleInspectionRadiatorCap?: VehicleStatus | null;
  vehicleInspectionTriangle?: VehicleStatus | null;
  vehicleInspectionServiceBrake?: VehicleStatus | null;
  vehicleInspectionParkingBrake?: VehicleStatus | null;
  vehicleInspectionOilLeaks?: VehicleStatus | null;
  vehicleInspectionGlassActuator?: VehicleStatus | null;
  vehicleInspectionVehicleCleaning?: VehicleStatus | null;
  vehicleInspectionSeatState?: VehicleStatus | null;
  vehicleInspectionExhausts?: VehicleStatus | null;
  vehicleInspectionsObs?: string | null;
  vehicleInspectionsSignedUrl?: string | null;
  vehicleInspectionsImagens?: IVehicleInspectionsImagens[] | null;
  vehicleControls?: IVehicleControlItem | null;
}

export class VehicleInspections implements IVehicleInspections {
  constructor(
    public id?: number,
    public vehicleInspectionDate?: dayjs.Dayjs,
    public vehicleInspectionStatus?: InspectionStatus,
    public vehicleInspectionModel?: string,
    public vehicleInspectionLicensePlate?: string,
    public vehicleInspectionKm?: number | null,
    public vehicleInspectionLicenseYear?: number | null,
    public vehicleInspectionHasManual?: boolean | null,
    public vehicleInspectionHasExtraKey?: boolean | null,
    public vehicleInspectionHasStickers?: boolean | null,
    public vehicleInspectionGas?: FuelLevel | null,
    public vehicleInspectionRearView?: boolean | null,
    public vehicleInspectionHorn?: boolean | null,
    public vehicleInspectionWindshieldWiper?: boolean | null,
    public vehicleInspectionSquirt?: boolean | null,
    public vehicleInspectionInternalLight?: VehicleStatus | null,
    public vehicleInspectionPanelLight?: VehicleStatus | null,
    public vehicleInspectionHighLight?: VehicleStatus | null,
    public vehicleInspectionLowLight?: VehicleStatus | null,
    public vehicleInspectionTaillight?: VehicleStatus | null,
    public vehicleInspectionIndicator?: VehicleStatus | null,
    public vehicleInspectionBeacons?: VehicleStatus | null,
    public vehicleInspectionBreakLight?: VehicleStatus | null,
    public vehicleInspectionPlateLight?: VehicleStatus | null,
    public vehicleInspectionSpeedometer?: VehicleStatus | null,
    public vehicleInspectionTemperature?: VehicleStatus | null,
    public vehicleInspectionTires?: VehicleStatus | null,
    public vehicleInspectionStep?: VehicleStatus | null,
    public vehicleInspectionFireExtinguisher?: VehicleStatus | null,
    public vehicleInspectionSeatBelts?: VehicleStatus | null,
    public vehicleInspectionMonkey?: VehicleStatus | null,
    public vehicleInspectionTireIron?: VehicleStatus | null,
    public vehicleInspectionRadiatorCap?: VehicleStatus | null,
    public vehicleInspectionTriangle?: VehicleStatus | null,
    public vehicleInspectionServiceBrake?: VehicleStatus | null,
    public vehicleInspectionParkingBrake?: VehicleStatus | null,
    public vehicleInspectionOilLeaks?: VehicleStatus | null,
    public vehicleInspectionGlassActuator?: VehicleStatus | null,
    public vehicleInspectionVehicleCleaning?: VehicleStatus | null,
    public vehicleInspectionSeatState?: VehicleStatus | null,
    public vehicleInspectionExhausts?: VehicleStatus | null,
    public vehicleInspectionsObs?: string | null,
    public vehicleInspectionsSignedUrl?: string | null,
    public vehicleInspectionsImagens?: IVehicleInspectionsImagens[] | null,
    public vehicleControls?: IVehicleControlItem | null
  ) {
    this.vehicleInspectionHasManual = this.vehicleInspectionHasManual ?? false;
    this.vehicleInspectionHasExtraKey = this.vehicleInspectionHasExtraKey ?? false;
    this.vehicleInspectionHasStickers = this.vehicleInspectionHasStickers ?? false;
    this.vehicleInspectionRearView = this.vehicleInspectionRearView ?? false;
    this.vehicleInspectionHorn = this.vehicleInspectionHorn ?? false;
    this.vehicleInspectionWindshieldWiper = this.vehicleInspectionWindshieldWiper ?? false;
    this.vehicleInspectionSquirt = this.vehicleInspectionSquirt ?? false;
  }
}

export function getVehicleInspectionsIdentifier(vehicleInspections: IVehicleInspections): number | undefined {
  return vehicleInspections.id;
}
