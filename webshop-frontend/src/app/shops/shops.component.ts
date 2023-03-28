import { HttpClient } from '@angular/common/http';
import { Component, OnInit, AfterViewInit, OnDestroy, Output, EventEmitter, ViewChild } from '@angular/core';
import { MapComponent } from './map/map.component';
import * as L from 'leaflet';

@Component({
  selector: 'app-shops',
  templateUrl: './shops.component.html',
  styleUrls: ['./shops.component.css']
})
export class ShopsComponent implements OnInit, AfterViewInit  {
  dbUrl = "";
  shops: {shopName: string, 
    latitude: number, 
    longitude: number, openTimes: string}[] = [];
  @ViewChild(MapComponent) mapComponent!: MapComponent;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    // TODO: Get images from backend
    // this.http.get<{shopName: string, 
    //   latitude: number, 
    //   longitude: number, openTimes: string}[]>(this.dbUrl).subscribe(shopsFromDb => {
    //   this.shops = shopsFromDb;
    //   this.mapComponent.initMap();
    //   this.mapComponent.shopsObservable.next(this.shops);
    // });
  }

  onZoomShop(shopName: string) {
    const shopFound = this.shops.find(element => element.shopName === shopName);
    if (shopFound) {
      this.mapComponent.map.setView(L.latLng([shopFound.latitude, shopFound.longitude]),13);
    } else {
      this.mapComponent.map.setView(L.latLng([59.4341, 24.7489]),11);
    }
  }
  
}
