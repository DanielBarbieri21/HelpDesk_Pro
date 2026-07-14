import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { ChamadoService } from '../../../core/services/chamado.service';

@Component({
  selector: 'app-chamado-create',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './chamado-create.component.html',
  styleUrls: ['./chamado-create.component.scss']
})
export class ChamadoCreateComponent {
  form: FormGroup;
  isLoading = signal(false);

  prioridades = ['BAIXA', 'MEDIA', 'ALTA', 'URGENTE'];

  constructor(
    private fb: FormBuilder,
    private chamadoService: ChamadoService,
    public dialogRef: MatDialogRef<ChamadoCreateComponent>,
    private snackBar: MatSnackBar
  ) {
    this.form = this.fb.group({
      titulo: ['', [Validators.required, Validators.minLength(5)]],
      descricao: ['', [Validators.required, Validators.minLength(10)]],
      prioridade: ['BAIXA', Validators.required]
    });
  }

  onSubmit() {
    if (this.form.invalid) return;
    
    this.isLoading.set(true);
    const dto = this.form.value;

    this.chamadoService.create(dto).subscribe({
      next: (res) => {
        this.isLoading.set(false);
        this.snackBar.open('Chamado aberto com sucesso!', 'Fechar', {
          duration: 3000, panelClass: 'success-snackbar'
        });
        this.dialogRef.close(true); // Retorna true informando sucesso
      },
      error: (err) => {
        this.isLoading.set(false);
        this.snackBar.open('Erro ao criar chamado.', 'OK', {
          duration: 5000, panelClass: 'error-snackbar'
        });
      }
    });
  }
}
