import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators'
@Injectable({
  providedIn: 'root'
})
export class PostsService {

  constructor(private httpClient: HttpClient) { }

  getCarouselItems() {
    let url = 'https://cedar-heaven-313505.wl.r.appspot.com/apis/posts/current';
    return this.httpClient.get(url)
  }

  getPopularMovies() {
    let url = 'https://cedar-heaven-313505.wl.r.appspot.com/apis/posts/popular/movie';
    return this.httpClient.get(url);
  }

  getTopRatedMovies() {
    let url = 'https://cedar-heaven-313505.wl.r.appspot.com/apis/posts/topRated/movie';
    return this.httpClient.get(url);
  }

  getTrendingMovies() {
    let url = 'https://cedar-heaven-313505.wl.r.appspot.com/apis/posts/trending/movie';
    return this.httpClient.get(url);
  }

  getPopularTV() {
    let url = 'https://cedar-heaven-313505.wl.r.appspot.com/apis/posts/popular/tv';
    return this.httpClient.get(url);
  }

  getTopRatedTV() {
    let url = 'https://cedar-heaven-313505.wl.r.appspot.com/apis/posts/topRated/tv';
    return this.httpClient.get(url);
  }

  getTrendingTV() {
    let url = 'https://cedar-heaven-313505.wl.r.appspot.com/apis/posts/trending/tv';
    return this.httpClient.get(url);
  }

  multiSearch(query: string) {
    let url = 'https://cedar-heaven-313505.wl.r.appspot.com/apis/posts/multisearch/' + query;
    return this.httpClient.get(url);
  }

  getVideo(type: string, id: string){
    let url = 'https://cedar-heaven-313505.wl.r.appspot.com/apis/posts/video/'+ type + '/' + id;
    return this.httpClient.get(url);
  }

  getDetails(type: string, id: string ){
    let url = 'https://cedar-heaven-313505.wl.r.appspot.com/apis/posts/detail/' + type + '/' + id ;
    return this.httpClient.get(url);
  }

  getRecommended(type: string, id: string ){
    let url = 'https://cedar-heaven-313505.wl.r.appspot.com/apis/posts/recommended/' + type + '/' + id;
    return this.httpClient.get(url);
  }

  getSimilar(type: string, id: string) {
    let url = "https://cedar-heaven-313505.wl.r.appspot.com/apis/posts/similar/" + type + '/' + id;
    return this.httpClient.get(url);
  }

  getCast(type: string, id: string) {
    let url = "https://cedar-heaven-313505.wl.r.appspot.com/apis/posts/cast/" + type + '/' + id;
    return this.httpClient.get(url);
  }

  getReview(type: string, id: string){
    let url = "https://cedar-heaven-313505.wl.r.appspot.com/apis/posts/review/" + type + '/' + id;
    return this.httpClient.get(url);
  }

  getCastDetails(id: string){
    let url = "https://cedar-heaven-313505.wl.r.appspot.com/apis/posts/castDetails/" + id;
    return this.httpClient.get(url);
  }

  getCastHandles(id: string){
    let url = "https://cedar-heaven-313505.wl.r.appspot.com/apis/posts/castExternalId/" + id;
    return this.httpClient.get(url);
  }

  getSearch(query: string){
    if (query.length > 0){
      let url = "https://cedar-heaven-313505.wl.r.appspot.com/apis/posts/multisearch/" + query;

      return this.httpClient.get<[any, string[]]>(url)
    } else {
      return []
    }

  }
}
