import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-mylist',
  templateUrl: './mylist.component.html',
  styleUrls: ['./mylist.component.css']
})
export class MylistComponent implements OnInit {
  noItems:Boolean = Boolean(false);
  watchItems: Boolean = Boolean(false);
  public posts: any = [];
  mobile: any;

  constructor() { }

  ngOnInit(): void {
    this.checkIfNoItems();
    if (this.watchItems) {
      this.fetchPosts();
      console.log(this.posts)
    }
    if (window.screen.width < 500) {
      this.mobile = true;
    } else {
      this.mobile = false;
    }

  }

  checkIfNoItems(){
    var elements = localStorage.getItem("watchList");
    if (elements){
      console.log("E:",elements.length)

      if (elements.length > 2){
        this.noItems = false;
        this.watchItems = true;
      }else {
        this.noItems = true;
        this.watchItems = false;
      }

    } else {
      this.noItems = true;
      this.watchItems = false;
    }
  }

  fetchPosts(){
    var order = localStorage.getItem("listOrder");
    var v: any;
    if (order) {
      var v = JSON.parse(order);

      var elements = localStorage.getItem("watchList");
      if (elements){
        var jsonPosts = JSON.parse(elements);
        console.log(jsonPosts)
        var posts:any = []
        for (let id of v){


              posts.push(jsonPosts[id])

        }

        console.log(jsonPosts)
        jsonPosts = posts;
        var i = -1;
        var j = -1;
        for (const post in jsonPosts){
          jsonPosts[post].poster_path = "https://image.tmdb.org/t/p/w500" + jsonPosts[post].poster_path
          if (jsonPosts[post].title) {
            jsonPosts[post].caption = jsonPosts[post].title;
          } else {
            jsonPosts[post].caption = jsonPosts[post].name;
          }
          i += 1;
          if (i % 6 == 0){
            j += 1
            this.posts[j] = [];
          }
          this.posts[j].push(jsonPosts[post]);

        }
      }
      console.log(this.posts)
    }
    /*
    var elements = localStorage.getItem("watchList");
    if (elements){
      var jsonPosts = JSON.parse(elements);

      var i = -1;
      var j = -1;


      for (const post in jsonPosts){
        jsonPosts[post].poster_path = "https://image.tmdb.org/t/p/w500" + jsonPosts[post].poster_path
        if (jsonPosts[post].title) {
          jsonPosts[post].caption = jsonPosts[post].title;
        } else {
          jsonPosts[post].caption = jsonPosts[post].name;
        }
        i += 1;
        if (i % 6 == 0){
          j += 1
          this.posts[j] = [];
        }
        this.posts[j].push(jsonPosts[post]);

      }
      console.log(this.posts)
    }*/
  }
  route(event:any, item:any, type:any){
    location.href = '/watch/' + type +'/'+ item;
  }

}
