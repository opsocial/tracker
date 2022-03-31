import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor() { }

  formLogin;
  loginIncorreto = false;
  ngOnInit(): void {
    this.formLogin = new FormGroup({
      login: new FormControl('', Validators.required),
      senha: new FormControl('', Validators.required)
    });
  }

  entrar() {
    this.loginIncorreto = true;
    alert('Usuario ou senha inexistentes');
  }

}
