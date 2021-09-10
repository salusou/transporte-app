import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IParking, Parking } from '../parking.model';
import { ParkingService } from '../service/parking.service';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';

@Component({
  selector: 'jhi-parking-update',
  templateUrl: './parking-update.component.html',
})
export class ParkingUpdateComponent implements OnInit {
  isSaving = false;

  affiliatesSharedCollection: IAffiliates[] = [];
  citiesSharedCollection: ICities[] = [];

  editForm = this.fb.group({
    id: [],
    active: [null, [Validators.required]],
    parkingName: [null, [Validators.required]],
    parkingTradeName: [],
    parkingNumber: [],
    parkingPostalCode: [null, [Validators.maxLength(9)]],
    parkingAddress: [],
    parkingAddressComplement: [],
    parkingAddressNumber: [],
    parkingAddressNeighborhood: [],
    parkingTelephone: [],
    parkingEmail: [],
    parkingContactName: [],
    affiliates: [null, Validators.required],
    cities: [null, Validators.required],
  });

  constructor(
    protected parkingService: ParkingService,
    protected affiliatesService: AffiliatesService,
    protected citiesService: CitiesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parking }) => {
      this.updateForm(parking);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const parking = this.createFromForm();
    if (parking.id !== undefined) {
      this.subscribeToSaveResponse(this.parkingService.update(parking));
    } else {
      this.subscribeToSaveResponse(this.parkingService.create(parking));
    }
  }

  trackAffiliatesById(index: number, item: IAffiliates): number {
    return item.id!;
  }

  trackCitiesById(index: number, item: ICities): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParking>>): void {
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

  protected updateForm(parking: IParking): void {
    this.editForm.patchValue({
      id: parking.id,
      active: parking.active,
      parkingName: parking.parkingName,
      parkingTradeName: parking.parkingTradeName,
      parkingNumber: parking.parkingNumber,
      parkingPostalCode: parking.parkingPostalCode,
      parkingAddress: parking.parkingAddress,
      parkingAddressComplement: parking.parkingAddressComplement,
      parkingAddressNumber: parking.parkingAddressNumber,
      parkingAddressNeighborhood: parking.parkingAddressNeighborhood,
      parkingTelephone: parking.parkingTelephone,
      parkingEmail: parking.parkingEmail,
      parkingContactName: parking.parkingContactName,
      affiliates: parking.affiliates,
      cities: parking.cities,
    });

    this.affiliatesSharedCollection = this.affiliatesService.addAffiliatesToCollectionIfMissing(
      this.affiliatesSharedCollection,
      parking.affiliates
    );
    this.citiesSharedCollection = this.citiesService.addCitiesToCollectionIfMissing(this.citiesSharedCollection, parking.cities);
  }

  protected loadRelationshipsOptions(): void {
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
  }

  protected createFromForm(): IParking {
    return {
      ...new Parking(),
      id: this.editForm.get(['id'])!.value,
      active: this.editForm.get(['active'])!.value,
      parkingName: this.editForm.get(['parkingName'])!.value,
      parkingTradeName: this.editForm.get(['parkingTradeName'])!.value,
      parkingNumber: this.editForm.get(['parkingNumber'])!.value,
      parkingPostalCode: this.editForm.get(['parkingPostalCode'])!.value,
      parkingAddress: this.editForm.get(['parkingAddress'])!.value,
      parkingAddressComplement: this.editForm.get(['parkingAddressComplement'])!.value,
      parkingAddressNumber: this.editForm.get(['parkingAddressNumber'])!.value,
      parkingAddressNeighborhood: this.editForm.get(['parkingAddressNeighborhood'])!.value,
      parkingTelephone: this.editForm.get(['parkingTelephone'])!.value,
      parkingEmail: this.editForm.get(['parkingEmail'])!.value,
      parkingContactName: this.editForm.get(['parkingContactName'])!.value,
      affiliates: this.editForm.get(['affiliates'])!.value,
      cities: this.editForm.get(['cities'])!.value,
    };
  }
}
