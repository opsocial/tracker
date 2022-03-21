import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { PaymentModalComponent } from '../components/payment-modal/payment-modal.component';
import Typewriter from '../../../node_modules/t-writer.js/dist/t-writer.js'
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
    const target = document.querySelector(".tw");

    const writer = new Typewriter(target, {
        loop: false,
        typeColor: '08b9cd'
    });
    writer.type("ajudamos mentes empreendedoras a transformarem projetos em empresas de sucesso")
    .removeCursor()
    .rest(500)
    .start();
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
