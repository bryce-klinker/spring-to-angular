import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Movie} from "./movie";
import {Director} from "./director";
import {Actor} from "./actor";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  movies: Movie[];
  selectedMovie: Movie;
  movieActors: Actor[];
  movieDirector: Director;

  actors: Actor[];

  directors: Director[];

  constructor(private httpClient: HttpClient) {

  }

  public ngOnInit() {
    this.httpClient.get<Movie[]>('http://localhost:8080/movies')
      .subscribe(movies => this.movies = movies);

    this.httpClient.get<Actor[]>("http://localhost:8080/actors")
      .subscribe(actors => this.actors = actors);

    this.httpClient.get<Director[]>('http://localhost:8080/directors')
      .subscribe(directors => this.directors = directors);
  }

  public selectMovie(movie: Movie) {
    this.selectedMovie = movie;

    this.httpClient.get<Actor[]>("http://localhost:8080/movies/" + movie.id + "/actors")
      .subscribe(actors => this.movieActors = actors);

    this.httpClient.get<Director>("http://localhost:8080/movies/" + movie.id + "/director")
      .subscribe(director => this.movieDirector = director);
  }
}
