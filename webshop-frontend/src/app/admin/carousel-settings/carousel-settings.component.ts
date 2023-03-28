import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-carousel-settings',
  templateUrl: './carousel-settings.component.html',
  styleUrls: ['./carousel-settings.component.css']
})
export class CarouselSettingsComponent implements OnInit {
  dbUrl = "";
  images: {imgName: string}[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    // TODO: Get images from backend
    // this.http.get<{imgName: string}[]>(this.dbUrl).subscribe(imagesFromDb => {
    //   this.images = imagesFromDb;
    // });
  }

  onSubmit(form: NgForm) {
    const image = "https://picsum.photos/id/" + form.value.imgName + "/900/300";
    // TODO : Send new image to backend
    // this.http.post(this.dbUrl,{imgName: image}).subscribe(() => {
    //   this.images.push({imgName: image});
    // });
  }

  onDeleteImg(image: {imgName: string}) {
    const index = this.images.findIndex(element => element.imgName === image.imgName);
    this.images.splice(index,1);
    // TODO : Delete image from backend
  }
}
