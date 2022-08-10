import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import Typewriter from '../../../node_modules/t-writer.js/dist/t-writer.js'
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { EmailService } from '../services/email.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FileServiceService } from '../services/file.service';
declare var $: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  formContato;
  FileUpload: File | undefined;
  progress: {porcentage: number} = {porcentage: 0};
  currentFileUpload!: File;
  selectedFiles!: FileList;
  constructor(public dialog: MatDialog,
    public emailService: EmailService,
    private snackbar: MatSnackBar,
    public fileSerice: FileServiceService) { }

  ngOnInit(): void {
    const target = document.querySelector(".tw");

    const writer = new Typewriter(target, {
        loop: false,
        typeColor: '08b9cd'
    });
    writer.type("ajudamos mentes empreendedoras a transformarem <br> projetos em empresas de sucesso")
    .removeCursor()
    .rest(600)
    .start();
    this.slickConfig();
    this.criaFormularioContato();
  }

  criaFormularioContato() {
    this.formContato = new FormGroup({
      name: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      nomeEmpresa: new FormControl('', [Validators.required,]),
      mensagem: new FormControl('', [Validators.required])
    })
  }

  slickConfig() { //config do scroll das marcas agora nao utilizado
    $(document).ready(function(){
      $('.customer-logos').slick({
        slidesToShow: 4,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 800,
        arrows: false,
        dots: false,
        pauseOnHover: false,
        responsive: [{
          breakpoint: 768,
          settings: {
            slidesToShow: 4
          }
        }, {
          breakpoint: 520,
          settings: {
            slidesToShow: 3
          }
        }]
      });
    });

  }

  detectar_mobile() {
    if( navigator.userAgent.match(/Android/i)
    || navigator.userAgent.match(/webOS/i)
    || navigator.userAgent.match(/iPhone/i)
    || navigator.userAgent.match(/iPad/i)
    || navigator.userAgent.match(/iPod/i)
    || navigator.userAgent.match(/BlackBerry/i)
    || navigator.userAgent.match(/Windows Phone/i)
    ){
       return true;
     }
    else {
      return false;
    }
  }
    //snackbar config
    opnSnackbar(msg: string, action: string): void {
      this.snackbar.open(msg, action);
    }


  async enviarEmailContato() {
    const data = this.formContato.getRawValue();

    if(data.name.length > 0 && data.email.length > 0) {
      data.email = data.email.split(" ").join("");
      this.emailService.enviarEmailContato(data).subscribe(res => {
        if(res != null) {
          this.setTimeOuSnac('Email enviado com sucesso! obrigado pelo contato!');
        } else {
          this.setTimeOuSnac('Não foi possivel enviar o email!');
        }
        });
    } else {
      this.setTimeOuSnac('Por favor preencha todos os dados em vermelho!');
    }
    this.limparForm();

  }

  limparForm() {
    // this.formContato.reset();
    this.formContato.markAsUntouched();
    this.formContato.markAsPristineAndUntouched();
  }

  selectFile(event) {
  this.selectedFiles = event.target.files;
  }

  setTimeOuSnac(text: string) {
  this.snackbar.open(text, 'fechar');
    setTimeout(() => {
      this.snackbar.dismiss();

    }, 10000)
  }

}

