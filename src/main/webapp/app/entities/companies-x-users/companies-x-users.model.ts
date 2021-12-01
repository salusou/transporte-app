import { ICompanies } from 'app/entities/companies/companies.model';
import { IUser } from 'app/entities/user/user.model';

export interface ICompaniesXUsers {
  id?: number;
  companies?: ICompanies;
  user?: IUser;
}

export class CompaniesXUsers implements ICompaniesXUsers {
  constructor(public id?: number, public companies?: ICompanies, public user?: IUser) {}
}

export function getCompaniesXUsersIdentifier(companiesXUsers: ICompaniesXUsers): number | undefined {
  return companiesXUsers.id;
}
