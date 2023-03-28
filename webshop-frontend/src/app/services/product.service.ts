import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, tap } from 'rxjs';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  dbUrl = "http://localhost:8080/product";
  cartChanged = new BehaviorSubject(true);

  constructor(private http: HttpClient) { }

  getProductsFromDb() {
    return this.http.get<any>(this.dbUrl).pipe(
      map(response => {
        return response.content;
      })
    );
  }

  addProductToDb(newProduct: Product) {
    return this.http.post(this.dbUrl, newProduct);
  }

  updateProductsInDb(updatedProducts: Product[]) {
    return this.http.put(this.dbUrl, updatedProducts);
  }

  // TODO: Delete product from backend

  // TODO: Get one product from backend

  // TODO: Edit one product from backend
}
