import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'descriptionShortener'
})
export class DescriptionShortenerPipe implements PipeTransform {

  transform(value: string, wordCount: number): string {
    if (value == null) {
      return "Kirjeldus puudub";
    }
    return value.split(" ").slice(0,wordCount).join(" ");
  }

}
