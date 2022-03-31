import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { STEPPER_GLOBAL_OPTIONS } from '@angular/cdk/stepper';
import { loadStripe, PaymentIntent, StripeCardElementOptions, StripeElementsOptions } from '@stripe/stripe-js';
import { environment } from 'src/environments/environment';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpClient, HttpEventType, HttpResponse } from '@angular/common/http';
import { FileServiceService } from 'src/app/services/file.service';
import { Observable } from 'rxjs/internal/Observable';

//stripe service
import { StripeService, StripePaymentElementComponent, StripeCardComponent } from 'ngx-stripe';
import { switchMap } from 'rxjs';
import { PaymentService } from 'src/app/services/payment.service';
import { RegisterService } from 'src/app/services/register.service';
import { EmailService } from 'src/app/services/email.service';

@Component({
  selector: 'app-project-register',
  templateUrl: './project-register.component.html',
  styleUrls: ['./project-register.component.scss'],
  providers: [{
    provide: STEPPER_GLOBAL_OPTIONS, useValue: { displayDefaultIndicatorType: false}
  }]
})
export class ProjectRegisterComponent implements OnInit {

  // @ViewChild(StripeCardComponent) card: StripeCardComponent;

  //config do stripe
  elementOpts: StripeElementsOptions = {
    locale: 'en'
  };

  cardOptions: StripeCardElementOptions = {
    style: {
      base: {
        iconColor: '#666EE8',
        color: '#31325F',
        fontWeight: 300,
        fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
        fontSize: '18px',
        '::placeholder': {
          color: '#CFD7E0'
        }
      }
    }
  };


  paying = false;

  isLinear = false;
  form1;
  form2;
  form3;
  stripePromise = loadStripe(environment.stripe);
  //files
  selectedFiles!: FileList | null;
  currentFile!: File | null;
  progress = 0;
  message = '';
  fileInfos!: Observable<any>;
  stripe;
  amount: number = 99000;
  email = 'matheusmelhor3@dsafa.com'
  stripeCardValid: boolean = false;
  customerId?: Number;
  constructor(private http: HttpClient,
    private snackbar: MatSnackBar,
    private uploadService: FileServiceService,
    private stripeService: StripeService,
    private paymentService: PaymentService,
    private registerService: RegisterService,
    private emailService: EmailService) {}


