import { Component, Input, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { ToastService } from 'angular-toastify';
import { AuthService } from 'src/app/auth/auth.service';
import { CartProduct } from 'src/app/models/cart-product.model';
import { Product } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css']
})
export class ProductCardComponent implements OnInit {
  loggedIn = false;
  @Input() product!: Product;

  constructor(private productService: ProductService,
    private _toastService: ToastService,
    private translateService: TranslateService,
    private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.loggedInChanged.subscribe(loggedInFromSubject => {
      this.loggedIn = loggedInFromSubject;
    });
  }

  onAddToCart(productClicked: Product) {
    const cartItemsSS = sessionStorage.getItem("cartItems");
    let cartItems: CartProduct[] = [];
    if (cartItemsSS) {
      cartItems = JSON.parse(cartItemsSS);
    }                                                               3
    const index = cartItems.findIndex(element => element.product.id === productClicked.id);
    if (index >= 0) {
      cartItems[index].quantity++; 
    } else {
      const parcelMachineIndex = cartItems.findIndex(element => element.product.id === 11110000);
      if (parcelMachineIndex >= 0) {
        cartItems.splice(parcelMachineIndex,0,{ product: productClicked, quantity: 1 });
      } else {
        cartItems.push({ product: productClicked, quantity: 1 });
      }
    }

    sessionStorage.setItem("cartItems", JSON.stringify(cartItems));
    this.productService.cartChanged.next(true);
    this._toastService.success(this.translateService.instant('Edukalt ') + 
          productClicked.name + 
          this.translateService.instant(' ostukorvi lisatud'));
  }

}
