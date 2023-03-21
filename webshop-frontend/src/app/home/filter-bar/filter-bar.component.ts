import { Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product.model';

@Component({
  selector: 'app-filter-bar',
  templateUrl: './filter-bar.component.html',
  styleUrls: ['./filter-bar.component.css']
})
export class FilterBarComponent implements OnInit {
  selectedCategory = "";
  categories: string[] = [];
  @Input() products!: Product[];
  @Input() originalProducts!: Product[];

  constructor() { }

  ngOnInit(): void {
    console.log(this.products);
    this.categories = this.products.map(element => element.category);
    this.categories = [...new Set(this.categories)];
    console.log(this.products);
  }

  onFilterByCategory(category: string) {
    this.selectedCategory = category;
    if (category === '') {
      this.products = this.originalProducts;
    } else {
      this.products = this.originalProducts.filter(element => element.category === category);
    }
  }

}
