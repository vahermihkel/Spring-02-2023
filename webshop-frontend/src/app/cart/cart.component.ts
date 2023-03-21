import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { CartProduct } from '../models/cart-product.model';
import { ProductService } from '../services/product.service';
import { ParcelMachinesComponent } from './parcel-machines/parcel-machines.component';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cartProducts: CartProduct[] = [];
  sumOfCart = 0;
  @ViewChild(ParcelMachinesComponent) parcelMachinesComponent!: ParcelMachinesComponent;

  constructor(private productService: ProductService) { } 

  ngOnInit(): void {
    const cartItemsSS = sessionStorage.getItem("cartItems");
    if (cartItemsSS) {
      this.cartProducts = JSON.parse(cartItemsSS); 
    }
    this.calculateSumOfCart();
  }

  onDecreaseQuantity(cartProduct: CartProduct) {
    cartProduct.quantity--;
    if (cartProduct.quantity <= 0) {
      this.onRemoveProduct(cartProduct);
    }
    sessionStorage.setItem("cartItems", JSON.stringify(this.cartProducts));
    this.productService.cartChanged.next(true);
    this.calculateSumOfCart();
  }

  onIncreaseQuantity(cartProduct: CartProduct) {
    cartProduct.quantity++;
    sessionStorage.setItem("cartItems", JSON.stringify(this.cartProducts));
    this.productService.cartChanged.next(true);
    this.calculateSumOfCart();
  }

  onRemoveProduct(cartProduct: CartProduct) {
    const index = this.cartProducts.findIndex(element => element.product.id === cartProduct.product.id);
    if (index >= 0) {
      this.cartProducts.splice(index,1);
      if (this.cartProducts.length === 1 && this.cartProducts[0].product.id === 11110000) {
        this.parcelMachinesComponent.onUnselectParcelMachine();
      }
      sessionStorage.setItem("cartItems", JSON.stringify(this.cartProducts));
      this.productService.cartChanged.next(true);
    }
    this.calculateSumOfCart();
  }

  onEmptyCart() {
    this.cartProducts = [];
    sessionStorage.setItem("cartItems", JSON.stringify(this.cartProducts));
    this.productService.cartChanged.next(true);
    this.calculateSumOfCart();
  }

  calculateSumOfCart() {
    this.sumOfCart = 0;
    this.cartProducts.forEach(element => this.sumOfCart += element.product.price * element.quantity);
  }
}
