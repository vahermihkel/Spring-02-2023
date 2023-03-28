import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-view-products',
  templateUrl: './view-products.component.html',
  styleUrls: ['./view-products.component.css']
})
export class ViewProductsComponent implements OnInit {
  descriptionWordCount = 5;
  products: Product[] = [];
  originalProducts: Product[] = [];
  searchedProduct: string = "";

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.productService.getProductsFromDb().subscribe(response => { 
        this.products = response;
        this.originalProducts = response;
    }); 
  }

  onFilterProducts() {
    this.products = this.originalProducts.filter(element => 
        element.name.toLowerCase().indexOf(this.searchedProduct.toLowerCase()) >= 0 ||
        element.description.toLowerCase().indexOf(this.searchedProduct.toLowerCase()) >= 0 ||
        element.id.toString().indexOf(this.searchedProduct.toLowerCase()) >= 0
        );
  }

  onChangeProductActive(product: Product) {
    product.active = !product.active;
    // TODO: Edit one product from backend
  }

}