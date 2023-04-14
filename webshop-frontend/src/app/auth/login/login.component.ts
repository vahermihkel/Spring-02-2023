import { Component, OnInit } from '@angular/core';
// import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

import {FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { HttpClient } from '@angular/common/http';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  matcher = new MyErrorStateMatcher();

  isLoading = false;
  error: string = "";

  constructor(
    private router: Router,
    private authService: AuthService,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
  }

  onLogin(loginForm: NgForm) {
    this.http.post<{token: string}>("http://localhost:8080/login", 
    {
      "email": this.emailFormControl.value,
      "password": loginForm.value.password
    }).subscribe(res => {
      this.router.navigateByUrl("/admin");
      sessionStorage.setItem("token", res.token);
      this.authService.loggedInChanged.next(true);
    })

    // if (!loginForm.valid) {
    //   return;
    // }
    // this.isLoading = true;
    // this.authService.login(this.emailFormControl.value, loginForm.value.password).subscribe({
    //     next: () => {
    //       this.error = "";
    //       this.isLoading = false;
    //       this.authService.loggedInChanged.next(true);
    //       this.router.navigateByUrl("/admin");
    //     },
    //     error: (errorMessage) => {
    //       this.error = errorMessage;
    //       this.isLoading = false;
    //     }
    //   }
    // );
    // loginForm.reset();
  }
}
