import { Component, OnInit } from '@angular/core';
import { PositionsUpdateComponent } from '../../../../entities/positions/update/positions-update.component';

@Component({
  selector: 'jhi-positions-update-portal',
  templateUrl: './positions-update.component.html',
})
export class PositionsUpdatePortalComponent extends PositionsUpdateComponent implements OnInit {}
