import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CartProduct } from '../models/cart-product.model';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private dbUrl = "http://localhost:8080/payment/321"

  constructor(private http: HttpClient) { }

  getPaymentLink(cartProducts: CartProduct[]) {
    return this.http.post<any>(this.dbUrl, cartProducts);
  }
}
