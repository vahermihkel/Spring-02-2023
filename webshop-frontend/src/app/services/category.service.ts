import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Category } from '../models/category.model';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private categoriesDbUrl = "http://localhost:8080/category";

  constructor(private http: HttpClient) { }

  getCategoriesFromDb() {
    return this.http.get<Category[]>(this.categoriesDbUrl);
  }

  addCategoryToDb(newCategory: Category) {
    return this.http.post<Category[]>(this.categoriesDbUrl, newCategory);
  }
}
