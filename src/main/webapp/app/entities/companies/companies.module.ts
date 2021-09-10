import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CompaniesComponent } from './list/companies.component';
import { CompaniesDetailComponent } from './detail/companies-detail.component';
import { CompaniesUpdateComponent } from './update/companies-update.component';
import { CompaniesDeleteDialogComponent } from './delete/companies-delete-dialog.component';
import { CompaniesRoutingModule } from './route/companies-routing.module';

@NgModule({
  imports: [SharedModule, CompaniesRoutingModule],
  declarations: [CompaniesComponent, CompaniesDetailComponent, CompaniesUpdateComponent, CompaniesDeleteDialogComponent],
  entryComponents: [CompaniesDeleteDialogComponent],
})
export class CompaniesModule {}
