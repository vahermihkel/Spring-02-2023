import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent {
  name = '';
  card!: { suit: String; rank: String; value: Number } | undefined; // hüümärk laseb tühjana luua
  status = "";
  // "right", "wrong", "over", "guess"

  constructor(private http: HttpClient) {}

  enterName() {
    this.http.post('http://localhost:8080/player/' + this.name,
        {},{ responseType: 'text' })
          .subscribe((response) => {
            if (response === 'OK') {
              this.newRound();
            }
          });
  }

  guess(userGuess: string) {
    this.http
      .get('http://localhost:8080/guess/' + userGuess, { responseType: 'text' })
      .subscribe((response) => { 
        console.log(response);
        this.status = response;
      });
  }

  newRound() {
    this.http.get<{ suit: String; rank: String; value: Number }>(
              'http://localhost:8080/start'
            )
            .subscribe((card) => {
              console.log(card);
              this.card = card;
              this.status = "guess";
            });
  }

  newGame() {
    this.status = "";
    this.card = undefined;
  }

}
