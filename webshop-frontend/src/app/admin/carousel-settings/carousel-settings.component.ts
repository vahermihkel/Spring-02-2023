import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { CarouselPicture } from 'src/app/models/carousel-picture.model';
import { CarouselPictureService } from 'src/app/services/carousel-picture.service';

@Component({
  selector: 'app-carousel-settings',
  templateUrl: './carousel-settings.component.html',
  styleUrls: ['./carousel-settings.component.css']
})
export class CarouselSettingsComponent implements OnInit {
  // dbUrl = "";
  images: CarouselPicture[] = [];

  constructor(private carouselPictureService: CarouselPictureService) { }

  ngOnInit(): void {
    // TODO: Get images from backend
    // this.http.get<{imgName: string}[]>(this.dbUrl).subscribe(imagesFromDb => {
    //   this.images = imagesFromDb;
    // });
    this.carouselPictureService.getCarouselPictures().subscribe(res => {
      this.images = res;
    })
  }

  onSubmit(form: NgForm) {
    // const image = "https://picsum.photos/id/" + form.value.imgName + "/900/300";
    // TODO : Send new image to backend
    // this.http.post(this.dbUrl,{imgName: image}).subscribe(() => {
    //   this.images.push({imgName: image});
    // });
    const newCarouselPicture = new CarouselPicture(form.value.imgName);
    this.carouselPictureService.addCarouselPicture(newCarouselPicture).subscribe(res => {
      this.images = res;
    })
  }

  onDeleteImg(image: CarouselPicture) {
    // const index = this.images.findIndex(element => element.imgName === image.imgName);
    // this.images.splice(index,1);
    // TODO : Delete image from backend
  }
}
