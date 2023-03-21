import { Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product.model';

@Component({
  selector: 'app-sort-buttons',
  templateUrl: './sort-buttons.component.html',
  styleUrls: ['./sort-buttons.component.css']
})
export class SortButtonsComponent implements OnInit {
  @Input('productsToBeSorted') products: Product[] = [];

  constructor() { }

  ngOnInit(): void {
  }

  onSortAZ() {
    this.products.sort((a,b) => a.name.trim().localeCompare(b.name.trim()) );
  }

  onSortZA() {
    this.products.sort((a,b) => b.name.trim().localeCompare(a.name.trim()) );
  }

  onSortPriceAsc() {
    this.products.sort((a,b) => a.price - b.price );
  }

  onSortPriceDesc() {
    this.products.sort((a,b) => b.price - a.price );
  }

}
