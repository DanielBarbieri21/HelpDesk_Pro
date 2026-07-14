import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { NgChartsModule } from 'ng2-charts';
import { ChartConfiguration, ChartData, ChartType } from 'chart.js';

import { AuthService } from '../../core/services/auth.service';
import { DashboardService, DashboardStats } from '../../core/services/dashboard.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatIconModule, MatProgressSpinnerModule, NgChartsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  isLoading = signal(true);
  stats = signal<DashboardStats | null>(null);

  // Status Chart (Doughnut)
  public statusChartType: ChartType = 'doughnut';
  public statusChartData: ChartData<'doughnut'> = {
    labels: [],
    datasets: [{ data: [], backgroundColor: ['#fef9c3', '#dbeafe', '#dcfce7', '#e2e8f0', '#ffedd5'] }]
  };

  // Prioridade Chart (Bar)
  public prioridadeChartType: ChartType = 'bar';
  public prioridadeChartData: ChartData<'bar'> = {
    labels: [],
    datasets: [{ data: [], label: 'Chamados por Prioridade', backgroundColor: '#3b82f6' }]
  };
  public prioridadeChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    scales: { y: { beginAtZero: true, ticks: { precision: 0 } } }
  };

  constructor(public authService: AuthService, private dashboardService: DashboardService) {}

  ngOnInit() {
    this.dashboardService.getEstatisticas().subscribe({
      next: (data) => {
        this.stats.set(data);
        
        // Popula gráfico de status
        this.statusChartData.labels = Object.keys(data.chamadosPorStatus);
        this.statusChartData.datasets[0].data = Object.values(data.chamadosPorStatus);
        
        // Popula gráfico de prioridade
        this.prioridadeChartData.labels = Object.keys(data.chamadosPorPrioridade);
        this.prioridadeChartData.datasets[0].data = Object.values(data.chamadosPorPrioridade);

        this.isLoading.set(false);
      },
      error: (err) => {
        console.error('Erro ao carregar dashboard', err);
        this.isLoading.set(false);
      }
    });
  }
}
