import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CartProduct } from 'src/app/models/cart-product.model';

@Component({
  selector: 'app-cart-product',
  templateUrl: './cart-product.component.html',
  styleUrls: ['./cart-product.component.css']
})
export class CartProductComponent implements OnInit {
  @Input() cartProduct!: CartProduct;
  @Output() decreaseQuantityEvent: EventEmitter<CartProduct> = new EventEmitter();
  @Output() increaseQuantityEvent: EventEmitter<CartProduct> = new EventEmitter();
  @Output() removeFromCartEvent: EventEmitter<CartProduct> = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

}
