import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ThousandSeparatorPipe } from '../pipes/thousand-separator.pipe';

@NgModule({
  declarations: [
    ThousandSeparatorPipe
  ],
  imports: [
    CommonModule
  ],
  exports: [
    ThousandSeparatorPipe
  ]
})
export class SharedModule { }
