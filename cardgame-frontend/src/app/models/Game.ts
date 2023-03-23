import { Player } from "./Player";

export interface Game {
  id: number;
  correctGuesses: number;
  duration: number;
  player?: Player;
}