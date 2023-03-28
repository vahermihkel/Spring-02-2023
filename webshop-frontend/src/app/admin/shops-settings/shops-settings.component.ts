import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-shops-settings',
  templateUrl: './shops-settings.component.html',
  styleUrls: ['./shops-settings.component.css']
})
export class ShopsSettingsComponent implements OnInit {
  dbUrl = "";
  shops: {shopName: string, 
    latitude: number, 
    longitude: number, openTimes: string}[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    // TODO: Get shops from db
    // this.http.get<{shopName: string, 
    //   latitude: number, 
    //   longitude: number, openTimes: string}[]>(this.dbUrl).subscribe(shopsFromDb => {
    //   this.shops = shopsFromDb;
    // });
  }

  onSubmit(form: NgForm) {
    // TODO: Add shop to db
    // this.http.post(this.dbUrl, form.value).subscribe();
    this.shops.push(form.value);
  }
}
