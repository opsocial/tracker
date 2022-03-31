import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient) { }


  public seIncrever(data: any): Observable<any> {
    return this.http.post(`${environment.url}projeto/salvar`, data);
  }

  public salvarUsuario(data: any): Observable<any> {
    return this.http.post(`${environment.url}user/salvar`, data);
  }
}
