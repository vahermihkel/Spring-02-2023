import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastService } from 'angular-toastify';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  isLoading = false;
  error: string = "";

  constructor(
    private router: Router,
    private authService: AuthService,
    private _toastService: ToastService
    ) { }

  ngOnInit(): void {
  }

  onSignUp(signupForm: NgForm) {
    if (!signupForm.valid) {
      return;
    }
    this.isLoading = true;
    this.authService.signUp(signupForm.value.email, signupForm.value.password).subscribe({
        next: () => {
          this.error = "";
          this.isLoading = false;
          this.authService.loggedInChanged.next(true);
          this._toastService.success('Edukalt uus admin lisatud');
        },
        error: (error) => {
          this.error = error;
          this.isLoading = false;
          this._toastService.error(error);
        }
      }
    );
    signupForm.reset();
  }
}