import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs/internal/operators/tap';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';

export interface AuthResponseData {
  
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  backendUrl = "";
  loggedInChanged = new BehaviorSubject<boolean>(this.checkIfSignedIn());

  constructor(private http: HttpClient, private router: Router) { }

  private checkIfSignedIn() {
    if(sessionStorage.getItem("token")) {
      return true;
    } else {
      return false;
    }
  }

  signUp(email: string, password: string) {
    return this.http.post<AuthResponseData>(this.backendUrl,
    {
      email: email,
      password: password
    }).pipe(
      tap(resData => {
        this.handleAuthentication(
          
        );
      })
    );
  }

  login(email: string, password: string) {
    return this.http
      .post<AuthResponseData>(this.backendUrl,
        {
          email: email,
          password: password
        }
      )
      .pipe(
        tap(resData => {
          this.handleAuthentication(
           
          );
        })
      );
  }

  logout() {
    this.router.navigate(['/']);
    sessionStorage.removeItem('token');
  }

  

  private handleAuthentication() {
    const token = "";
    sessionStorage.setItem('token', JSON.stringify(token));
  }


}