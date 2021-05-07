import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {PostsService} from '../../services/posts.service';

@Component({
  selector: 'app-watch',
  templateUrl: './watch.component.html',
  styleUrls: ['./watch.component.css']
})

export class WatchComponent implements OnInit {
  public video_id: string = "";
  public id: any;
  public type: any;
  public post: any;
  aBtnisVisible:Boolean = Boolean(true);
  rBtnisVisible:Boolean = Boolean(false);
  showSuccessAlert: Boolean = false;
  showRemoveAlert: Boolean = false;
  tweetContent: string = "";
  facebookContent: string = "";
  recommended: any;
  rTitle: string = "";
  similar: any;
  sTitle: string = "";
  fullCast: any = [];
  casts: any = [];
  reviews: any = [];
  singleCast: any = [];
  public mobile: any = false;
  handles: any = {};
  facebook:Boolean = Boolean(false);
  insta:Boolean = Boolean(false);
  imbd:Boolean = Boolean(false);
  twitter:Boolean = Boolean(false);
  site: Boolean = Boolean(false);
  rCount: any;

  constructor(private route: ActivatedRoute, private postsService: PostsService) { }

  ngOnInit(): void {
    if (window.screen.width < 500) {
      this.mobile = true;
    } else {
      this.mobile = false; 
    }
    this.id = this.route.snapshot.paramMap.get('id');
    this.type = this.route.snapshot.paramMap.get('type');
    this.fetchDetails();
    this.checkWatchList();
    this.getRec();
    this.getSimilar();
    this.cast();
    this.fetchReviews();

  }

  getVideoId(){
    this.postsService.getVideo(this.type, this.id).subscribe(res => {
      let p: any;
      p = res;
      p = p["results"]
      for (let res of p) {
        if (res.name.toLowerCase().includes("trailer")) {
          this.video_id = res.key;
          break;
        } else if (res.name == "Teaser"){
            this.video_id = res.key;
            break;
          }
        }
        if (this.video_id.length == 0){
          this.video_id = "tzkWB85ULJY";
        }
        this.formTweetContent();
        this.formFacebookContent();
    })

  }

  fetchDetails() {
    this.postsService.getDetails(this.type, this.id).subscribe(res => {
      let p: any;
      p = res;
      this.post = p;

      // add to local localStorage
      var posts: any = {};
      var current = localStorage.getItem("continueWatching");
      if (current){
        posts = JSON.parse(current);
      }
      posts[this.id]=this.post;
      var jsonPost = JSON.stringify(posts);
      localStorage.setItem("continueWatching", jsonPost)

      // update order of continueWatching to local storage
      var currentOrder = localStorage.getItem("cwOrder");
      var order:any = [];
      if (currentOrder) {
        order = JSON.parse(currentOrder)
      }
      const index = order.indexOf(this.id);
      if (index > -1) {
        order.splice(index, 1);
      }
      order.unshift(this.id);
      localStorage.setItem("cwOrder", JSON.stringify(order));

      //parse post
      if (this.type == "tv") {
        this.post.title = this.post.name;
        this.post.release_date = this.post.first_air_date;
        this.post.runtime = this.post.episode_run_time;
      }
      console.log(this.post)

      this.post.release_date = this.post.release_date.split('-')[0];

      let g:string = "";
      for (var genre in this.post.genres) {
        if (parseInt(genre) > 0) {
          g += ', '
        }
        g += this.post.genres[genre]['name']
      }
      this.post.genres = g;

      var time = parseInt(this.post.runtime);
      var hour = Math.floor(time/60);
      var minutes = time % 60;
      this.post.runtime = hour.toString() + 'hrs ' + minutes.toString() + 'mins'

      let l:string = "";
      for (var lang in this.post.spoken_languages) {
        if (parseInt(lang) > 0){
          l += ', ';
        }
        l += this.post.spoken_languages[lang]['english_name']
      }
      this.post.spoken_languages = l;

      this.getVideoId();

    })

  }

  checkWatchList(){
    var elements = localStorage.getItem("watchList");

    var found: Boolean = false;
    if (elements) {
      let e = JSON.parse(elements);

      for (const p in e){
        if (this.id == p) {
          found = true;
          break;
        }
      }
    }
    if (found == true){
      this.rBtnisVisible = true;
      this.aBtnisVisible = false;
    } else {
      this.rBtnisVisible = false;
      this.aBtnisVisible = true;
    }
  }

  addToWatchList(event:any, post:any){
    var elements = localStorage.getItem("watchList")
    var posts: any = {};
    if (elements){
      posts = JSON.parse(elements);
    }
    post.media_type = this.type;

    posts[this.id]=post;



    var jsonPost = JSON.stringify(posts);
    localStorage.setItem("watchList", jsonPost)

    var currentOrder = localStorage.getItem("listOrder");
    var order: any = [];
    if (currentOrder) {
      order = JSON.parse(currentOrder)
    }
    const index = order.indexOf(this.id);
    if (index > -1) {
      order.splice(index, 1);
    }
    order.unshift(this.id);
    localStorage.setItem("listOrder", JSON.stringify(order));


    this.rBtnisVisible = true;
    this.aBtnisVisible = false;

    this.showSuccessAlert = true;
    this.showRemoveAlert = false;

  }

