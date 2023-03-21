import { Component, Input, OnInit } from '@angular/core';
import * as L from 'leaflet';
import { BehaviorSubject, Subject } from 'rxjs';
const iconRetinaUrl = 'assets/marker-icon-2x.png';
const iconUrl = 'assets/marker-icon.png';
const shadowUrl = 'assets/marker-shadow.png';
const iconDefault = L.icon({
  iconRetinaUrl,
  iconUrl,
  shadowUrl,
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  tooltipAnchor: [16, -28],
  shadowSize: [41, 41]
});
L.Marker.prototype.options.icon = iconDefault;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  @Input() shops!: any[];
  shopsObservable = new Subject<any[]>();
  map!: L.Map;
  private lng = 59.4341;
  private lat = 24.7489;
  private zoom = 11;
  private marker!: L.Marker;

  initMap(): void {
    this.map = L.map('map', {
      center: [ this.lng, this.lat ],
      zoom: this.zoom
    });

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });

    tiles.addTo(this.map);

    this.shopsObservable.subscribe(shops => {
      shops.forEach(element => {
        this.marker = L.marker([element.latitude, element.longitude]);  
        this.marker.addTo(this.map);
        this.marker.bindPopup("<div>"+element.shopName+
          "</div><br><div>Lahtioleku aeg: "+
          element.openTimes+"</div>");
      })
    })

  }

  constructor() { }

  ngOnInit(): void {
  }

}
