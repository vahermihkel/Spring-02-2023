import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Product } from 'src/app/models/product.model';
import { CategoryService } from 'src/app/services/category.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css'],
  animations: [
    trigger('productActive', [
      state('inactive', style({
        backgroundColor: 'red'
      })),
      state('active', style({
        transform: 'translateX(50px)',
        backgroundColor: 'green'
      })),
      transition('inactive <=> active', [
        animate('1s')
      ])
    ]),
  ],
})
export class AddProductComponent implements OnInit {
  // productId!: number;
  // products: Product[] = [];
  // idUnique = false;
  categories: {categoryName: string}[] = [];
  // selectedFile!: File;
  
  isActive = false;

  toggle() {
    this.isActive = !this.isActive;
  }

  constructor(private productService: ProductService,
    private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.categoryService.getCategoriesFromDb().subscribe(categoriesFromDb => {
      const newArray = [];
      for (const key in categoriesFromDb) {
        newArray.push(categoriesFromDb[key]);
      }
      this.categories = newArray;
    });

    // this.productService.getProductsFromDb().subscribe(response => { 
    //   this.products = response;
    // }); 
  }

  // onCheckIdUniqueness() {
  //   const index = this.products.findIndex(element => element.id === this.productId );
  //   if (index >= 0) {
  //     this.idUnique = false;
  //   } else {
  //     this.idUnique = true;
  //   }
  // }

  // handleFileInput(event: any) {
  //   this.selectedFile = <File>event.target.files[0];
  // }

  onSubmit(addProductForm: NgForm) {
    const val = addProductForm.value;
    const newProduct = new Product(val.id, val.name, val.url, val.price, val.category,
      val.description, val.active);
    this.productService.addProductToDb(newProduct).subscribe();
    addProductForm.reset();
  }
}
