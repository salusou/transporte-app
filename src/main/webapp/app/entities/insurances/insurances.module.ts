import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { InsurancesComponent } from './list/insurances.component';
import { InsurancesDetailComponent } from './detail/insurances-detail.component';
import { InsurancesUpdateComponent } from './update/insurances-update.component';
import { InsurancesDeleteDialogComponent } from './delete/insurances-delete-dialog.component';
import { InsurancesRoutingModule } from './route/insurances-routing.module';

@NgModule({
  imports: [SharedModule, InsurancesRoutingModule],
  declarations: [InsurancesComponent, InsurancesDetailComponent, InsurancesUpdateComponent, InsurancesDeleteDialogComponent],
  entryComponents: [InsurancesDeleteDialogComponent],
})
export class InsurancesModule {}
