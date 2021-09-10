import * as dayjs from 'dayjs';
import { IEmployees } from 'app/entities/employees/employees.model';

export interface IEmployeeAttachments {
  id?: number;
  attachImageContentType?: string | null;
  attachImage?: string | null;
  attachUrl?: string;
  attachDescription?: string;
  attachedDate?: dayjs.Dayjs;
  employees?: IEmployees;
}

export class EmployeeAttachments implements IEmployeeAttachments {
  constructor(
    public id?: number,
    public attachImageContentType?: string | null,
    public attachImage?: string | null,
    public attachUrl?: string,
    public attachDescription?: string,
    public attachedDate?: dayjs.Dayjs,
    public employees?: IEmployees
  ) {}
}

export function getEmployeeAttachmentsIdentifier(employeeAttachments: IEmployeeAttachments): number | undefined {
  return employeeAttachments.id;
}
