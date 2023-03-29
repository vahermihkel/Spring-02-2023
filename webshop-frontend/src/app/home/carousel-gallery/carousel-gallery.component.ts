import { Component, OnInit } from '@angular/core';
import { CarouselPicture } from 'src/app/models/carousel-picture.model';
import { CarouselPictureService } from 'src/app/services/carousel-picture.service';

@Component({
  selector: 'app-carousel-gallery',
  templateUrl: './carousel-gallery.component.html',
  styleUrls: ['./carousel-gallery.component.css']
})
export class CarouselGalleryComponent implements OnInit {
  images: CarouselPicture[] = [];

  constructor(private carouselPictureService: CarouselPictureService) { }

  ngOnInit(): void {
    this.carouselPictureService.getCarouselPictures().subscribe(res => {
      this.images = res;
    })
  }

}
