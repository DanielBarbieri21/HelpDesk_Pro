import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { Inject } from '@angular/core';

import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-usuario-form',
  standalone: true,
  imports: [
    CommonModule, 
    ReactiveFormsModule, 
    MatDialogModule, 
    MatButtonModule, 
    MatFormFieldModule, 
    MatInputModule,
    MatSelectModule
  ],
  template: `
    <h2 mat-dialog-title>{{ data?.id ? 'Editar Usuário' : 'Novo Usuário' }}</h2>
    <mat-dialog-content>
      <form [formGroup]="userForm" class="user-form">
        <mat-form-field appearance="outline">
          <mat-label>Nome Completo</mat-label>
          <input matInput formControlName="name" placeholder="Ex: João Silva">
        </mat-form-field>

        <mat-form-field appearance="outline">
          <mat-label>E-mail</mat-label>
          <input matInput type="email" formControlName="email">
        </mat-form-field>

        <mat-form-field appearance="outline">
          <mat-label>Senha</mat-label>
          <input matInput type="password" formControlName="password" placeholder="Em branco para manter a atual">
        </mat-form-field>

        <mat-form-field appearance="outline">
          <mat-label>Perfil (Role)</mat-label>
          <mat-select formControlName="role">
            <mat-option value="USUARIO">Usuário</mat-option>
            <mat-option value="TECNICO">Técnico</mat-option>
            <mat-option value="ADMIN">Administrador</mat-option>
          </mat-select>
        </mat-form-field>
      </form>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cancelar</button>
      <button mat-flat-button color="primary" [disabled]="userForm.invalid || isLoading()" (click)="save()">Salvar</button>
    </mat-dialog-actions>
  `,
  styles: [`
    .user-form {
      display: flex;
      flex-direction: column;
      gap: 16px;
      margin-top: 16px;
      min-width: 350px;
    }
  `]
})
export class UsuarioFormComponent implements OnInit {
  userForm: FormGroup;
  isLoading = signal(false);

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<UsuarioFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.userForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: [''],
      role: ['USUARIO', Validators.required]
    });
  }

  ngOnInit() {
    if (this.data) {
      this.userForm.patchValue({
        name: this.data.name,
        email: this.data.email,
        role: this.data.role
      });
    }
  }

  save() {
    if (this.userForm.valid) {
      this.dialogRef.close(this.userForm.value);
    }
  }
}
