import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CreditCardValidators } from 'angular-cc-library';
import * as creditCardType from 'credit-card-type';
import { CreditCardType } from 'credit-card-type/dist/types';

@Component({
  selector: 'app-payment-modal',
  templateUrl: './payment-modal.component.html',
  styleUrls: ['./payment-modal.component.scss']
})

export class PaymentModalComponent implements OnInit {

  form: any;
  description: string = '';


  constructor(public dialogRef: MatDialogRef<PaymentModalComponent>,
    @Inject(MAT_DIALOG_DATA) data: any,
    form: FormBuilder) { 
      if(data) {
        this.description = data.description;
      }
    }

  ngOnInit(): void {
    this.changeCreditCardIcon();
    this.form = new FormGroup({
      name: new FormControl('', [Validators.required]),
      cardNumber: new FormControl('', [Validators.required, Validators.maxLength(16), CreditCardValidators.validateCCNumber]),
      cvc: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(4)]),
      expdate: new FormControl('', [Validators.required])
    })
  }

  changeCreditCardIcon() {
    console.log(creditCardType);
    
  }

  close(): void {
    this.dialogRef.close();
  } 
}
