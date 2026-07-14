import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UsuarioService } from '../../../core/services/usuario.service';
import { UsuarioFormComponent } from '../usuario-form/usuario-form.component';

@Component({
  selector: 'app-usuario-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatButtonModule, MatIconModule, MatPaginatorModule, MatDialogModule],
  template: `
    <div class="header-actions">
      <h2>Gerenciamento de Usuários</h2>
      <button mat-flat-button color="primary" (click)="openDialog()">
        <mat-icon>add</mat-icon> Novo Usuário
      </button>
    </div>

    <div class="table-container mat-elevation-z8">
      <table mat-table [dataSource]="dataSource()" class="glass-table">
        
        <ng-container matColumnDef="name">
          <th mat-header-cell *matHeaderCellDef> Nome </th>
          <td mat-cell *matCellDef="let user"> {{user.name}} </td>
        </ng-container>

        <ng-container matColumnDef="email">
          <th mat-header-cell *matHeaderCellDef> E-mail </th>
          <td mat-cell *matCellDef="let user"> {{user.email}} </td>
        </ng-container>

        <ng-container matColumnDef="role">
          <th mat-header-cell *matHeaderCellDef> Perfil </th>
          <td mat-cell *matCellDef="let user">
            <span class="badge badge-{{user.role | lowercase}}">{{user.role}}</span>
          </td>
        </ng-container>

        <ng-container matColumnDef="actions">
          <th mat-header-cell *matHeaderCellDef> Ações </th>
          <td mat-cell *matCellDef="let user">
            <button mat-icon-button color="primary" (click)="openDialog(user)">
              <mat-icon>edit</mat-icon>
            </button>
            <button mat-icon-button color="warn" (click)="delete(user.id)">
              <mat-icon>delete</mat-icon>
            </button>
          </td>
        </ng-container>

        <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
        <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
      </table>

      <mat-paginator 
        [length]="totalElements()" 
        [pageSize]="10" 
        [pageSizeOptions]="[5, 10, 20]"
        (page)="onPageChange($event)">
      </mat-paginator>
    </div>
  `,
  styles: [`
    .header-actions {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 24px;
      h2 { margin: 0; font-size: 24px; color: #1e293b; }
    }
    .table-container {
      background: rgba(255, 255, 255, 0.7);
      backdrop-filter: blur(10px);
      border-radius: 12px;
      overflow: hidden;
    }
    .badge {
      padding: 4px 12px;
      border-radius: 16px;
      font-size: 12px;
      font-weight: 600;
      color: white;
      &.badge-admin { background: #ef4444; }
      &.badge-tecnico { background: #3b82f6; }
      &.badge-usuario { background: #10b981; }
    }
  `]
})
export class UsuarioListComponent implements OnInit {
  displayedColumns: string[] = ['name', 'email', 'role', 'actions'];
  dataSource = signal<any[]>([]);
  totalElements = signal(0);
  pageIndex = 0;
  pageSize = 10;

  constructor(
    private usuarioService: UsuarioService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.loadUsuarios();
  }

  loadUsuarios() {
    this.usuarioService.findAll(this.pageIndex, this.pageSize).subscribe(res => {
      this.dataSource.set(res.content);
      this.totalElements.set(res.totalElements);
    });
  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadUsuarios();
  }

  openDialog(user?: any) {
    const dialogRef = this.dialog.open(UsuarioFormComponent, {
      width: '400px',
      data: user
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if (user && user.id) {
          this.usuarioService.update(user.id, result).subscribe(() => {
            this.snackBar.open('Usuário atualizado com sucesso!', 'OK', { duration: 3000 });
            this.loadUsuarios();
          });
        } else {
          this.usuarioService.create(result).subscribe(() => {
            this.snackBar.open('Usuário criado com sucesso!', 'OK', { duration: 3000 });
            this.loadUsuarios();
          });
        }
      }
    });
  }

  delete(id: string) {
    if (confirm('Tem certeza que deseja excluir este usuário?')) {
      this.usuarioService.delete(id).subscribe(() => {
        this.snackBar.open('Usuário excluído com sucesso!', 'OK', { duration: 3000 });
        this.loadUsuarios();
      });
    }
  }
}
