import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-carousel-settings',
  templateUrl: './carousel-settings.component.html',
  styleUrls: ['./carousel-settings.component.css']
})
export class CarouselSettingsComponent implements OnInit {
  dbUrl = "https://webshop-03-22-default-rtdb.europe-west1.firebasedatabase.app/images.json";
  images: {imgName: string}[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<{imgName: string}[]>(this.dbUrl).subscribe(imagesFromDb => {
      const newArray = [];
      for (const key in imagesFromDb) {
        newArray.push(imagesFromDb[key]);
      }
      this.images = newArray;
    });
  }

  onSubmit(form: NgForm) {
    const image = "https://picsum.photos/id/" + form.value.imgName + "/900/300";
    this.http.post(this.dbUrl,{imgName: image}).subscribe(() => {
      this.images.push({imgName: image});
    });
  }

  onDeleteImg(image: {imgName: string}) {
    const index = this.images.findIndex(element => element.imgName === image.imgName);
    this.images.splice(index,1);
    this.http.put(this.dbUrl,this.images).subscribe();
  }
}
