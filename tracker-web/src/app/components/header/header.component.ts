import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor(public activateRoute: Router) { }

  formLogin;
  loginIncorreto = false;
  ngOnInit(): void {
    this.formLogin = new FormGroup({
      login: new FormControl('', Validators.required),
      senha: new FormControl('', Validators.required)
    });
    console.log(this.activateRoute.url);

  }

  entrar() {
    this.loginIncorreto = true;
    alert('Usuario ou senha inexistentes');
  }

}
