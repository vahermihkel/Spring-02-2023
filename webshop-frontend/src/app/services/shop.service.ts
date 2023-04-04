import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Shop } from '../models/shop.model';

@Injectable({
  providedIn: 'root'
})
export class ShopService {
  dbBaseUrl: string = "http://localhost:8080/";

    constructor(private http: HttpClient) { }

    getShopsFromDb() {
        return this.http.get<Shop[]>(this.dbBaseUrl + "getshops");
    }

    addShopToDb(newShop: Shop) {
        return this.http.post<Shop[]>(this.dbBaseUrl + "addshop", newShop);
    }

    deleteShopFromDb(shop: Shop) {
        return this.http.delete<Shop[]>(
          this.dbBaseUrl + "deleteshop/" + shop.id
        );
    }
}
