import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EmailService {

  constructor(private http: HttpClient) { }

  enviarEmail(data: any): Observable<any> {
    return this.http.get(`${environment.url}email/getemail`, data);
  }

}
