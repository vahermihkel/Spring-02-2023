import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Shop } from 'src/app/models/shop.model';
import { ShopService } from 'src/app/services/shop.service';

@Component({
  selector: 'app-shops-settings',
  templateUrl: './shops-settings.component.html',
  styleUrls: ['./shops-settings.component.css']
})
export class ShopsSettingsComponent implements OnInit {
  
  shops: Shop[] = [];

  constructor(private shopService: ShopService) { }

  ngOnInit(): void {
    this.shopService.getShopsFromDb().subscribe(res => {
      this.shops = res;
    });
  }

  onSubmit(addShopForm: NgForm) {
    const newShop = new Shop(
      addShopForm.value.name,
      addShopForm.value.address,
      addShopForm.value.openTime,
      addShopForm.value.closeTime,
      addShopForm.value.latitude,
      addShopForm.value.longitude
    );
    this.shopService.addShopToDb(newShop).subscribe((res) => {
      this.shops = res;
    });
  }

  onDeleteShop(shop: Shop) {
    this.shopService.deleteShopFromDb(shop).subscribe((res) => {
      this.shops = res;
    });
  }
}
