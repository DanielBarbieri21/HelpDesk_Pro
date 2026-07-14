import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatTooltipModule } from '@angular/material/tooltip';

import { Chamado } from '../../../core/models/chamado.model';
import { ChamadoService } from '../../../core/services/chamado.service';
import { ChamadoCreateComponent } from '../chamado-create/chamado-create.component';

@Component({
  selector: 'app-chamado-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    MatChipsModule,
    MatProgressSpinnerModule,
    MatDialogModule,
    MatTooltipModule
  ],
  templateUrl: './chamado-list.component.html',
  styleUrls: ['./chamado-list.component.scss']
})
export class ChamadoListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'titulo', 'status', 'prioridade', 'acoes'];
  dataSource = new MatTableDataSource<Chamado>();
  isLoading = true;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private chamadoService: ChamadoService,
    private dialog: MatDialog,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadChamados();
  }

  loadChamados() {
    this.isLoading = true;
    this.chamadoService.findAll().subscribe({
      next: (page) => {
        this.dataSource.data = page.content;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erro ao carregar chamados', err);
        this.isLoading = false;
      }
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  openCreateDialog() {
    const dialogRef = this.dialog.open(ChamadoCreateComponent, {
      width: '500px',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadChamados();
      }
    });
  }

  viewChamado(id: number) {
    this.router.navigate(['/chamados', id]);
  }

  getPrioridadeColor(prioridade: string): string {
    switch (prioridade) {
      case 'BAIXA': return 'bg-green-100 text-green-800';
      case 'MEDIA': return 'bg-blue-100 text-blue-800';
      case 'ALTA': return 'bg-orange-100 text-orange-800';
      case 'URGENTE': return 'bg-red-100 text-red-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  }

  getStatusColor(status: string): string {
    switch (status) {
      case 'ABERTO': return 'bg-yellow-100 text-yellow-800';
      case 'EM_ANDAMENTO': return 'bg-blue-100 text-blue-800';
      case 'RESOLVIDO': return 'bg-green-100 text-green-800';
      case 'FECHADO': return 'bg-gray-200 text-gray-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  }
}
