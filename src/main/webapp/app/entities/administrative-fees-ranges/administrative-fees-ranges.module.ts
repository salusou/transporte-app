import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AdministrativeFeesRangesComponent } from './list/administrative-fees-ranges.component';
import { AdministrativeFeesRangesDetailComponent } from './detail/administrative-fees-ranges-detail.component';
import { AdministrativeFeesRangesUpdateComponent } from './update/administrative-fees-ranges-update.component';
import { AdministrativeFeesRangesDeleteDialogComponent } from './delete/administrative-fees-ranges-delete-dialog.component';
import { AdministrativeFeesRangesRoutingModule } from './route/administrative-fees-ranges-routing.module';

@NgModule({
  imports: [SharedModule, AdministrativeFeesRangesRoutingModule],
  declarations: [
    AdministrativeFeesRangesComponent,
    AdministrativeFeesRangesDetailComponent,
    AdministrativeFeesRangesUpdateComponent,
    AdministrativeFeesRangesDeleteDialogComponent,
  ],
  entryComponents: [AdministrativeFeesRangesDeleteDialogComponent],
})
export class AdministrativeFeesRangesModule {}