  async ngOnInit() {
    this.form1 = new FormGroup({
      nome: new FormControl('', Validators.required),
      cpf: new FormControl('', [Validators.required, Validators.maxLength(11)]),
      telefone: new FormControl('', [Validators.required, Validators.maxLength(11)]),
      linkedin: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email])
    });

    this.form2 = new FormGroup({
      nome: new FormControl('', Validators.required),
      segmento: new FormControl('', Validators.required),
      descricao: new FormControl('', Validators.required),
      deckNomeDoArquivo: new FormControl([])
    });
    this.form3 = new FormGroup({
      nome: new FormControl(''),
      number: new FormControl(''),
      cvc: new FormControl(''),
      month: new FormControl(''),
      year: new FormControl('')
    })

    this.fileInfos = this.uploadService.getFiles();
  }

  onChange({ type, event }) {
    if (type === 'change') {
      this.stripeCardValid = event.complete;
    }
  }



  // createToker(): void {
  //   this.stripeService.createPaymentMethod({
  //     type: 'card',
  //     card: this.card.element,
  //     billing_details: {name: 'matheus'},
  //   }).subscribe((result: any) => {
  //     if(result.paymentMethod) {
  //       const pack = {
  //         paymentMethodId: result.paymentMethod,
  //         customerID: this.customerId,
  //       }//envia o metodo de pagmaento e o id do comprador para o servidor
  //       this.paymentService.criarIntencaoDePagamento(pack).subscribe((res: any) => {
  //         console.log('foi: ', res);
  //       });
  //       console.log(result.paymentMethod.id);
  //     } else if(result.error) {
  //       console.log(result.error.message);

  //     }
  //   })
  // }

  chargeCreditCard() {
    const form = this.form3.getRawValue();
    console.log(form);
    (<any>window).Stripe.card.createToken({
      number: form.number.value,
      exp_month: form.month.value,
      exp_year: form.year.value,
      cvc: form.cvc.value
    }, (status: number, response: any) => {
      if(status === 200) {
        let token = response.id;
        // this.chargeCard(token);
      } else {
        console.log(response.error.message);

      }
    })
  }

  // chargeCard(token: string) {
  //     const headers = new Headers({'token': token});
  //     this.http.post(`${environment.url}api/charge`, {}, {headers: headers}).subscribe(resp => {
  //        console.log(resp);
  //      })

  // }


  //tentado implementar novo metodo de pagamento
  async pay(): Promise<void> {
    //tentando mudar para o frontend
    this.paying = true;
  //   if(this.form3.valid) {
  //     this.stripeService.createToken(this.card.element, {name: 'matheus'})
  //     .subscribe(result => {
  //       if(result.token) {
  //         console.log(result.token);
  //         // this.opnSnackbar('Sucesso, Seu Pagamento foi confirmado', '');
  //       } else if(result.error) {
  //         console.log(result.error.message);

  //       }
  //     })
  //     this.paymentService.criarIntencaoDePagamento()
  //     .pipe(switchMap((item: any) =>
  //     this.stripeService.confirmCardPayment(item.client_secret, {
  //       payment_method: {
  //         card: this.card.element,
  //         billing_details: {
  //           name: 'matheus',
  //         },
  //       },
  //     })
  //     )
  //     ).subscribe((result) => {
  //       if(result.error) {
  //         console.log('deu erro', result.error.message);
  //       } else {
  //         this.opnSnackbar('Sucesso, Seu Pagamento foi confirmado', '');
  //       }
  //     })

  // }

    const payment = {
      name: 'assinatura tracker',
      currency: 'brl',
      longAmount: 9900, //o valor no stripe fica em centavos por isso tem que ser * 100;
      quantity: 1,
      cancelUrl: 'http://localhost:4200/cancel',
      success_url: 'http://localhost:4200/success',
    } //aqui cria o objeto do pagamento

    const stripe = await this.stripePromise;

    this.opnSnackbar("Certo redirecionando para a tela de pagamento!", '');
    //chama a api do pagamento
    this.http.post(`${environment.url}api/payment`, payment).subscribe((data: any) => {

      stripe?.redirectToCheckout({sessionId: data.id}); //redireciona para a pagina de checkout do stripe
    });
  }

  //snackbar config
  opnSnackbar(msg: string, action: string): void {
    this.snackbar.open(msg, action);
  }

  //files functions
  selectFiles(event) {
    this.selectedFiles = event.target.files;
    if(this.selectFiles != null) {
        this.upload();
    }
  }

  upload() {
    this.progress = 0;
    this.currentFile = this.selectedFiles?.item(0) as File;
    this.uploadService.upload(this.currentFile).subscribe(
      event => {
        if(event.type === HttpEventType.UploadProgress) {
          this.progress = Math.round(100 * event.loaded / event.total!);
        } else if(event instanceof HttpResponse) {
          this.message = event.body.message;
          this.fileInfos = this.uploadService.getFiles();
        }
      },
      err => {
        this.progress = 0;
        this.message = 'Não foi possível salvar o arquivo';
        this.currentFile = null;
      });
      this.selectedFiles = null;
  }

  public async salvarUsuario() {
    const data = this.form1.getRawValue();
    const dataEmail = {email: data.email, name: data.nome}
    this.enviarEmail(dataEmail);
    this.registerService.salvarUsuario(data).subscribe(data => {
    })
  }

  public async inscrever() {
    const data = this.form2.getRawValue();
    this.salvarUsuario();
    this.registerService.seIncrever(data).subscribe(data => {
      this.opnSnackbar('Cadastrado!, agora basta pagar', '');
    })
  }

  public async enviarEmail(data) {
    this.emailService.enviarEmail(data).subscribe(data => {
      console.log(data);

    })
  }
}


