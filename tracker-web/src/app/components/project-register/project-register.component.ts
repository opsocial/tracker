import { Component, OnInit} from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { STEPPER_GLOBAL_OPTIONS } from '@angular/cdk/stepper';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpClient, HttpEventType, HttpResponse } from '@angular/common/http';
import { FileServiceService } from 'src/app/services/file.service';
import { Observable } from 'rxjs/internal/Observable';

//stripe service
import { RegisterService } from 'src/app/services/register.service';
import { EmailService } from 'src/app/services/email.service';
import { Usuario } from 'src/app/model/Usuario';
import { StripeService } from 'ngx-stripe';
import { PaymentService } from 'src/app/services/payment.service';
import { loadStripe } from '@stripe/stripe-js';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-project-register',
  templateUrl: './project-register.component.html',
  styleUrls: ['./project-register.component.scss'],
  providers: [{
    provide: STEPPER_GLOBAL_OPTIONS, useValue: { displayDefaultIndicatorType: false}
  }]
})
export class ProjectRegisterComponent implements OnInit {
stripePromise = loadStripe(environment.stripe);
  form1;
  form2;
  form3;
  isLinear = true;
  //files
  selectedFiles!: FileList | null;
  currentFile!: File | null;
  progress = 0;
  message = '';
  fileInfos!: Observable<any>;
  stripe;
  amount: number = 99000;
  email = 'matheusmelhor3@dsafa.com'
  shortLink: string = "";
  loading: boolean = false;
  file: File = null!;
  usuario!: Usuario;

  constructor(
    private snackbar: MatSnackBar,
    private uploadService: FileServiceService,
    private registerService: RegisterService,
    private emailService: EmailService,
    private http: HttpClient) {}


  async ngOnInit() {
    window.scrollTo(0, 0);
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

  onChange(event) {
    this.file = event.target.files[0];
    console.log(this.file)
  }

  onUpload() {
    this.loading = !this.loading;
    this.uploadService.upload(this.file).subscribe(
      (event: any) => {
        if(typeof(event) == "object") {
          this.shortLink = event.link;
          this.loading = false;
          this.setTimeOuSnac('Arquivo subido com sucesso!')
        }
      }
    )
  }

   //tentado implementar novo metodo de pagamento
   async pay(): Promise<void> {
   debugger
    const payment = {
      id_usuario: this.usuario.id_usuario,
      name: 'assinatura tracker',
      currency: 'brl',
      longAmount: 9900, //o valor no stripe fica em centavos por isso tem que ser * 100;
      quantity: 1,
      cancelUrl: 'https://trackeraceleradora.com.br/#/cancel',
      success_url: 'https://trackeraceleradora.com.br/#/success',
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
      this.opnSnackbar(this.message, '');
  }

  public async salvarUsuario() {
      const data = this.form1.getRawValue();
      const dataEmail = {email: data.email.trim(), name: data.nome.trim()};
      if(data.nome.length > 0 || data.email.length > 0 ||
      data.cpf.length > 0 || data.telefone.length > 0) {
        data.email = data.email.trim();
        data.cpf = data.cpf.trim();
        data.nome = data.nome.trim();
        this.enviarEmail(dataEmail);
      this.registerService.salvarUsuario(data).subscribe(data => {
        this.usuario = data;
        this.inscrever();

      });
    } else {
      this.setTimeOuSnac('Por favor preencha todos os campos marcados!');
    }
  }

  public async enviarEmail(data) {
    this.emailService.enviarEmail(data).subscribe(data => {
      if(data) {

      }
    })
  }
  public async inscrever() {
    const data = this.form2.getRawValue();
    data.deckNomeDoArquivo = this.shortLink;
    data.idUsuario = this.usuario.id_usuario;

    if(data.nome.length > 0 || data.segmento.length > 0 || data.descricao.length > 0) {
    this.registerService.seIncrever(data).subscribe(data => {
      this.setTimeOuSnac('Cadastrado!, agora basta pagar');
      this.pay();
    })
  }  else {
    this.setTimeOuSnac('Por favor preencha todos os campos marcados!');
  }
  localStorage.clear();
}



  setTimeOuSnac(text: string) {
    this.snackbar.open(text, '');
      setTimeout(() => {
        this.snackbar.dismiss();
      }, 2000)
    }

    OnDestroy() {
      localStorage.clear();
    }
}
