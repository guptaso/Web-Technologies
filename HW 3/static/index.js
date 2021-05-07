var movie_placehold = 'imgs/movie-placeholder.jpg'
var person_placehold = 'imgs/person-placeholder.png'

function openTab(e, tabName) {
  var i;

  // close all the tabs first and remove color
  tabs = document.getElementsByClassName("tabPage");
  for (i = 0; i < tabs.length; i++){
    tabs[i].style.display = "none";
  }

  // make sure all the tabs are unactivated and remove color
  tabButtons = document.getElementsByClassName('tabButton');
  for (i = 0; i < tabButtons.length; i++){
    tabButtons[i].className = tabButtons[i].className.replace(" active", "");
    tabButtons[i].style.color = "white";
    tabButtons[i].style.borderBottom = "none";
  }

  // make only the selected tab active
  e.currentTarget.className += " active";


  // finally, show the selected tabs content and change color to red
  document.getElementById(tabName).style.display = "block";
  e.currentTarget.style.color = "rgb(158,34,25)";
  e.currentTarget.style.borderBottom = "1px solid white";
}

function searchInput(){
  // if either queries are empty, return an alert
  var selectedValue = document.getElementById("category");
  var keywordValue = document.getElementById("keyword");

  if (selectedValue.value == 0 || keywordValue.value.length == 0){
    alert("Please enter valid values.");
  }
  else {
    if (selectedValue.value == 1){
      getSearchMovie(keywordValue.value);
    }
    else if (selectedValue.value == 2) {
      getSearchTVShow(keyword.value);
    }
    else {
      getSearchBoth(keyword.value);
    }
  }
  //clearInput();
}

function clearInput(){
  document.getElementById("keyword").value = "";
  document.getElementById("category").value=0;
}

function slideshow(){
  var i;
  var trendingSlides = document.getElementsByClassName("trendingSlides");
  var airingSlides = document.getElementsByClassName("airingSlides");
  var trendingSlidesCaption = document.getElementsByClassName("trendingSlidesCaption");
  var airingSlidesCaption = document.getElementsByClassName("airingSlidesCaption");

  // hide the current slide
  for (i = 0; i < airingSlides.length; i++) {
    airingSlides[i].style.display = "none";
    trendingSlides[i].style.display = "none";
    trendingSlidesCaption[i].style.display = "none";
    airingSlidesCaption[i].style.display = "none";
  }

  // display the next slide
  index += 1;
  if (index >= airingSlides.length){
    index = 0;
  }
  airingSlides[index].style.display = "block "
  trendingSlides[index].style.display = "block"
  trendingSlidesCaption[index].style.display = "block"
  airingSlidesCaption[index].style.display = "block"
  // repeat
  setTimeout(slideshow, 2000)
}

function removeAll(){
    var m = document.getElementsByClassName("results");
    while(m.length > 0){
      m[0].parentNode.removeChild(m[0]);
    }
    if (document.getElementById("showingRes") == null){
      var main = document.getElementById("resultsContainer");
      var x = document.createElement("div");
      x.innerHTML = "Showing results..."
      x.id = "showingRes"
      main.appendChild(x);
    }else {
      document.getElementById("showingRes").innerHTML = "Showing results..."
    }
}

