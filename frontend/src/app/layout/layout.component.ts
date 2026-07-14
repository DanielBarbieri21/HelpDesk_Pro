import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

// Angular Material
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';

import { AuthService } from '../core/services/auth.service';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [
    CommonModule, 
    RouterModule, 
    MatSidenavModule, 
    MatToolbarModule, 
    MatIconModule, 
    MatButtonModule, 
    MatListModule,
    MatMenuModule
  ],
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent {
  isSidebarOpen = signal(true);

  constructor(public authService: AuthService) {}

  toggleSidebar() {
    this.isSidebarOpen.set(!this.isSidebarOpen());
  }

  logout() {
    this.authService.logout();
  }
}
