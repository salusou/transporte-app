import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICompaniesXUsers, CompaniesXUsers } from '../companies-x-users.model';
import { CompaniesXUsersService } from '../service/companies-x-users.service';
import { ICompanies } from 'app/entities/companies/companies.model';
import { CompaniesService } from 'app/entities/companies/service/companies.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

@Component({
  selector: 'jhi-companies-x-users-update',
  templateUrl: './companies-x-users-update.component.html',
})
export class CompaniesXUsersUpdateComponent implements OnInit {
  isSaving = false;

  companiesSharedCollection: ICompanies[] = [];
  usersSharedCollection: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    companies: [null, Validators.required],
    user: [null, Validators.required],
  });

  constructor(
    protected companiesXUsersService: CompaniesXUsersService,
    protected companiesService: CompaniesService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companiesXUsers }) => {
      this.updateForm(companiesXUsers);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const companiesXUsers = this.createFromForm();
    if (companiesXUsers.id !== undefined) {
      this.subscribeToSaveResponse(this.companiesXUsersService.update(companiesXUsers));
    } else {
      this.subscribeToSaveResponse(this.companiesXUsersService.create(companiesXUsers));
    }
  }

  trackCompaniesById(index: number, item: ICompanies): number {
    return item.id!;
  }

  trackUserById(index: number, item: IUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompaniesXUsers>>): void {
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

  protected updateForm(companiesXUsers: ICompaniesXUsers): void {
    this.editForm.patchValue({
      id: companiesXUsers.id,
      companies: companiesXUsers.companies,
      user: companiesXUsers.user,
    });

    this.companiesSharedCollection = this.companiesService.addCompaniesToCollectionIfMissing(
      this.companiesSharedCollection,
      companiesXUsers.companies
    );
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, companiesXUsers.user);
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

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }

  protected createFromForm(): ICompaniesXUsers {
    return {
      ...new CompaniesXUsers(),
      id: this.editForm.get(['id'])!.value,
      companies: this.editForm.get(['companies'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }
}
