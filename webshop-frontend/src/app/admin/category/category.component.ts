import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Category } from 'src/app/models/category.model';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css'],
})
export class CategoryComponent implements OnInit {
  dbUrl = '';
  categories: Category[] = [];

  // @Autowired
  // CategoryService categoryService;
  constructor(private categoryService: CategoryService) {}

  ngOnInit(): void {
    // TODO: Get categories from backend
    // this.http.get<{categoryName: string}[]>(this.dbUrl).subscribe(categoriesFromDb => {
    //   this.categories = categoriesFromDb;
    // });
    this.categoryService.getCategoriesFromDb().subscribe((res) => {
      this.categories = res;
    });
  }

  // {categoryName: "teretere"};
  onSubmit(addCategoryForm: NgForm) {
    // TODO: Add category to backend
    // this.http.post(this.dbUrl,addCategoryForm.value).subscribe(() => {
    //   this.categories.push(addCategoryForm.value);
    // });        // OTSE Formi väärtusi (võti-väärtus paarid) ei panegi
    const newCategory = new Category(addCategoryForm.value.name);
    this.categoryService.addCategoryToDb(newCategory).subscribe((res) => {
      this.categories = res;
    });
  }

  onDeleteCategory(category: Category) {
    // const index = this.categories.findIndex(element => element.categoryName === category.categoryName);
    // this.categories.splice(index,1);
    // TODO: Delete category from backend
    this.categoryService.deleteCategoryFromDb(category).subscribe((res) => {
      this.categories = res;
    });
  }
}
