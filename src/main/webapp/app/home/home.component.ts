import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { Injectable } from '@angular/core';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  pies: PieOptions[];

  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private router: Router, service: Service) {
    this.pies = service.getPies();
  }

  screen(width: number): string {
    return width < 700 ? 'sm' : 'lg';
  }

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}

class Area {
  name = '';
  area = 0;
}

export class PieOptions {
  title = '';
  palette = '';
  dataSource: Area[] = [];
}

const countries: Area[] = [
  {
    name: 'São Paulo',
    area: 0.12,
  },
  {
    name: 'Bahia',
    area: 0.07,
  },
  {
    name: 'Minas Gerais',
    area: 0.07,
  },
  {
    name: 'Rio de Janeiro',
    area: 0.07,
  },
  {
    name: 'Paraná',
    area: 0.06,
  },
  {
    name: 'Rio Grande do Norte',
    area: 0.05,
  },
  {
    name: 'Rio Grande do Sul',
    area: 0.02,
  },
  {
    name: 'Santa Catarina',
    area: 0.55,
  },
];

const waterLandRatio: Area[] = [
  {
    name: 'Transporte',
    area: 0.29,
  },
  {
    name: 'Armazenagem',
    area: 0.71,
  },
];

@Injectable()
export class Service {
  getPies(): PieOptions[] {
    return [
      {
        title: 'Lucro Estimado',
        palette: 'Soft',
        dataSource: countries,
      },
      {
        title: 'Coleta Pendente',
        palette: 'Soft Pastel',
        dataSource: waterLandRatio,
      },
    ];
  }
}
