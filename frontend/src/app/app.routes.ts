import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./features/auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: '',
    loadComponent: () => import('./layout/layout.component').then(m => m.LayoutComponent),
    canActivate: [authGuard],
    children: [
      {
        path: '',
        loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent)
      },
      {
        path: 'dashboard',
        loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent)
      },
      {
        path: 'chamados',
        loadComponent: () => import('./features/chamados/chamado-list/chamado-list.component').then(m => m.ChamadoListComponent)
      },
      {
        path: 'chamados/:id',
        loadComponent: () => import('./features/chamados/chamado-detail/chamado-detail.component').then(m => m.ChamadoDetailComponent)
      },
      {
        path: 'usuarios',
        loadComponent: () => import('./features/usuarios/usuario-list/usuario-list.component').then(m => m.UsuarioListComponent)
      }
    ]
  },
  {
    path: '**',
    redirectTo: ''
  }
];
