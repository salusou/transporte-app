import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEmployees, Employees } from '../employees.model';
import { EmployeesService } from '../service/employees.service';
import { ICompanies } from 'app/entities/companies/companies.model';
import { CompaniesService } from 'app/entities/companies/service/companies.service';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';
import { IPositions } from 'app/entities/positions/positions.model';
import { PositionsService } from 'app/entities/positions/service/positions.service';

@Component({
  selector: 'jhi-employees-update',
  templateUrl: './employees-update.component.html',
})
export class EmployeesUpdateComponent implements OnInit {
  isSaving = false;

  companiesSharedCollection: ICompanies[] = [];
  affiliatesSharedCollection: IAffiliates[] = [];
  citiesSharedCollection: ICities[] = [];
  positionsSharedCollection: IPositions[] = [];

  editForm = this.fb.group({
    id: [],
    active: [null, [Validators.required]],
    employeeFullName: [null, [Validators.required]],
    employeeEmail: [null, [Validators.required]],
    employeeIdentificationNumber: [],
    employeeNumber: [],
    employeePostalCode: [null, [Validators.maxLength(9)]],
    employeeAddress: [],
    employeeAddressComplement: [],
    employeeAddressNumber: [],
    employeeAddressNeighborhood: [],
    employeeBirthday: [],
    companies: [null, Validators.required],
    affiliates: [],
    cities: [null, Validators.required],
    positions: [null, Validators.required],
  });

  constructor(
    protected employeesService: EmployeesService,
    protected companiesService: CompaniesService,
    protected affiliatesService: AffiliatesService,
    protected citiesService: CitiesService,
    protected positionsService: PositionsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employees }) => {
      this.updateForm(employees);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employees = this.createFromForm();
    if (employees.id !== undefined) {
      this.subscribeToSaveResponse(this.employeesService.update(employees));
    } else {
      this.subscribeToSaveResponse(this.employeesService.create(employees));
    }
  }

  trackCompaniesById(index: number, item: ICompanies): number {
    return item.id!;
  }

  trackAffiliatesById(index: number, item: IAffiliates): number {
    return item.id!;
  }

  trackCitiesById(index: number, item: ICities): number {
    return item.id!;
  }

  trackPositionsById(index: number, item: IPositions): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployees>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(employees: IEmployees): void {
    this.editForm.patchValue({
      id: employees.id,
      active: employees.active,
      employeeFullName: employees.employeeFullName,
      employeeEmail: employees.employeeEmail,
      employeeIdentificationNumber: employees.employeeIdentificationNumber,
      employeeNumber: employees.employeeNumber,
      employeePostalCode: employees.employeePostalCode,
      employeeAddress: employees.employeeAddress,
      employeeAddressComplement: employees.employeeAddressComplement,
      employeeAddressNumber: employees.employeeAddressNumber,
      employeeAddressNeighborhood: employees.employeeAddressNeighborhood,
      employeeBirthday: employees.employeeBirthday,
      companies: employees.companies,
      affiliates: employees.affiliates,
      cities: employees.cities,
      positions: employees.positions,
    });

    this.companiesSharedCollection = this.companiesService.addCompaniesToCollectionIfMissing(
      this.companiesSharedCollection,
      employees.companies
    );
    this.affiliatesSharedCollection = this.affiliatesService.addAffiliatesToCollectionIfMissing(
      this.affiliatesSharedCollection,
      employees.affiliates
    );
    this.citiesSharedCollection = this.citiesService.addCitiesToCollectionIfMissing(this.citiesSharedCollection, employees.cities);
    this.positionsSharedCollection = this.positionsService.addPositionsToCollectionIfMissing(
      this.positionsSharedCollection,
      employees.positions
    );
  }

  protected loadRelationshipsOptions(): void {
    this.companiesService
      .query()
      .pipe(map((res: HttpResponse<ICompanies[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompanies[]) =>
          this.companiesService.addCompaniesToCollectionIfMissing(companies, this.editForm.get('companies')!.value)
        )
      )
      .subscribe((companies: ICompanies[]) => (this.companiesSharedCollection = companies));

    this.affiliatesService
      .query()
      .pipe(map((res: HttpResponse<IAffiliates[]>) => res.body ?? []))
      .pipe(
        map((affiliates: IAffiliates[]) =>
          this.affiliatesService.addAffiliatesToCollectionIfMissing(affiliates, this.editForm.get('affiliates')!.value)
        )
      )
      .subscribe((affiliates: IAffiliates[]) => (this.affiliatesSharedCollection = affiliates));

    this.citiesService
      .query()
      .pipe(map((res: HttpResponse<ICities[]>) => res.body ?? []))
      .pipe(map((cities: ICities[]) => this.citiesService.addCitiesToCollectionIfMissing(cities, this.editForm.get('cities')!.value)))
      .subscribe((cities: ICities[]) => (this.citiesSharedCollection = cities));

    this.positionsService
      .query()
      .pipe(map((res: HttpResponse<IPositions[]>) => res.body ?? []))
      .pipe(
        map((positions: IPositions[]) =>
          this.positionsService.addPositionsToCollectionIfMissing(positions, this.editForm.get('positions')!.value)
        )
      )
      .subscribe((positions: IPositions[]) => (this.positionsSharedCollection = positions));
  }

  protected createFromForm(): IEmployees {
    return {
      ...new Employees(),
      id: this.editForm.get(['id'])!.value,
      active: this.editForm.get(['active'])!.value,
      employeeFullName: this.editForm.get(['employeeFullName'])!.value,
      employeeEmail: this.editForm.get(['employeeEmail'])!.value,
      employeeIdentificationNumber: this.editForm.get(['employeeIdentificationNumber'])!.value,
      employeeNumber: this.editForm.get(['employeeNumber'])!.value,
      employeePostalCode: this.editForm.get(['employeePostalCode'])!.value,
      employeeAddress: this.editForm.get(['employeeAddress'])!.value,
      employeeAddressComplement: this.editForm.get(['employeeAddressComplement'])!.value,
      employeeAddressNumber: this.editForm.get(['employeeAddressNumber'])!.value,
      employeeAddressNeighborhood: this.editForm.get(['employeeAddressNeighborhood'])!.value,
      employeeBirthday: this.editForm.get(['employeeBirthday'])!.value,
      companies: this.editForm.get(['companies'])!.value,
      affiliates: this.editForm.get(['affiliates'])!.value,
      cities: this.editForm.get(['cities'])!.value,
      positions: this.editForm.get(['positions'])!.value,
    };
  }
}
