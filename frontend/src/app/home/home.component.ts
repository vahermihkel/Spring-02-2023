import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  country = "ee"; // {countryName: "Estonia", countryCode: "ee"}
  parcelMachines: any = {omnivaPMs: [], smartpostPMs: []}; // HTMLs ta ei oota ära API päringuid
  products: any = [];

  constructor(private http: HttpClient) {} // failide (moodulite ühendamiseks): Http moodul on Http päringute tegemiseks

  ngOnInit() { // sissekirjutatud funktsioon, mis läheb ALATI käima kui siia lehele tullakse
    // system.out.println
    console.log("olen home lehel"); 
    this.getParcelMachines();
    this.getProducts();
    // brauseris parem klõps -> inspect -> console
  }

  changeCountry(newCountry: string) {
    this.country = newCountry;
    this.getParcelMachines();
  }

  private getParcelMachines() {
    this.http.get<any>("http://localhost:8080/parcelmachines/" + this.country).subscribe(res =>
      this.parcelMachines = res
    );
  }

  private getProducts() {
    this.http.get<any>("http://localhost:8080/product",).subscribe(res =>
      this.products = res
    );
  }
}
