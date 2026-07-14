import { Component, OnInit, signal } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar } from '@angular/material/snack-bar';

import { Chamado } from '../../../core/models/chamado.model';
import { ChamadoService } from '../../../core/services/chamado.service';

@Component({
  selector: 'app-chamado-detail',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './chamado-detail.component.html',
  styleUrls: ['./chamado-detail.component.scss']
})
export class ChamadoDetailComponent implements OnInit {
  chamado = signal<Chamado | null>(null);
  isLoading = signal(true);
  isActionLoading = signal(false);

  constructor(
    private route: ActivatedRoute,
    private chamadoService: ChamadoService,
    private snackBar: MatSnackBar,
    public location: Location
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadChamado(id);
    }
  }

  loadChamado(id: string) {
    this.isLoading.set(true);
    this.chamadoService.findById(id).subscribe({
      next: (data) => {
        this.chamado.set(data);
        this.isLoading.set(false);
      },
      error: () => {
        this.isLoading.set(false);
        this.snackBar.open('Erro ao carregar chamado', 'OK', { panelClass: 'error-snackbar' });
      }
    });
  }

  executarAcao(acao: 'atender' | 'resolver' | 'fechar') {
    const id = this.chamado()?.id?.toString();
    if (!id) return;

    this.isActionLoading.set(true);

    let request;
    if (acao === 'atender') request = this.chamadoService.atender(id);
    else if (acao === 'resolver') request = this.chamadoService.resolver(id);
    else request = this.chamadoService.fechar(id);

    request.subscribe({
      next: (atualizado) => {
        this.isActionLoading.set(false);
        this.chamado.set(atualizado);
        this.snackBar.open(`Chamado ${acao} com sucesso!`, 'Fechar', { duration: 3000, panelClass: 'success-snackbar' });
      },
      error: (err) => {
        this.isActionLoading.set(false);
        this.snackBar.open(err.error?.detail || 'Erro ao processar ação.', 'OK', { panelClass: 'error-snackbar' });
      }
    });
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
