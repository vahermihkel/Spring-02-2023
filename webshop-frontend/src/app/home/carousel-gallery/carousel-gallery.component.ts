import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-carousel-gallery',
  templateUrl: './carousel-gallery.component.html',
  styleUrls: ['./carousel-gallery.component.css']
})
export class CarouselGalleryComponent implements OnInit {
  images: any[] = [];
  dbUrl = "";

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    // TODO: Get images from backend
    // this.http.get<{imgName: string}[]>(this.dbUrl).subscribe(imagesFromDb => {
    //   this.images = imagesFromDb;
    // });
  }

}
