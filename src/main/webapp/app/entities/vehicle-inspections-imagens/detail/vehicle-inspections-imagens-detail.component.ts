import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicleInspectionsImagens } from '../vehicle-inspections-imagens.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-vehicle-inspections-imagens-detail',
  templateUrl: './vehicle-inspections-imagens-detail.component.html',
})
export class VehicleInspectionsImagensDetailComponent implements OnInit {
  vehicleInspectionsImagens: IVehicleInspectionsImagens | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleInspectionsImagens }) => {
      this.vehicleInspectionsImagens = vehicleInspectionsImagens;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
