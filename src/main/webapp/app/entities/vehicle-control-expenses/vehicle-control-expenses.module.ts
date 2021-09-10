import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VehicleControlExpensesComponent } from './list/vehicle-control-expenses.component';
import { VehicleControlExpensesDetailComponent } from './detail/vehicle-control-expenses-detail.component';
import { VehicleControlExpensesUpdateComponent } from './update/vehicle-control-expenses-update.component';
import { VehicleControlExpensesDeleteDialogComponent } from './delete/vehicle-control-expenses-delete-dialog.component';
import { VehicleControlExpensesRoutingModule } from './route/vehicle-control-expenses-routing.module';

@NgModule({
  imports: [SharedModule, VehicleControlExpensesRoutingModule],
  declarations: [
    VehicleControlExpensesComponent,
    VehicleControlExpensesDetailComponent,
    VehicleControlExpensesUpdateComponent,
    VehicleControlExpensesDeleteDialogComponent,
  ],
  entryComponents: [VehicleControlExpensesDeleteDialogComponent],
})
export class VehicleControlExpensesModule {}
