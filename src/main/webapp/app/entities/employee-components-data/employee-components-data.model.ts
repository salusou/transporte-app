import { IEmployees } from 'app/entities/employees/employees.model';

export interface IEmployeeComponentsData {
  id?: number;
  dataInfo?: string | null;
  employee?: IEmployees;
}

export class EmployeeComponentsData implements IEmployeeComponentsData {
  constructor(public id?: number, public dataInfo?: string | null, public employee?: IEmployees) {}
}

export function getEmployeeComponentsDataIdentifier(employeeComponentsData: IEmployeeComponentsData): number | undefined {
  return employeeComponentsData.id;
}
