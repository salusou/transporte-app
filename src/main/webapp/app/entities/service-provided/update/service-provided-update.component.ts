import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IServiceProvided, ServiceProvided } from '../service-provided.model';
import { ServiceProvidedService } from '../service/service-provided.service';

@Component({
  selector: 'jhi-service-provided-update',
  templateUrl: './service-provided-update.component.html',
})
export class ServiceProvidedUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    serviceName: [null, [Validators.required]],
  });

  constructor(
    protected serviceProvidedService: ServiceProvidedService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceProvided }) => {
      this.updateForm(serviceProvided);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const serviceProvided = this.createFromForm();
    if (serviceProvided.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceProvidedService.update(serviceProvided));
    } else {
      this.subscribeToSaveResponse(this.serviceProvidedService.create(serviceProvided));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceProvided>>): void {
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

  protected updateForm(serviceProvided: IServiceProvided): void {
    this.editForm.patchValue({
      id: serviceProvided.id,
      serviceName: serviceProvided.serviceName,
    });
  }

  protected createFromForm(): IServiceProvided {
    return {
      ...new ServiceProvided(),
      id: this.editForm.get(['id'])!.value,
      serviceName: this.editForm.get(['serviceName'])!.value,
    };
  }
}
