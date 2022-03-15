import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { PaymentModalComponent } from '../components/payment-modal/payment-modal.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
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
