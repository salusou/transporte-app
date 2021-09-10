import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HousingVehicleItemComponent } from './list/housing-vehicle-item.component';
import { HousingVehicleItemDetailComponent } from './detail/housing-vehicle-item-detail.component';
import { HousingVehicleItemUpdateComponent } from './update/housing-vehicle-item-update.component';
import { HousingVehicleItemDeleteDialogComponent } from './delete/housing-vehicle-item-delete-dialog.component';
import { HousingVehicleItemRoutingModule } from './route/housing-vehicle-item-routing.module';

@NgModule({
  imports: [SharedModule, HousingVehicleItemRoutingModule],
  declarations: [
    HousingVehicleItemComponent,
    HousingVehicleItemDetailComponent,
    HousingVehicleItemUpdateComponent,
    HousingVehicleItemDeleteDialogComponent,
  ],
  entryComponents: [HousingVehicleItemDeleteDialogComponent],
})
export class HousingVehicleItemModule {}
