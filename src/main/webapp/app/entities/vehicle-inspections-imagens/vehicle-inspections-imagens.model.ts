import { IVehicleInspections } from 'app/entities/vehicle-inspections/vehicle-inspections.model';
import { InspectionPositions } from 'app/entities/enumerations/inspection-positions.model';
import { VehicleStatus } from 'app/entities/enumerations/vehicle-status.model';

export interface IVehicleInspectionsImagens {
  id?: number;
  vehicleInspectionsImagensPositions?: InspectionPositions;
  vehicleInspectionsImagensStatus?: VehicleStatus;
  vehicleInspectionsImagensObs?: string | null;
  vehicleInspectionsImagensPhotoContentType?: string;
  vehicleInspectionsImagensPhoto?: string;
  vehicleInspectionsImagensPositionsX?: number;
  vehicleInspectionsImagensPositionsY?: number;
  vehicleInspections?: IVehicleInspections;
}

export class VehicleInspectionsImagens implements IVehicleInspectionsImagens {
  constructor(
    public id?: number,
    public vehicleInspectionsImagensPositions?: InspectionPositions,
    public vehicleInspectionsImagensStatus?: VehicleStatus,
    public vehicleInspectionsImagensObs?: string | null,
    public vehicleInspectionsImagensPhotoContentType?: string,
    public vehicleInspectionsImagensPhoto?: string,
    public vehicleInspectionsImagensPositionsX?: number,
    public vehicleInspectionsImagensPositionsY?: number,
    public vehicleInspections?: IVehicleInspections
  ) {}
}

export function getVehicleInspectionsImagensIdentifier(vehicleInspectionsImagens: IVehicleInspectionsImagens): number | undefined {
  return vehicleInspectionsImagens.id;
}
