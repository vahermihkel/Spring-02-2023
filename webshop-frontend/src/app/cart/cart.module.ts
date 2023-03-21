import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartProductComponent } from './cart-product/cart-product.component';
import { CartComponent } from './cart.component';
import { ParcelMachinesComponent } from './parcel-machines/parcel-machines.component';
import { SharedModule } from '../shared/shared.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    ParcelMachinesComponent,
    CartProductComponent,
    CartComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    SharedModule
  ]
})
export class CartModule { }
