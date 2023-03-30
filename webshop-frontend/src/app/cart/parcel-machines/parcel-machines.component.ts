import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CartProduct } from 'src/app/models/cart-product.model';
import { Category } from 'src/app/models/category.model';
import { ProductService } from 'src/app/services/product.service';
import { PmService } from './pm.service';

@Component({
  selector: 'app-parcel-machines',
  templateUrl: './parcel-machines.component.html',
  styleUrls: ['./parcel-machines.component.css']
})
export class ParcelMachinesComponent implements OnInit {
  // 3 nuppu HTMLi -> sisus Eesti, Läti, Leedu
  // uus funktsioon teha (võin ka 3 funktsiooni teha)
  // nupus uus API päring backendi
  parcelMachines: any[] = [];  // selle järgi
  smartpostMachines: any[] = []; // KODUS
  selectedParcelMachine: any;
  @Input() cartProducts: CartProduct[] = []; // andmed sisse
  @Output() calculateSumEvent: EventEmitter<any> = new EventEmitter();      // funktsioonid välja
  @Output() removeParcelMachineEvent: EventEmitter<CartProduct> = new EventEmitter();      // funktsioonid välja

  constructor(private pmService: PmService,
    private productService: ProductService) { }

  ngOnInit(): void {
    // TODO: Get parcel machines from backend
    // this.http.get<any[]>("https://www.omniva.ee/locations.json").subscribe(res => 
    //     this.parcelMachines = res);
    this.pmService.getParcelMachines("ee").subscribe(res => {
      this.parcelMachines = res.omnivaPMs;
      this.smartpostMachines = res.smartpostPMs;
    });
    this.selectedParcelMachine = sessionStorage.getItem("parcelMachine");
  }

  onParcelMachineSelected() {
    sessionStorage.setItem("parcelMachine", this.selectedParcelMachine);
    this.cartProducts.push({
    product: {id: 11110000,name:this.selectedParcelMachine,price:3.5,image: "assets/locker.png",category: new Category(""),description: "",active: true, stock: 1},
    quantity: 1
    });
    sessionStorage.setItem("cartItems", JSON.stringify(this.cartProducts));
    this.productService.cartChanged.next(true);
    this.calculateSumEvent.emit();
  }

  onUnselectParcelMachine() {
    this.selectedParcelMachine = "";
    sessionStorage.removeItem("parcelMachine");
    this.removeParcelMachineEvent.emit({
      product: {id: 11110000,name:this.selectedParcelMachine,price:3.5,image: "assets/locker.png",category: new Category(""),description: "",active: true, stock: 1},
      quantity: 1
    });
  }

}
