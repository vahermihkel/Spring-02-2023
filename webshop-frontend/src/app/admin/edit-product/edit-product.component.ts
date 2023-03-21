import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {
  products: Product[] = [];
  dbUrl = "https://webshop-03-22-default-rtdb.europe-west1.firebasedatabase.app/products.json";
  categoriesDbUrl = "https://webshop-03-22-default-rtdb.europe-west1.firebasedatabase.app/categories.json";

  product!: Product;
  editProductForm!: FormGroup;
  categories: {categoryName: string}[] = [];

  constructor(private route: ActivatedRoute,  
    private http: HttpClient,
    private router: Router,
    private productService: ProductService) { } 

  ngOnInit(): void {
    const productId = this.route.snapshot.paramMap.get("productId");
    if (productId) {
      this.getProductsFromDb(productId);
    }
    this.getCategoriesFromDb();
  }

  private getProductsFromDb(productId: string) {
    this.productService.getProductsFromDb().subscribe(response => { 
      for (const key in response) {
        this.products.push(response[key]);
      }
      const productFound = this.products.find(element => Number(element.id) === Number(productId));
      if (productFound) {
        this.product = productFound;
      }
      this.initEditForm();
    }); 
  }

  private initEditForm() {
    this.editProductForm = new FormGroup({
      id: new FormControl(this.product.id),
      name: new FormControl(this.product.name),
      price: new FormControl(this.product.price),
      imgSrc: new FormControl(this.product.imgSrc),
      category: new FormControl(this.product.category),
      description: new FormControl(this.product.description),
      isActive: new FormControl(this.product.isActive),
    })
  } 

  private getCategoriesFromDb() {
    this.http.get<{categoryName: string}[]>(this.categoriesDbUrl).subscribe(categoriesFromDb => {
      const newArray = [];
      for (const key in categoriesFromDb) {
        newArray.push(categoriesFromDb[key]);
      }
      this.categories = newArray;
    });
  }

  onSubmit() {
    const queueNumber = this.products.indexOf(this.product);
    this.products[queueNumber] = this.editProductForm.value;
    this.productService.updateProductsInDb(this.products).subscribe(()=>this.router.navigateByUrl("/admin/halda") );
  }

}
