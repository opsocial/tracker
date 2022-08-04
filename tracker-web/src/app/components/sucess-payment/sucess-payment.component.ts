import { Component, OnInit } from '@angular/core';
import { EmailService } from 'src/app/services/email.service';

@Component({
  selector: 'app-sucess-payment',
  templateUrl: './sucess-payment.component.html',
  styleUrls: ['./sucess-payment.component.scss']
})
export class SucessPaymentComponent implements OnInit {

  constructor(private emailService: EmailService) { }

  ngOnInit(): void {
  }

}
