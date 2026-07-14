import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = 'http://localhost:8081/api/v1/usuarios';

  constructor(private http: HttpClient) {}

  findAll(page: number = 0, size: number = 10): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<any>(this.apiUrl, { params });
  }

  create(usuario: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, usuario);
  }

  update(id: string, usuario: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, usuario);
  }

  delete(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