  removeWatchList(event:any, post:any){
    var elements = localStorage.getItem("watchList");
    var posts: any = {};
    if (elements){
      posts = JSON.parse(elements);
    }
    delete posts[post.id]

    var p: any = [];
    var e = localStorage.getItem('listOrder');
    if (e) {
      p = JSON.parse(e);
    }
    var newX = [];
    for( var i = 0; i < p.length; i++){

      if ( p[i] === post.id.toString()) {

          console.log(post.name)
      } else {
        newX.push(p[i])
      }

    }
    var m = JSON.stringify(newX)
    localStorage.setItem("listOrder",m)
    console.log(p)


    var jsonPost = JSON.stringify(posts);
    localStorage.setItem("watchList", jsonPost)
    this.rBtnisVisible = false;
    this.aBtnisVisible = true;
    console.log("removed: ", localStorage.getItem("watchList"))
    this.showRemoveAlert = true;
    this.showSuccessAlert = false;
    //this.wait()
    //this.showRemoveAlert = false;
  }

  formTweetContent() {
    this.tweetContent = "https://twitter.com/intent/tweet?text=Watch%20"
    console.log(this.post)
    if (this.post.title){
      this.tweetContent += encodeURIComponent(this.post.title);
    } else {
      this.tweetContent += encodeURIComponent(this.post.name);
    }
    this.tweetContent += encodeURIComponent('\n') + 'https://youtube.com/watch?v=' + this.video_id
    this.tweetContent += encodeURIComponent('\n#') + 'USC%20' + encodeURIComponent('#') + 'CSCI571%20' + encodeURIComponent('#') + 'FightOn'

  }

  formFacebookContent(){
    this.facebookContent = "https://www.facebook.com/sharer/sharer.php?u="
    this.facebookContent += 'https://youtu.be/' + this.video_id
    this.facebookContent += '%2F&amp;src=sdkpreparse'
  }

  getRec(){
    this.postsService.getRecommended(this.type, this.id).subscribe(res => {
      let p: any;
      p = res;
      this.recommended = p["results"];
      if (this.type == 'movie') {
        this.rTitle = "Recommended Movies"
      } else {
        this.rTitle = "Recommended TV shows"
      }
    })
  }

  getSimilar(){
    this.postsService.getSimilar(this.type, this.id).subscribe(res => {
      let p: any;
      p = res;
      this.similar = p["results"];
      if (this.type == 'movie') {
        this.sTitle = "Similar Movies"
      } else {
        this.sTitle = "Similar TV shows"
      }
    })
  }

  cast(){
    this.postsService.getCast(this.type, this.id).subscribe(res => {
      let p: any;
      p = res;
      p = p["cast"];

      var i = -1;
      var j = -1;
      for (let ind of p){
        i+=1;
        if (i % 6 == 0){
          j += 1;
          this.fullCast[j] = [];
        }
        if (ind.profile_path){
          ind.profile_path = "https://image.tmdb.org/t/p/w500" + ind.profile_path;
          this.fullCast[j].push(ind)
          this.casts.push(ind);
        }

      }
    })
  }

  fetchReviews(){
    this.postsService.getReview(this.type, this.id).subscribe(res => {
      let p: any;
      p = res;
      p = p['results']
      for (let review of p){
        console.log(review.author_details)
        if (review.author_details.avatar_path && !review.author_details.avatar_path.includes('http')){
          review.author_details.avatar_path = "https://image.tmdb.org/t/p/original" +  review.author_details.avatar_path;
        } else {
          review.author_details.avatar_path = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRHnPmUvFLjjmoYWAbLTEmLLIRCPpV_OgxCVA&usqp=CAU";
        }
        if (!review.author_details.rating){
          review.author_details.rating = '0';
        }

        this.reviews.push(review)
      }
      let size = Math.min(10, p.length)
      this.rCount = size;

      this.reviews = this.reviews.slice(0, size);
    })
  }

  openModel(event: any, cast: any, img: any){
    this.postsService.getCastDetails(cast.id).subscribe(res => {
      let p: any;
      p = res;
      p.profile_path = img;
      if (p.gender == 1){
        p.gender = "Female"
      } else if (p.gender == 2){
        p.gender = "Male"
      } else {
        p.gender = "Undefined"
      }
      if (p.homepage){
        this.site = true;
      } else {
        this.site = false;
      }
      var a = ""
      for (var i = 0; i < p.also_known_as.length; i++) {
        if (i > 0){
          a += ', '
        }
        a += p.also_known_as[i]
      }
      p.also_known_as = a;
      this.singleCast = p;
    })
    this.postsService.getCastHandles(cast.id).subscribe(res =>{
      let p: any;
      p = res;
      console.log("Handles: ", p)
      if (p.facebook_id) {
        this.facebook = true;
        this.handles.facebook = "https://www.facebook.com/" + p.facebook_id;
      } else {
        this.facebook = false;
      }
      if (p.imdb_id) {
        this.imbd = true;
        this.handles.imbd = "https://www.imdb.com/name/" + p.imdb_id;
      } else {
        this.imbd = false;
      }
      if (p.instagram_id){
        this.insta = true;
        this.handles.instagram = "https://www.instagram.com/" + p.instagram_id;
      } else {
        this.insta = false
      }
      if (p.twitter_id){
        this.twitter = true;
        this.handles.twitter = "https://www.twitter.com/" + p.twitter_id;
      } else {
        this.twitter = false;
      }
    })
    console.log("HANDLES: ", this.handles)

  }

  getHandles(event:any, url: any){

      console.log(url)

  }



}
