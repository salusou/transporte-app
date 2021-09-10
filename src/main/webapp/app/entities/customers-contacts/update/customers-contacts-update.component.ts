import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICustomersContacts, CustomersContacts } from '../customers-contacts.model';
import { CustomersContactsService } from '../service/customers-contacts.service';
import { ICustomers } from 'app/entities/customers/customers.model';
import { CustomersService } from 'app/entities/customers/service/customers.service';

@Component({
  selector: 'jhi-customers-contacts-update',
  templateUrl: './customers-contacts-update.component.html',
})
export class CustomersContactsUpdateComponent implements OnInit {
  isSaving = false;

  customersSharedCollection: ICustomers[] = [];

  editForm = this.fb.group({
    id: [],
    contactName: [null, [Validators.required]],
    contactTelephone: [null, [Validators.required]],
    contactEmail: [],
    customers: [null, Validators.required],
  });

  constructor(
    protected customersContactsService: CustomersContactsService,
    protected customersService: CustomersService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customersContacts }) => {
      this.updateForm(customersContacts);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customersContacts = this.createFromForm();
    if (customersContacts.id !== undefined) {
      this.subscribeToSaveResponse(this.customersContactsService.update(customersContacts));
    } else {
      this.subscribeToSaveResponse(this.customersContactsService.create(customersContacts));
    }
  }

  trackCustomersById(index: number, item: ICustomers): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomersContacts>>): void {
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

  protected updateForm(customersContacts: ICustomersContacts): void {
    this.editForm.patchValue({
      id: customersContacts.id,
      contactName: customersContacts.contactName,
      contactTelephone: customersContacts.contactTelephone,
      contactEmail: customersContacts.contactEmail,
      customers: customersContacts.customers,
    });

    this.customersSharedCollection = this.customersService.addCustomersToCollectionIfMissing(
      this.customersSharedCollection,
      customersContacts.customers
    );
  }

  protected loadRelationshipsOptions(): void {
    this.customersService
      .query()
      .pipe(map((res: HttpResponse<ICustomers[]>) => res.body ?? []))
      .pipe(
        map((customers: ICustomers[]) =>
          this.customersService.addCustomersToCollectionIfMissing(customers, this.editForm.get('customers')!.value)
        )
      )
      .subscribe((customers: ICustomers[]) => (this.customersSharedCollection = customers));
  }

  protected createFromForm(): ICustomersContacts {
    return {
      ...new CustomersContacts(),
      id: this.editForm.get(['id'])!.value,
      contactName: this.editForm.get(['contactName'])!.value,
      contactTelephone: this.editForm.get(['contactTelephone'])!.value,
      contactEmail: this.editForm.get(['contactEmail'])!.value,
      customers: this.editForm.get(['customers'])!.value,
    };
  }
}
