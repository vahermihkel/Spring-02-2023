import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/app/models/category.model';
import { Product } from 'src/app/models/product.model';
import { CategoryService } from 'src/app/services/category.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {
  products: Product[] = [];

  product!: Product;
  editProductForm!: FormGroup;
  categories: Category[] = [];

  constructor(private route: ActivatedRoute,  
    private router: Router,
    private productService: ProductService,
    private categoryService: CategoryService) { } 

  ngOnInit(): void {
    const productId = this.route.snapshot.paramMap.get("productId");
    if (productId) {
      this.getProductsFromDb(productId);
      this.getCategoriesFromDb();
    }
  }

  private getProductsFromDb(productId: string) {
    this.productService.getAdminProductsFromDb().subscribe(response => { 
      this.products = response;
      const productFound = this.products.find(element => Number(element.id) === Number(productId));
      if (productFound) {
        this.product = productFound;
        this.initEditForm();
      }
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
    this.categoryService.getCategoriesFromDb().subscribe(categoriesFromDb => {
      this.categories = categoriesFromDb;
    });
  }

  onSubmit() {
    // const index = this.products.indexOf(this.product);
    // this.products[index] = this.editProductForm.value;
    // this.product = this.editProductForm.value;
    const val = this.editProductForm.value;
    const newProduct = new Product(this.product.id, val.name, val.url, val.price,
      val.description, val.active, this.product.stock, new Category("", val.category));
    // TODO: Edit product
    this.productService.updateProduct(newProduct).subscribe(() => {
      // this.products = res;
      // constructoris Router --> import angular/router
      this.router.navigateByUrl("/admin/halda");
    });
  }

}
