import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStatus } from '../status.model';

import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { StatusDeleteDialogPortalComponent } from '../delete/status-delete-dialog.component';
import { StatusService } from '../../../../entities/status/service/status.service';
import { StatusComponent } from '../../../../entities/status/list/status.component';

@Component({
  selector: 'jhi-status-portal',
  templateUrl: './status.component.html',
})
export class StatusPortalComponent extends StatusComponent implements OnInit {}
