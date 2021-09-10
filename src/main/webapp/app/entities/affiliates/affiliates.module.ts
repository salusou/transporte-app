import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AffiliatesComponent } from './list/affiliates.component';
import { AffiliatesDetailComponent } from './detail/affiliates-detail.component';
import { AffiliatesUpdateComponent } from './update/affiliates-update.component';
import { AffiliatesDeleteDialogComponent } from './delete/affiliates-delete-dialog.component';
import { AffiliatesRoutingModule } from './route/affiliates-routing.module';

@NgModule({
  imports: [SharedModule, AffiliatesRoutingModule],
  declarations: [AffiliatesComponent, AffiliatesDetailComponent, AffiliatesUpdateComponent, AffiliatesDeleteDialogComponent],
  entryComponents: [AffiliatesDeleteDialogComponent],
})
export class AffiliatesModule {}
