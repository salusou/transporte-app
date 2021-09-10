import { IEmployees } from 'app/entities/employees/employees.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';

export interface IPositions {
  id?: number;
  positionName?: string;
  employees?: IEmployees[] | null;
  affiliates?: IAffiliates;
}

export class Positions implements IPositions {
  constructor(public id?: number, public positionName?: string, public employees?: IEmployees[] | null, public affiliates?: IAffiliates) {}
}

export function getPositionsIdentifier(positions: IPositions): number | undefined {
  return positions.id;
}
