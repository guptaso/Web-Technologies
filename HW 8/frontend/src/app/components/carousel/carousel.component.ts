import { Component, OnInit } from '@angular/core';
import {PostsService} from '../../services/posts.service';

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.css']
})

export class CarouselComponent implements OnInit {
  public posts:any;
  public mobile: Boolean = true;
  constructor(private postsService: PostsService) { }

  ngOnInit(): void {
    if (window.screen.width < 500) {
      this.mobile = false;
    }
    this.fetchData();
  }

  fetchData(){
    this.postsService.getCarouselItems().subscribe(res =>{

      let p: any;
      p = res;
      p = p["results"];

      var arr = [] as any;

      let count = Math.min(p.length, 5);

      for (const d of (p as any)) {
        if (count == 0){
          break;
        } else if (d.backdrop_path.length>1) {
          d.backdrop_path = 'https://image.tmdb.org/t/p/original' + d.backdrop_path;
          arr.push(d)
          count -= 1;
        }
      }
      this.posts = arr;

    })
  }

  route(event: any, post:any){
    console.log(post)
    location.href = '/watch/movie/' + post.id;
  }
}
