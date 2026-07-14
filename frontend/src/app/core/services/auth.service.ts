import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User, AuthResponse } from '../models/user.model';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8081/api/v1/auth';
  private readonly TOKEN_KEY = 'helpdesk_jwt';

  // Usamos Signals, novidade poderosa do Angular 17 para reatividade sem Observables complexos
  public currentUser = signal<User | null>(null);

  constructor(private http: HttpClient, private router: Router) {
    this.checkToken();
  }

  login(credentials: any): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {
        if (response.accessToken) {
          localStorage.setItem(this.TOKEN_KEY, response.accessToken);
          this.checkToken();
        }
      })
    );
  }

  logout() {
    localStorage.removeItem(this.TOKEN_KEY);
    this.currentUser.set(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  private checkToken() {
    const token = this.getToken();
    if (token) {
      // Decode JWT para pegar as infos do usuário
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        this.currentUser.set({
          email: payload.sub,
          name: payload.name || 'User',
          role: payload.role
        });
      } catch (e) {
        this.logout();
      }
    }
  }
}