function display(id, title, overview, poster_path, release_date, vote_avg, vote_count, genre_ids,type){

  // main container
  var main = document.getElementById("resultsContainer")
  var container = document.createElement("div");
  container.id="results";
  container.className="results"

  // create vertical line
  var vl = document.createElement("div");
  vl.className = "verticalLine2";
  container.appendChild(vl);

  // add poster
  basePosterPath = 'https://image.tmdb.org/t/p/original';
  var myImage = document.createElement("img");
  var iUrl = "";
  if (poster_path == "N/A") {
    iUrl = movie_placehold;
  } else {
    iUrl = basePosterPath + poster_path;
  }
  myImage.src = iUrl;
  myImage.id = "poster";
  container.appendChild(myImage);

  //add content
  var contentDiv = document.createElement("div");
  contentDiv.id = "content";

  // add title
  var titleDiv = document.createElement("div");
  titleDiv.innerHTML = title;
  titleDiv.id = "title";
  contentDiv.appendChild(titleDiv);

  // add about
  var aboutContainer = document.createElement("div");
  aboutContainer.id = "about";
  var date = document.createElement("div");
  date.id = "date"
  genre_string = ""
  if (genre_ids != "N/A") {
    var i;
    for (i = 0; i < genre_ids.length; i++){
      gid = parseInt(genre_ids[i])
      if (type == 'movie'){
        genre_string += movie_genres[gid]
      }
      else {
        genre_string += tv_genres[gid]
      }
      if (i + 1 < genre_ids.length){
        genre_string += ', '
      }
    }
  }
  else {
    genre_string = "N/A"
  }
  date.innerHTML = release_date.split('-')[0] + ' | ' + genre_string
  aboutContainer.appendChild(date)
  contentDiv.appendChild(aboutContainer);

  // add voting stuff
  var votingContainer = document.createElement("div")

  votingContainer.id="voting";
  var star = document.createElement("div")
  star.id = "star"
  star.innerHTML = "&#9733; "
  votingContainer.appendChild(star)
  var votingAvg = document.createElement("div")
  votingAvg.id = "voteAvg";
  var newAvg;
  if (vote_avg != "N/A") {
    newAvg = (parseFloat(vote_avg) / 2).toFixed(2).replace(/0+$/, "");
    if (newAvg.split('.')[1].length == 0){
      newAvg = newAvg.split('.')[0] + '.0'
    }
    newAvg += '/5 '
  } else {
    newAvg = "0.0"
  }

  votingAvg.innerHTML = newAvg ;
  votingContainer.appendChild(votingAvg);
  var vCount = document.createElement("div");
  vCount.id = "voteCount";
  if (vote_count == "N/A") {
    vote_count = '0'; 
  }
  vCount.innerHTML = vote_count + ' votes';
  votingContainer.appendChild(vCount);
  contentDiv.appendChild(votingContainer);

  //add the overview
  var o = document.createElement("div");
  o.id = "overview";
  o.innerHTML = overview;
  contentDiv.appendChild(o);

  //add show more button
  var b = document.createElement("button");
  b.className="showMore";

  b.id=id+'_'+type;
  b.innerHTML = "Show more"
  b.onclick = function() {openPopup(this.id)};
  contentDiv.appendChild(b);
  container.appendChild(contentDiv);

  main.appendChild(container);
}

function openPopup(id) {
  type = id.split('_')[1]
  id = id.split('_')[0]

  getPopupContentMovie(id, type);
  //USE THIS: document.getElementById("popup").style.display = "block";
  document.querySelector('.full-screen').classList.remove('hidden');


  //document.getElementsByClassName("html").style.overflow = "hidden";
  //document.getElementById("body").style.position = "fixed";
  //overflow-y: hidden;

  //document.getElementById("popup").style.overflow = "auto";
}

function closePopup(){
  //document.getElementById("popup").style.display = "none";
  document.querySelector('.full-screen').classList.add('hidden');
  //document.getElementsByClassName("html").style.overflow = "auto";
  //document.getElementById("popup").style.overflow = "hidden";
}


function removeCast(){
  var m = document.getElementsByClassName("cast");
  while(m.length > 0){
    m[0].parentNode.removeChild(m[0]);
  }
}

function removeReviews(){
  var m = document.getElementsByClassName("reviews");
  while(m.length > 0){
    m[0].parentNode.removeChild(m[0]);
  }
}

index = 0;
document.getElementById("homeTabButton").click();
//re-look at this, there seems to be a pause for slideshow to start
setTimeout(slideshow, 2000)
getGenresId("movie")
getGenresId("tv")
var movie_genres = {}
var tv_genres = {}
