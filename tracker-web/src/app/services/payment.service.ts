import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PaymentIntent } from '@stripe/stripe-js';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http:HttpClient) { }

  createCustomer(data) {
    return this.http.post(`${environment.url}api/testCustomer`, data);
  }

  criarIntencaoDePagamento(data): Observable<PaymentIntent> {
    return this.http.post<PaymentIntent>(
      `${environment.url}api/payment`, data
    )
  }

}
