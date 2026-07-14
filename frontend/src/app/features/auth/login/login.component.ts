import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';

// Angular Material Modules
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  hidePassword = signal(true);
  isLoading = signal(false);

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(4)]]
    });
  }

  togglePasswordVisibility(event: MouseEvent) {
    event.preventDefault();
    this.hidePassword.set(!this.hidePassword());
  }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }

    this.isLoading.set(true);
    
    this.authService.login(this.loginForm.value).subscribe({
      next: () => {
        this.isLoading.set(false);
        this.snackBar.open('Login realizado com sucesso!', 'Fechar', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'bottom',
          panelClass: ['success-snackbar']
        });
        this.router.navigate(['/']);
      },
      error: (err) => {
        this.isLoading.set(false);
        const errorMsg = err.error?.detail || 'E-mail ou senha incorretos.';
        this.snackBar.open(errorMsg, 'OK', {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
          panelClass: ['error-snackbar']
        });
      }
    });
  }
}
