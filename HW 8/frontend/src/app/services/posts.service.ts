import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators'
@Injectable({
  providedIn: 'root'
})
export class PostsService {

  constructor(private httpClient: HttpClient) { }

  getCarouselItems() {
    let url = 'http://localhost:8080/apis/posts/current';
    return this.httpClient.get(url)
  }

  getPopularMovies() {
    let url = 'http://localhost:8080/apis/posts/popular/movie';
    return this.httpClient.get(url);
  }

  getTopRatedMovies() {
    let url = 'http://localhost:8080/apis/posts/topRated/movie';
    return this.httpClient.get(url);
  }

  getTrendingMovies() {
    let url = 'http://localhost:8080/apis/posts/trending/movie';
    return this.httpClient.get(url);
  }

  getPopularTV() {
    let url = 'http://localhost:8080/apis/posts/popular/tv';
    return this.httpClient.get(url);
  }

  getTopRatedTV() {
    let url = 'http://localhost:8080/apis/posts/topRated/tv';
    return this.httpClient.get(url);
  }

  getTrendingTV() {
    let url = 'http://localhost:8080/apis/posts/trending/tv';
    return this.httpClient.get(url);
  }

  multiSearch(query: string) {
    let url = 'http://localhost:8080/apis/posts/multisearch/' + query;
    return this.httpClient.get(url);
  }

  getVideo(type: string, id: string){
    let url = 'http://localhost:8080/apis/posts/video/'+ type + '/' + id;
    return this.httpClient.get(url);
  }

  getDetails(type: string, id: string ){
    let url = 'http://localhost:8080/apis/posts/detail/' + type + '/' + id ;
    return this.httpClient.get(url);
  }

  getRecommended(type: string, id: string ){
    let url = 'http://localhost:8080/apis/posts/recommended/' + type + '/' + id;
    return this.httpClient.get(url);
  }

  getSimilar(type: string, id: string) {
    let url = "http://localhost:8080/apis/posts/similar/" + type + '/' + id;
    return this.httpClient.get(url);
  }

  getCast(type: string, id: string) {
    let url = "http://localhost:8080/apis/posts/cast/" + type + '/' + id;
    return this.httpClient.get(url);
  }

  getReview(type: string, id: string){
    let url = "http://localhost:8080/apis/posts/review/" + type + '/' + id;
    return this.httpClient.get(url);
  }

  getCastDetails(id: string){
    let url = "http://localhost:8080/apis/posts/castDetails/" + id;
    return this.httpClient.get(url);
  }

  getCastHandles(id: string){
    let url = "http://localhost:8080/apis/posts/castExternalId/" + id;
    return this.httpClient.get(url);
  }

  getSearch(query: string){
    if (query.length > 0){
      let url = "http://localhost:8080/apis/posts/multisearch/" + query;

      return this.httpClient.get<[any, string[]]>(url)
    } else {
      return []
    }

  }
}
