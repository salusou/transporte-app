import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CitiesComponent } from './list/cities.component';
import { CitiesDetailComponent } from './detail/cities-detail.component';
import { CitiesUpdateComponent } from './update/cities-update.component';
import { CitiesDeleteDialogComponent } from './delete/cities-delete-dialog.component';
import { CitiesRoutingModule } from './route/cities-routing.module';

@NgModule({
  imports: [SharedModule, CitiesRoutingModule],
  declarations: [CitiesComponent, CitiesDetailComponent, CitiesUpdateComponent, CitiesDeleteDialogComponent],
  entryComponents: [CitiesDeleteDialogComponent],
})
export class CitiesModule {}
