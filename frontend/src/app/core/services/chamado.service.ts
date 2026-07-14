import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Chamado } from '../models/chamado.model';

@Injectable({
  providedIn: 'root'
})
export class ChamadoService {
  private apiUrl = 'http://localhost:8081/api/v1/chamados';

  constructor(private http: HttpClient) {}

  findAll(): Observable<any> {
    // Retorna a página Spring Boot contendo content[]
    return this.http.get<any>(this.apiUrl);
  }

  findById(id: string): Observable<Chamado> {
    return this.http.get<Chamado>(`${this.apiUrl}/${id}`);
  }

  create(chamado: any): Observable<Chamado> {
    return this.http.post<Chamado>(this.apiUrl, chamado);
  }

  atender(id: string): Observable<Chamado> {
    return this.http.put<Chamado>(`${this.apiUrl}/${id}/atender`, {});
  }

  resolver(id: string): Observable<Chamado> {
    return this.http.put<Chamado>(`${this.apiUrl}/${id}/resolver`, {});
  }

  fechar(id: string): Observable<Chamado> {
    return this.http.put<Chamado>(`${this.apiUrl}/${id}/fechar`, {});
  }

  aguardarCliente(id: string): Observable<Chamado> {
    return this.http.put<Chamado>(`${this.apiUrl}/${id}/aguardar-cliente`, {});
  }
}
