import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SuppliersComponent } from './list/suppliers.component';
import { SuppliersDetailComponent } from './detail/suppliers-detail.component';
import { SuppliersUpdateComponent } from './update/suppliers-update.component';
import { SuppliersDeleteDialogComponent } from './delete/suppliers-delete-dialog.component';
import { SuppliersRoutingModule } from './route/suppliers-routing.module';

@NgModule({
  imports: [SharedModule, SuppliersRoutingModule],
  declarations: [SuppliersComponent, SuppliersDetailComponent, SuppliersUpdateComponent, SuppliersDeleteDialogComponent],
  entryComponents: [SuppliersDeleteDialogComponent],
})
export class SuppliersModule {}
