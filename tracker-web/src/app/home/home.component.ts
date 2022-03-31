import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { PaymentModalComponent } from '../components/payment-modal/payment-modal.component';
import Typewriter from '../../../node_modules/t-writer.js/dist/t-writer.js'
import { FormControl, FormGroup, Validators } from '@angular/forms';
declare var $: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  formContato;
  constructor(public dialog: MatDialog) { }

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

    this.formContato = new FormGroup({
      nome: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      empresa: new FormControl('', [Validators.required,]),
      duvida: new FormControl('', [Validators.required])
    })
  }

  slickConfig() {
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

  opnDialogPayment(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '590px';
    const dialogRef = this.dialog.open(PaymentModalComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      data => console.log("DATA: ", data)
    )
  }
}
