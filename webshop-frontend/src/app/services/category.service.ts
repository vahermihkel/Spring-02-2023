import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  categoriesDbUrl = "https://webshop-03-22-default-rtdb.europe-west1.firebasedatabase.app/categories.json";

  constructor(private http: HttpClient) { }

  getCategoriesFromDb() {
    return this.http.get<{categoryName: string}[]>(this.categoriesDbUrl);
  }
}
