import { Component, OnInit,Input } from '@angular/core';

@Component({
  selector: 'app-generic-carousel',
  templateUrl: './generic-carousel.component.html',
  styleUrls: ['./generic-carousel.component.css']
})

export class GenericCarouselComponent implements OnInit {
  @Input('dataReceived') dataReceived:any;
  @Input('title') title:string = "";
  @Input('type') type:string = "";

  public images:any = [];
  posts: any = [];
  public mobile: any = false;
  constructor() { }

  ngOnInit(): void {
    if (window.screen.width < 500) {
      this.mobile = true;
    }

  }

  ngOnChanges(): void {
    if (this.dataReceived && this.dataReceived.length > 0){
      if (this.title == "Continue Watching"){
        this.changeOrder();
      }
      this.formatImages();
      if (this.title == "Continue Watching"){
        let element = document.getElementById("continueW")
        if (element) {
          element.style.display = "block"
        }
      }
    } else if (this.title == "Continue Watching"){
      let element = document.getElementById("continueW")
      if (element) {
        element.style.display = "none"
      }
    }
  }
  changeOrder(){
    var newData: any = [];
    var order = localStorage.getItem("cwOrder");
    if (order){
      order = JSON.parse(order)
      if (order){
        for (let id of order) {
          for (let post of this.dataReceived){
            if (post.id == id) {
              newData.push(post);
              break;
            }
          }
        }
        this.dataReceived = newData;
      }

    }

  }
  formatImages(){
    if (this.dataReceived){
      var i = -1;
      var j = -1;
      var hold = [];
      for (let post of this.dataReceived) {

        if (!( this.title == "Continue Watching")){

          post.media_type = this.type;

        } else {

          if (post.title){
            post.media_type = 'movie';
          } else {
            post.media_type = 'tv';
          }
        }
        i += 1;
        if (i % 6 == 0){
          j += 1;
          this.images[j] = [];
          hold = [];
          hold.push('https://image.tmdb.org/t/p/w500'+post.poster_path)

          if (post.media_type == 'movie') {
            hold.push(post.title);
          } else {
            hold.push(post.name);
          }
          hold.push(post.id);
          hold.push(post.media_type)
          this.images[j].push(hold);
        } else {
          hold = [];
          if (post.poster_path){
            hold.push('https://image.tmdb.org/t/p/w500'+post.poster_path)
          }

          if (post.media_type == 'movie') {
            hold.push(post.title);
          } else {
            hold.push(post.name);
          }
          hold.push(post.id);
          hold.push(post.media_type)
          if (post.poster_path){
            this.images[j].push(hold);
          }
        }
      }
      for (let batch of this.images){
        for (let post of batch){

            this.posts.push(post)


        }
      }
    }
  }

  route(event:any, item:any, type:any){
    location.href = '/watch/' + type +'/'+ item;
  }
}
