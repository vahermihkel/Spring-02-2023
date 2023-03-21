import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarouselGalleryComponent } from './carousel-gallery/carousel-gallery.component';
import { FilterBarComponent } from './filter-bar/filter-bar.component';
import { HomeComponent } from './home.component';
import { ProductCardComponent } from './product-card/product-card.component';
import { SortButtonsComponent } from './sort-buttons/sort-buttons.component';
import { HttpClient } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { AngularToastifyModule } from 'angular-toastify';
import { AppRoutingModule } from '../app-routing.module';
import { HttpLoaderFactory } from '../app.module';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [
    HomeComponent,
    CarouselGalleryComponent,
    SortButtonsComponent,
    FilterBarComponent,
    ProductCardComponent,
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    NgbModule,
    AngularToastifyModule,
    SharedModule,
    TranslateModule.forRoot({
      loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
        }
    })
  ]
})
export class HomeModule { }
