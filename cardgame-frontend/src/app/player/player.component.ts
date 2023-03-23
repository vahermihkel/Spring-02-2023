import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Game } from '../models/Game';
import { Player } from '../models/Player';

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.scss'],
})
export class PlayerComponent {
  // ts -> kooloni järgi tüüp     string[]
  players: Player[] = [];
  dbPlayers: Player[] = [];
  games: Game[] = [];
  searchInput = "";

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.getPlayers();
    this.getGames();
  }

  searchPlayers() {
    console.log(this.searchInput);
    this.players = this.dbPlayers.filter(player => 
      player.name.includes(this.searchInput) ||
      // player.lastName.includes(this.searchInput) ||
      // player.description.includes(this.searchInput) ||
      player.highScore > Number(this.searchInput)
      );
  }

  getPlayers() {
    this.http
      .get<Player[]>(
        'http://localhost:8080/players'
      )
      .subscribe((res) => {
        this.players = res;
        this.dbPlayers = res;
      });
  }

  getGames() {
    this.http.get<Game[]>('http://localhost:8080/games').subscribe((res) => {
      this.games = res.filter(el => el.player !== null);
    });
  }

  getPlayerGames(playerId: String) {
    this.http
      .get<Game[]>('http://localhost:8080/game/' + playerId)
      .subscribe((res) => {
        this.games = res;
      });
    // this.http.get<any[]>('http://localhost:8080/games').subscribe((res) => {
    //   this.games = res.filter(e => e.player?.name === playerId);
    // });
    // this.games = this.games.filter(e => e.player?.name === playerId);
  }
}
