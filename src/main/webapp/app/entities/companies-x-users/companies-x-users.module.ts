import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CompaniesXUsersComponent } from './list/companies-x-users.component';
import { CompaniesXUsersDetailComponent } from './detail/companies-x-users-detail.component';
import { CompaniesXUsersUpdateComponent } from './update/companies-x-users-update.component';
import { CompaniesXUsersDeleteDialogComponent } from './delete/companies-x-users-delete-dialog.component';
import { CompaniesXUsersRoutingModule } from './route/companies-x-users-routing.module';

@NgModule({
  imports: [SharedModule, CompaniesXUsersRoutingModule],
  declarations: [
    CompaniesXUsersComponent,
    CompaniesXUsersDetailComponent,
    CompaniesXUsersUpdateComponent,
    CompaniesXUsersDeleteDialogComponent,
  ],
  entryComponents: [CompaniesXUsersDeleteDialogComponent],
})
export class CompaniesXUsersModule {}
