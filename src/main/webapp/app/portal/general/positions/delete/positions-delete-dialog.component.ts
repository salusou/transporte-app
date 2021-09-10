import { Component } from '@angular/core';
import { PositionsDeleteDialogComponent } from '../../../../entities/positions/delete/positions-delete-dialog.component';

@Component({
  templateUrl: './positions-delete-dialog.component.html',
})
export class PositionsDeleteDialogPortalComponent extends PositionsDeleteDialogComponent {
  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.positionsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
