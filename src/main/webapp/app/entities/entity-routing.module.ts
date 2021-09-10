import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'countries',
        data: { pageTitle: 'transporteApp.countries.home.title' },
        loadChildren: () => import('./countries/countries.module').then(m => m.CountriesModule),
      },
      {
        path: 'state-provinces',
        data: { pageTitle: 'transporteApp.stateProvinces.home.title' },
        loadChildren: () => import('./state-provinces/state-provinces.module').then(m => m.StateProvincesModule),
      },
      {
        path: 'cities',
        data: { pageTitle: 'transporteApp.cities.home.title' },
        loadChildren: () => import('./cities/cities.module').then(m => m.CitiesModule),
      },
      {
        path: 'companies',
        data: { pageTitle: 'transporteApp.companies.home.title' },
        loadChildren: () => import('./companies/companies.module').then(m => m.CompaniesModule),
      },
      {
        path: 'affiliates',
        data: { pageTitle: 'transporteApp.affiliates.home.title' },
        loadChildren: () => import('./affiliates/affiliates.module').then(m => m.AffiliatesModule),
      },
      {
        path: 'insurances',
        data: { pageTitle: 'transporteApp.insurances.home.title' },
        loadChildren: () => import('./insurances/insurances.module').then(m => m.InsurancesModule),
      },
      {
        path: 'positions',
        data: { pageTitle: 'transporteApp.positions.home.title' },
        loadChildren: () => import('./positions/positions.module').then(m => m.PositionsModule),
      },
      {
        path: 'cost-center',
        data: { pageTitle: 'transporteApp.costCenter.home.title' },
        loadChildren: () => import('./cost-center/cost-center.module').then(m => m.CostCenterModule),
      },
      {
        path: 'administrative-fees-ranges',
        data: { pageTitle: 'transporteApp.administrativeFeesRanges.home.title' },
        loadChildren: () =>
          import('./administrative-fees-ranges/administrative-fees-ranges.module').then(m => m.AdministrativeFeesRangesModule),
      },
      {
        path: 'customers-groups',
        data: { pageTitle: 'transporteApp.customersGroups.home.title' },
        loadChildren: () => import('./customers-groups/customers-groups.module').then(m => m.CustomersGroupsModule),
      },
      {
        path: 'fees',
        data: { pageTitle: 'transporteApp.fees.home.title' },
        loadChildren: () => import('./fees/fees.module').then(m => m.FeesModule),
      },
      {
        path: 'customers',
        data: { pageTitle: 'transporteApp.customers.home.title' },
        loadChildren: () => import('./customers/customers.module').then(m => m.CustomersModule),
      },
      {
        path: 'customers-contacts',
        data: { pageTitle: 'transporteApp.customersContacts.home.title' },
        loadChildren: () => import('./customers-contacts/customers-contacts.module').then(m => m.CustomersContactsModule),
      },
      {
        path: 'customer-attachments',
        data: { pageTitle: 'transporteApp.customerAttachments.home.title' },
        loadChildren: () => import('./customer-attachments/customer-attachments.module').then(m => m.CustomerAttachmentsModule),
      },
      {
        path: 'service-provided',
        data: { pageTitle: 'transporteApp.serviceProvided.home.title' },
        loadChildren: () => import('./service-provided/service-provided.module').then(m => m.ServiceProvidedModule),
      },
      {
        path: 'status-attachments',
        data: { pageTitle: 'transporteApp.statusAttachments.home.title' },
        loadChildren: () => import('./status-attachments/status-attachments.module').then(m => m.StatusAttachmentsModule),
      },
      {
        path: 'status',
        data: { pageTitle: 'transporteApp.status.home.title' },
        loadChildren: () => import('./status/status.module').then(m => m.StatusModule),
      },
      {
        path: 'parking',
        data: { pageTitle: 'transporteApp.parking.home.title' },
        loadChildren: () => import('./parking/parking.module').then(m => m.ParkingModule),
      },
      {
        path: 'parking-sector',
        data: { pageTitle: 'transporteApp.parkingSector.home.title' },
        loadChildren: () => import('./parking-sector/parking-sector.module').then(m => m.ParkingSectorModule),
      },
      {
        path: 'parking-sector-space',
        data: { pageTitle: 'transporteApp.parkingSectorSpace.home.title' },
        loadChildren: () => import('./parking-sector-space/parking-sector-space.module').then(m => m.ParkingSectorSpaceModule),
      },
      {
        path: 'suppliers',
        data: { pageTitle: 'transporteApp.suppliers.home.title' },
        loadChildren: () => import('./suppliers/suppliers.module').then(m => m.SuppliersModule),
      },
      {
        path: 'supplier-banks-info',
        data: { pageTitle: 'transporteApp.supplierBanksInfo.home.title' },
        loadChildren: () => import('./supplier-banks-info/supplier-banks-info.module').then(m => m.SupplierBanksInfoModule),
      },
      {
        path: 'supplier-contacts',
        data: { pageTitle: 'transporteApp.supplierContacts.home.title' },
        loadChildren: () => import('./supplier-contacts/supplier-contacts.module').then(m => m.SupplierContactsModule),
      },
      {
        path: 'employees',
        data: { pageTitle: 'transporteApp.employees.home.title' },
        loadChildren: () => import('./employees/employees.module').then(m => m.EmployeesModule),
      },
      {
        path: 'employee-attachments',
        data: { pageTitle: 'transporteApp.employeeAttachments.home.title' },
        loadChildren: () => import('./employee-attachments/employee-attachments.module').then(m => m.EmployeeAttachmentsModule),
      },
      {
        path: 'employee-components-data',
        data: { pageTitle: 'transporteApp.employeeComponentsData.home.title' },
        loadChildren: () => import('./employee-components-data/employee-components-data.module').then(m => m.EmployeeComponentsDataModule),
      },
      {
        path: 'vehicle-controls',
        data: { pageTitle: 'transporteApp.vehicleControls.home.title' },
        loadChildren: () => import('./vehicle-controls/vehicle-controls.module').then(m => m.VehicleControlsModule),
      },
      {
        path: 'vehicle-location-status',
        data: { pageTitle: 'transporteApp.vehicleLocationStatus.home.title' },
        loadChildren: () => import('./vehicle-location-status/vehicle-location-status.module').then(m => m.VehicleLocationStatusModule),
      },
      {
        path: 'vehicle-control-history',
        data: { pageTitle: 'transporteApp.vehicleControlHistory.home.title' },
        loadChildren: () => import('./vehicle-control-history/vehicle-control-history.module').then(m => m.VehicleControlHistoryModule),
      },
      {
        path: 'vehicle-control-item',
        data: { pageTitle: 'transporteApp.vehicleControlItem.home.title' },
        loadChildren: () => import('./vehicle-control-item/vehicle-control-item.module').then(m => m.VehicleControlItemModule),
      },
      {
        path: 'vehicle-inspections',
        data: { pageTitle: 'transporteApp.vehicleInspections.home.title' },
        loadChildren: () => import('./vehicle-inspections/vehicle-inspections.module').then(m => m.VehicleInspectionsModule),
      },
      {
        path: 'vehicle-inspections-imagens',
        data: { pageTitle: 'transporteApp.vehicleInspectionsImagens.home.title' },
        loadChildren: () =>
          import('./vehicle-inspections-imagens/vehicle-inspections-imagens.module').then(m => m.VehicleInspectionsImagensModule),
      },
      {
        path: 'vehicle-control-billing',
        data: { pageTitle: 'transporteApp.vehicleControlBilling.home.title' },
        loadChildren: () => import('./vehicle-control-billing/vehicle-control-billing.module').then(m => m.VehicleControlBillingModule),
      },
      {
        path: 'vehicle-control-expenses',
        data: { pageTitle: 'transporteApp.vehicleControlExpenses.home.title' },
        loadChildren: () => import('./vehicle-control-expenses/vehicle-control-expenses.module').then(m => m.VehicleControlExpensesModule),
      },
      {
        path: 'vehicle-control-attachments',
        data: { pageTitle: 'transporteApp.vehicleControlAttachments.home.title' },
        loadChildren: () =>
          import('./vehicle-control-attachments/vehicle-control-attachments.module').then(m => m.VehicleControlAttachmentsModule),
      },
      {
        path: 'housing',
        data: { pageTitle: 'transporteApp.housing.home.title' },
        loadChildren: () => import('./housing/housing.module').then(m => m.HousingModule),
      },
      {
        path: 'housing-vehicle-item',
        data: { pageTitle: 'transporteApp.housingVehicleItem.home.title' },
        loadChildren: () => import('./housing-vehicle-item/housing-vehicle-item.module').then(m => m.HousingVehicleItemModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
