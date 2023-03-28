import { Component, Input, OnInit } from '@angular/core';
import { Category } from 'src/app/models/category.model';
import { Product } from 'src/app/models/product.model';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-filter-bar',
  templateUrl: './filter-bar.component.html',
  styleUrls: ['./filter-bar.component.css'],
})
export class FilterBarComponent implements OnInit {
  selectedCategory = '';
  categories: Category[] = [];
  @Input() products!: Product[];
  @Input() originalProducts!: Product[];

  // Spring mõistes @Autowired - (Dependency Injection)
  // CategoryService categoryService;
  constructor(private categoryService: CategoryService) {}

  ngOnInit(): void {
    // this.categories = this.products.map(element => element.category !== undefined ? element.category.name : "");
    // this.categories.filter(element => element !== "");
    // this.categories = [...new Set(this.categories)];
    this.categoryService.getCategoriesFromDb().subscribe((data) => {
      this.categories = data;
    });
  }

  // Alkohol
  onFilterByCategory(category: string) {
    this.selectedCategory = category;
    if (category === '') {
      this.products = this.originalProducts;
    } else {
      this.products = this.originalProducts.filter(
        (element) =>
          element.category !== undefined &&
          element.category !== null &&
          element.category.name === category
      );
    }
  }
}
