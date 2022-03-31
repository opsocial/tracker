import { HttpClient } from '@angular/common/http';
import { Component, Inject, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { loadStripe } from '@stripe/stripe-js';
import * as creditCardType from 'credit-card-type';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-payment-modal',
  templateUrl: './payment-modal.component.html',
  styleUrls: ['./payment-modal.component.scss']
})

export class PaymentModalComponent implements OnInit {

  formLogin: any;
  formRegister: any;
  description1: string = '';
  @Input() amount;
  @Input() description;
  confirmation: any;
  loading = false;
  //carregando o stripe
  stripePromise = loadStripe(environment.stripe);



  constructor(public dialogRef: MatDialogRef<PaymentModalComponent>,
    @Inject(MAT_DIALOG_DATA) data: any,
    form: FormBuilder,
    private http: HttpClient,
    private snackbar: MatSnackBar) {
      if(data) {
        this.description1 = data.description;
      }
    }


  ngOnInit(): void {

  }

  changeCreditCardIcon() {
    console.log(creditCardType);
  }

  async pay(): Promise<void> {
    const payment = {
      name: 'assinatura tracker',
      currency: 'brl',
      longAmount: 99900, //o valor no stripe fica em centavos por isso tem que ser * 100;
      quantity: 1,
      cancelUrl: 'http://localhost:4200/cancel',
      success_url: 'http://localhost:4200/success',
    } //aqui cria o objeto do pagamento

    const stripe = await this.stripePromise;

    this.opnSnackbar('sucesso!, redirecionando para o pagamento.', '');
    //chama a api do pagamento
    this.http.post(`${environment.url}api/payment`, payment).subscribe((data: any) => {
      stripe?.redirectToCheckout({sessionId: data.id}); //redireciona para a pagina de checkout do stripe
    });
  }


  //snackbar config
  opnSnackbar(msg: string, action: string): void {
    this.snackbar.open(msg, action);
  }

  close(): void {
    this.dialogRef.close();
  }
}
