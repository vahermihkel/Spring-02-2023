import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from '../app-routing.module';
import { MapComponent } from './map/map.component';
import { ShopsComponent } from './shops.component';

@NgModule({
  declarations: [
    ShopsComponent,
    MapComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
  ]
})
export class ShopsModule { }
