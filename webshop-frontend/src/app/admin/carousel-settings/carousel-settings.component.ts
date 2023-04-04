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
    this.carouselPictureService.getCarouselPictures().subscribe(res => {
      this.images = res;
    })
  }

  onSubmit(form: NgForm) {
    const newCarouselPicture = new CarouselPicture(form.value.imgName);
    this.carouselPictureService.addCarouselPicture(newCarouselPicture).subscribe(res => {
      this.images = res;
    })
  }

  onDeleteImg(image: CarouselPicture) {
    this.carouselPictureService.deleteCarouselPicture(image).subscribe((res) => {
      this.images = res;
    });
  }
}
