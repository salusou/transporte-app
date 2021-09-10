import { Component, OnInit } from '@angular/core';
import { PositionsComponent } from '../../../../entities/positions/list/positions.component';
import { IPositions } from '../../../../entities/positions/positions.model';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ASC, DESC } from '../../../../config/pagination.constants';

@Component({
  selector: 'jhi-positions-portal',
  templateUrl: './positions.component.html',
})
export class PositionsPortalComponent extends PositionsComponent implements OnInit {
  delete(positions: IPositions): void {
    super.delete(positions);
  }

  ngOnInit(): void {
    super.ngOnInit();
  }

  trackId(index: number, item: IPositions): number {
    return super.trackId(index, item);
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    super.loadPage(page, dontNavigate);

    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;

    this.positionsService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IPositions[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
        },
        () => {
          this.isLoading = false;
          this.onError();
        }
      );
  }

  onSuccess(data: IPositions[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    super.onSuccess(data, headers, page, navigate);
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/portal/general/positions'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
        },
      });
    }
    this.positions = data ?? [];
    this.ngbPaginationPage = this.page;
  }
}
