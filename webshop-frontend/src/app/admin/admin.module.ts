import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddProductComponent } from './add-product/add-product.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { CarouselSettingsComponent } from './carousel-settings/carousel-settings.component';
import { CategoryComponent } from './category/category.component';
import { EditProductComponent } from './edit-product/edit-product.component';
import { ShopsSettingsComponent } from './shops-settings/shops-settings.component';
import { ViewProductsComponent } from './view-products/view-products.component';
import { HttpClient } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { AppRoutingModule } from '../app-routing.module';
import { HttpLoaderFactory } from '../app.module';
import { DescriptionShortenerPipe } from '../pipes/description-shortener.pipe';
import { SharedModule } from '../shared/shared.module';
import { ExtendInputDirective } from './add-product/extend-input.directive';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AdminHomeComponent,
    AddProductComponent,
    EditProductComponent,
    ViewProductsComponent,
    CarouselSettingsComponent,
    CategoryComponent,
    ShopsSettingsComponent,
    DescriptionShortenerPipe,
    ExtendInputDirective
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    BrowserAnimationsModule,
    TranslateModule.forRoot({
        loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
        }
    })
  ]
})
export class AdminModule { }
