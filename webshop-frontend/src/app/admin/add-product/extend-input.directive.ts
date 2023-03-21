import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[extendInput]'
})
export class ExtendInputDirective {

  constructor(public element: ElementRef) { 
  }

  @HostListener('input')
  adjust() {
    const element = this.element.nativeElement;
    element.style.overflow = "hidden";
    element.style.height = 'auto';
    element.style.height = element.scrollHeight + "px";
  }
}
