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
  dbUrl = "";
  categoriesDbUrl = "";

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
      this.products = response;
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
      image: new FormControl(this.product.image),
      category: new FormControl(this.product.category),
      description: new FormControl(this.product.description),
      active: new FormControl(this.product.active)
    })
  } 

  private getCategoriesFromDb() {
    // TODO: Get categories from db
    // this.http.get<{categoryName: string}[]>(this.categoriesDbUrl).subscribe(categoriesFromDb => {
    //   this.categories = categoriesFromDb;
    // });
  }

  onSubmit() {
    const queueNumber = this.products.indexOf(this.product);
    this.products[queueNumber] = this.editProductForm.value;
    // TODO: Edit product
  }

}
