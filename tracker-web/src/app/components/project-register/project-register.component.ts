import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { STEPPER_GLOBAL_OPTIONS } from '@angular/cdk/stepper';
import { loadStripe } from '@stripe/stripe-js';
import { environment } from 'src/environments/environment';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-project-register',
  templateUrl: './project-register.component.html',
  styleUrls: ['./project-register.component.scss'],
  providers: [{
    provide: STEPPER_GLOBAL_OPTIONS, useValue: { displayDefaultIndicatorType: false}
  }]
})
export class ProjectRegisterComponent implements OnInit {

  isLinear = false;
  form1;
  form2;
  form3;
  stripePromise = loadStripe(environment.stripe);

  constructor(private http: HttpClient,
    private snackbar: MatSnackBar) { }

  ngOnInit(): void {
    this.form1 = new FormGroup({
      dados: new FormControl('', [Validators.required]),
      potencial: new FormControl('', [Validators.required]),
      concorrentes: new FormControl('', [Validators.required])
    });
    this.form2 = new FormGroup({
      recursos: new FormControl('', [Validators.required]),
      recursos_proprios: new FormControl('', [Validators.required]),
      preco_produtos: new FormControl('', [Validators.required])
    });
    this.form3 = new FormGroup({
      tecnologias: new FormControl('', [Validators.required]),
      dif_tecnologicos: new FormControl('', [Validators.required])
    });
  }

  async pay(): Promise<void> {
    const payment = {
      name: 'assinatura tracker',
      currency: 'brl',
      longAmount: 500, //o valor no stripe fica em centavos por isso tem que ser * 100;
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
}
