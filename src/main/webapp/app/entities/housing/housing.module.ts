import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HousingComponent } from './list/housing.component';
import { HousingDetailComponent } from './detail/housing-detail.component';
import { HousingUpdateComponent } from './update/housing-update.component';
import { HousingDeleteDialogComponent } from './delete/housing-delete-dialog.component';
import { HousingRoutingModule } from './route/housing-routing.module';

@NgModule({
  imports: [SharedModule, HousingRoutingModule],
  declarations: [HousingComponent, HousingDetailComponent, HousingUpdateComponent, HousingDeleteDialogComponent],
  entryComponents: [HousingDeleteDialogComponent],
})
export class HousingModule {}
