import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPositions } from '../positions.model';
import { PositionsDetailComponent } from '../../../../entities/positions/detail/positions-detail.component';

@Component({
  selector: 'jhi-positions-detail-portal',
  templateUrl: './positions-detail.component.html',
})
export class PositionsDetailPortalComponent extends PositionsDetailComponent implements OnInit {}
