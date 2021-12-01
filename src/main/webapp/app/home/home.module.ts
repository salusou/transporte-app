import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent, Service } from './home.component';
import { DxCircularGaugeModule, DxPieChartModule, DxResponsiveBoxModule } from 'devextreme-angular';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([HOME_ROUTE]), DxPieChartModule, DxCircularGaugeModule, DxResponsiveBoxModule],
  providers: [Service],
  declarations: [HomeComponent],
})
export class HomeModule {}
