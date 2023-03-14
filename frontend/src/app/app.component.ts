import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';
}

// ng new PROJEKTI_NIMI -- uue projekti loomiseks
// ng serve -- projekti käima panemiseks (peab olema projekti kausta, enne seda "cd PROJEKTI_NIMI")
// ng serve -o --- nii kui valmis saab, avab brauseris uue tabi
// ng generate component LEHE_NIMI  --- teeb valmis uue lehe