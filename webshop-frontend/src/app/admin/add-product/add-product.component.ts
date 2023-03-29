import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Category } from 'src/app/models/category.model';
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
  categories: Category[] = [];  
  active = false;

  toggle() {
    this.active = !this.active;
  }

  constructor(private productService: ProductService,
    private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.categoryService.getCategoriesFromDb().subscribe(categoriesFromDb => {
      this.categories = categoriesFromDb;
    });
  }

  onSubmit(addProductForm: NgForm) {
    const val = addProductForm.value;
    const newProduct = new Product(val.id, val.name, val.url, val.price,
      val.description, val.active, 0, new Category("", val.category));
    this.productService.addProductToDb(newProduct).subscribe();
    addProductForm.reset();
  }
}
