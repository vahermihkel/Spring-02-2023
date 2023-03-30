import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PmService {
  private dbUrl = "http://localhost:8080/parcelmachines/";

  constructor(private http: HttpClient) { }

  getParcelMachines(country: string) {
    return this.http.get<any>(this.dbUrl + country);
  }
}
