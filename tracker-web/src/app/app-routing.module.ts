import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CancelPaymentComponent } from './components/cancel-payment/cancel-payment.component';
import { SucessPaymentComponent } from './components/sucess-payment/sucess-payment.component';
import { HomeComponent } from './home/home.component';
import {ProjectRegisterComponent} from './components/project-register/project-register.component';

const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: '', component: HomeComponent},
  {
    path: 'cancel',
    component: CancelPaymentComponent
  },
  {
    path: 'success',
    component: SucessPaymentComponent
  },
  {
    path: 'registro',
    component: ProjectRegisterComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
