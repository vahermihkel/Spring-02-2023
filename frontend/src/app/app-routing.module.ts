import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CartComponent } from './cart/cart.component';
import { HomeComponent } from './home/home.component';

// path - mis järgneb localhost:4200 ---> component: HomeComponent.html .ts - dünaamika, .css - kujundus
// telia.ee
const routes: Routes = [
  // { path: "", redirectTo: "avaleht", pathMatch: "full" },
  { path: "", component: HomeComponent },
  { path: "ostukorv", component: CartComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
