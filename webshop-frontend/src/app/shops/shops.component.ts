import { Component, OnInit, AfterViewInit, OnDestroy, Output, EventEmitter, ViewChild } from '@angular/core';
import { MapComponent } from './map/map.component';
import * as L from 'leaflet';
import { Shop } from '../models/shop.model';
import { ShopService } from '../services/shop.service';

@Component({
  selector: 'app-shops',
  templateUrl: './shops.component.html',
  styleUrls: ['./shops.component.css']
})
export class ShopsComponent implements OnInit, AfterViewInit  {
  shops: Shop[] = [];
  @ViewChild(MapComponent) mapComponent!: MapComponent;

  constructor(private shopService: ShopService) { }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.shopService.getShopsFromDb().subscribe(shopsFromDb => {
       this.shops = shopsFromDb;
       this.mapComponent.initMap();
       this.mapComponent.shopsObservable.next(this.shops);
     });
  }

  onZoomShop(shopName: string) {
    const shopFound = this.shops.find(element => element.name === shopName);
    if (shopFound) {
      this.mapComponent.map.setView(L.latLng([shopFound.latitude, shopFound.longitude]),17);
    } else {
      this.mapComponent.map.setView(L.latLng([59.4341, 24.7489]),11);
    }
  }
  
}