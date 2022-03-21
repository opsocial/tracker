import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { HomeComponent } from './home/home.component';
import { PaymentModalComponent } from './components/payment-modal/payment-modal.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './components/material-module/material.module';
import { CreditCardDirectivesModule } from 'angular-cc-library';
import { CancelPaymentComponent } from './components/cancel-payment/cancel-payment.component';
import { SucessPaymentComponent } from './components/sucess-payment/sucess-payment.component';
import { CheckoutPaymentComponent } from './components/checkout-payment/checkout-payment.component';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    PaymentModalComponent,
    CancelPaymentComponent,
    SucessPaymentComponent,
    CheckoutPaymentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    MaterialModule, // modulo dos componenetes do angular material
    CreditCardDirectivesModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
