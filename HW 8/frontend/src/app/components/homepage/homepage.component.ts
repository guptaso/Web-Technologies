import { Component, OnInit } from '@angular/core';
import {PostsService} from '../../services/posts.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  public popularMovies:any;
  public topRatedMovies: any;
  public trendingMovies: any;
  public popularTV: any;
  public topRatedTV: any;
  public trendingTV: any;
  public current: any;

  constructor(private postsService: PostsService) { }

  ngOnInit(): void {
    this.fetchPopularMovies();
    this.fetchTopRatedMovies();
    this.fetchTrendingMovies();
    this.fetchPopularTV();
    this.fetchTopRatedTV();
    this.fetchTrendingTV();
    this.fetchCurrentMovies();
  }
  fetchCurrentMovies(){
    var postString = localStorage.getItem("continueWatching");
    var posts: any = {};
    var toReturn:any = [];
    if (postString) {
      posts = JSON.parse(postString);
      for (const post in posts){
        toReturn.push(posts[post])
      }
    }
    this.current = toReturn;
  }

  fetchPopularMovies(){
    this.postsService.getPopularMovies().subscribe(res => {
      let p: any;
      p = res;
      p = p["results"];
      this.popularMovies = p;
    })
  }

  fetchTopRatedMovies(){
    this.postsService.getTopRatedMovies().subscribe(res => {
      let p: any;
      p = res;
      p = p["results"];
      this.topRatedMovies = p;
    })
  }

  fetchTrendingMovies(){
    this.postsService.getTrendingMovies().subscribe(res => {
      let p: any;
      p = res;
      p = p["results"];
      this.trendingMovies = p;
    })
  }

  fetchPopularTV(){
    this.postsService.getPopularTV().subscribe(res => {
      let p: any;
      p = res;
      p = p["results"];
      this.popularTV = p;

    })
  }

  fetchTopRatedTV(){
    this.postsService.getTopRatedTV().subscribe(res => {
      let p: any;
      p = res;
      p = p["results"];
      this.topRatedTV = p;
    })
  }

  fetchTrendingTV(){
    this.postsService.getTrendingTV().subscribe(res => {
      let p: any;
      p = res;
      p = p["results"];
      this.trendingTV = p;
    })
  }

}
