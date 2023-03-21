import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-shops-settings',
  templateUrl: './shops-settings.component.html',
  styleUrls: ['./shops-settings.component.css']
})
export class ShopsSettingsComponent implements OnInit {
  dbUrl = "https://webshop-03-22-default-rtdb.europe-west1.firebasedatabase.app/shops.json";
  shops: {shopName: string, 
    latitude: number, 
    longitude: number, openTimes: string}[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<{shopName: string, 
      latitude: number, 
      longitude: number, openTimes: string}[]>(this.dbUrl).subscribe(shopsFromDb => {
      const newArray = [];
      for (const key in shopsFromDb) {
        newArray.push(shopsFromDb[key]);
      }
      this.shops = newArray;
    });
  }

  onSubmit(form: NgForm) {
    this.http.post(this.dbUrl, form.value).subscribe();
    this.shops.push(form.value);
  }
}
