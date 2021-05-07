var xhr = new XMLHttpRequest();
//var homeLink = "http://127.0.0.1:5000/";
var homeLink = "https://myyyapp.azurewebsites.net/"
async function getHomeTrendingMovies(){
  var link = homeLink + "home_trendingMovies";
  const response = await fetch(link, {
    method: 'GET',
    mode: 'cors',
  });
  let d = await response.text();

  var backdrop_URL = "https://image.tmdb.org/t/p/w500"
  var res = JSON.parse(d);
  var i;
  var div = document.getElementById("trendingSlides");
  var captionDiv = document.getElementById("trendingSlidesCaptionContainer");
  for (i = 0; i < 5; i++){
    var myImage = document.createElement("img");
    var imgUrl = res["results"][i]["backdrop_path"] || "N/A"
    if (imgUrl == "N/A"){
      imgUrl = movie_placehold;
    } else {
      imgUrl = backdrop_URL + imgUrl;
    }
    myImage.src = imgUrl;
    myImage.className="trendingSlides";

    var h2 = document.createElement("h2");
    var span = document.createElement("span");

    var title = res["results"][i]["title"] || "N/A"
    var rd = res["results"][i]["release_date"] || "N/A"
    if (rd.includes('-')) {
      rd = rd.split('-')[0]
    }
    span.innerHTML = title + ' (' + rd + ')';
    h2.className="trendingSlidesCaption";
    h2.appendChild(span);
    div.appendChild(myImage);
    captionDiv.appendChild(h2);
  }
}

async function getHomeAiringMovies(){
  var link = homeLink + "home_airingMovies";

  const response = await fetch(link, {
    method: 'GET',
    mode: 'cors',
  });
  let d = await response.text();

  var backdrop_URL = "https://image.tmdb.org/t/p/w500"
  var res = JSON.parse(d);
  var i;
  var div = document.getElementById("airingSlides");
  var captionDiv = document.getElementById("airingSlidesCaptionContainer");
  for (i = 0; i < 5; i++){
    var myImage = document.createElement("img");
    var imgUrl = res["results"][i]["backdrop_path"] || "N/A";
    if (imgUrl == "N/A") {
      imgUrl = movie_placehold
    } else {
      imgUrl = backdrop_URL + imgUrl;
    }
    myImage.src = imgUrl;
    myImage.className="airingSlides";
    div.appendChild(myImage);
    var h2 = document.createElement("h2");
    var span = document.createElement("span");
    var name = res["results"][i]["name"] || "N/A"
    var f = res["results"][i]["first_air_date"] || "N/A"
    if (f.includes('-')){
      f = f.split('-')[0]
    }
    span.innerHTML = name + ' (' + f + ')';
    h2.className="airingSlidesCaption";
    h2.appendChild(span);
    div.appendChild(myImage);
    captionDiv.appendChild(h2);
  }
}

async function getSearchMovie(keyword) {
  var link = homeLink + '/search_movie?key=' + keyword;

  const response = await fetch(link, {
    method: 'GET',
    mode: 'cors',
  });
  let d = await response.text();
  var res = JSON.parse(d);

  var x = Math.min(res["results"].length, 10);
  var i;
  removeAll();
  if (x == 0) {
    if (document.getElementById("showingRes") == null){
      var main = document.getElementById("resultsContainer");
      var x = document.createElement("div");
      x.innerHTML = "No results found."
      x.id = "showingRes"
      x.style.textAlign = "center"
      main.appendChild(x);
    } else {
      document.getElementById("showingRes").innerHTML = "No results found."
      document.getElementById("showingRes").style.textAlign = "center"
    }
  } else {
    for (i = 0; i < x; i++){
      id = res["results"][i]["id"] || "N/A";
      title = res["results"][i]["title" || "N/A"];
      overview = res["results"][i]["overview"] || "N/A";
      poster_path = res["results"][i]["poster_path"] || "N/A";
      release_date = res["results"][i]["release_date"] || "N/A";
      vote_avg = res["results"][i]["vote_average"] || "N/A";
      vote_count = res["results"][i]["vote_count"] || "N/A";
      genre_ids = res["results"][i]["genre_ids"] || "N/A";
      display(id, title, overview, poster_path, release_date, vote_avg, vote_count, genre_ids,'movie')
    }
  }
}

async function getSearchTVShow(keyword) {
  var link = homeLink + '/search_tvShow?key=' + keyword;
  const response = await fetch(link, {
    method: 'GET',
    mode: 'cors',
  });
  let d = await response.text();
  var res = JSON.parse(d);

  var x = Math.min(res["results"].length, 10);
  var i;
  removeAll();
  if (x == 0) {
    if (document.getElementById("showingRes") == null){
      var main = document.getElementById("resultsContainer");
      var x = document.createElement("div");
      x.innerHTML = "No results found."
      x.style.textAlign = "center"
      x.id = "showingRes"
      main.appendChild(x);
    } else {
      document.getElementById("showingRes").innerHTML = "No results found."
      document.getElementById("showingRes").style.textAlign = "center"
    }
  } else {
    for (i = 0; i < x; i++){
      id = res["results"][i]["id"] || "N/A";
      title = res["results"][i]["name"] || "N/A";
      overview = res["results"][i]["overview"] || "N/A"
      poster_path = res["results"][i]["poster_path"] || "N/A";
      release_date = res["results"][i]["first_air_date"] || "N/A"
      vote_avg = res["results"][i]["vote_average"] || "N/A"
      vote_count = res["results"][i]["vote_count"] || "N/A"
      genre_ids = res["results"][i]["genre_ids"] || "N/A"
      display(id, title, overview, poster_path, release_date, vote_avg, vote_count, genre_ids,'tv')
    }
  }
}

async function getSearchBoth(keyword) {
  var link = homeLink + '/search_both?key=' + keyword;
  const response = await fetch(link, {
    method: 'GET',
    mode: 'cors',
  });
  let d = await response.text();
  var res = JSON.parse(d);
  var x = Math.min(res["results"].length, 10);
  removeAll();
  var i;
  if (x == 0) {

    if (document.getElementById("showingRes") == null){
      var main = document.getElementById("resultsContainer");
      var x = document.createElement("div");
      x.innerHTML = "No results found."
      x.style.textAlign = "center"
      x.id = "showingRes"
      main.appendChild(x);
    } else {

      document.getElementById("showingRes").innerHTML = "No results found."
      document.getElementById("showingRes").style.textAlign = "center"
    }
  } else {
    for (i = 0; i < x; i++){
      if (res["results"][i]["media_type"] == "movie") {
        id = res["results"][i]["id"] || "N/A";
        title = res["results"][i]["title"] || "N/A";
        overview = res["results"][i]["overview"] || "N/A"
        poster_path = res["results"][i]["poster_path"] || "N/A";
        release_date = res["results"][i]["release_date"] || "N/A"
        vote_avg = res["results"][i]["vote_average"] || "N/A"
        vote_count = res["results"][i]["vote_count"] || "N/A"
        genre_ids = res["results"][i]["genre_ids"] || "N/A"
        display(id, title, overview, poster_path, release_date, vote_avg, vote_count, genre_ids,'movie')
      }
      else {
        id = res["results"][i]["id"] || "N/A";
        title = res["results"][i]["name"] || "N/A";
        overview = res["results"][i]["overview"] || "N/A"
        poster_path = res["results"][i]["poster_path"] || "N/A";
        release_date = res["results"][i]["first_air_date"] || "N/A"
        vote_avg = res["results"][i]["vote_average"] || "N/A"
        vote_count = res["results"][i]["vote_count"] || "N/A"
        genre_ids = res["results"][i]["genre_ids"] || "N/A"
        display(id, title, overview, poster_path, release_date, vote_avg, vote_count, genre_ids,'tv')
      }
    }
  }
}

async function getPopupContentMovie(id, type) {
  var link = homeLink + '/popup_movie?key=' + id + "&type=" + type;

  const response = await fetch(link, {
    method: 'GET',
    mode: 'cors',
  });
  let d = await response.text();
  var res = JSON.parse(d);
  var p = res["backdrop_path"] || "N/A"
  if (p == "N/A") {
    p = movie_placehold
  } else {
    p = "https://image.tmdb.org/t/p/w500" + p;
  }
  document.getElementById("popupPoster").src = p
  var va = res["vote_average"] || "N/A"
  var new_va;
  if (va != "N/A"){
    new_va = (parseFloat(vote_avg) / 2).toFixed(2).replace(/0+$/, "");
    new_va += '/5'
  } else {
    new_va = "N/A"
  }
  var vc = res["vote_count"] || "N/A"
  document.getElementById("popupVoting").innerHTML = new_va
  document.getElementById("popupVotingCount").innerHTML = vc + ' votes'
  document.getElementById("popupOverview").innerHTML = res["overview"] || "N/A"
  var spoken = "Spoken languages: "
  var j;
  var language = "";
  if (res["spoken_languages"].length > 0 ){
    for (j = 0; j < res["spoken_languages"].length; j++){
      language += res["spoken_languages"][j]["english_name"]
      if (j+1 < res["spoken_languages"].length) {
        language += ', '
      }
    }
  } else {
    language += "N/A"
  }


  document.getElementById("popupSpokenL").innerHTML = spoken + language;

  if (type == "movie") {
    document.getElementById("popupTitle").innerHTML = res["title"] || "N/A";
    var rd = res["release_date"] || "N/A"
    if (rd.includes('-')) {
      rd = rd.split('-')[0]
    }
    var genre_ids = res["genres"] || "N/A"
    var gid;
    genre_string = ""
    if (genre_ids != "N/A") {
      var i;
      for (i = 0; i < genre_ids.length; i++){
        gid = parseInt(genre_ids[i]["id"])
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
    document.getElementById("popupAbout").innerHTML = rd + ' | ' + genre_string
    url = "https://www.themoviedb.org/movie/" + id;
    document.getElementById("infoBtn").onclick = function() {window.open(url)};
  }
  else {
    document.getElementById("popupTitle").innerHTML = res["name"]  || "N/A";
    var rd = res["first_air_date"] || "N/A"
    if (rd.includes('-')) {
      rd = rd.split('-')[0]
    }
    var g= res["genres"] || "N/A"
    document.getElementById("popupAbout").innerHTML = rd + ' | ' + g
    url = "https://www.themoviedb.org/tv/" + id;
    document.getElementById("infoBtn").onclick = function() {window.open(url)};
  }


  // get movie credits
  removeCast();
  link = homeLink + '/getCredits?key=' + id + "&type=" + type;
  const response2 = await fetch(link, {
    method: 'GET',
    mode: 'cors',
  });
  let e = await response2.text();
  var resCredits = JSON.parse(e);
  var len = Math.min(resCredits["cast"].length, 8)
  var i;

  for (i = 0; i < len; i++){

    var main = document.getElementById("popupCast")
    var container = document.createElement("cast");
    container.id = "cast";
    container.className = "cast";
    var image = document.createElement("img")
    var iUrl = resCredits["cast"][i]["profile_path"] || "N/A"
    if (iUrl == "N/A") {
      iUrl = person_placehold;
    } else {
      iUrl = "https://image.tmdb.org/t/p/original" +  iUrl;
    }
    image.src = iUrl;
    image.id = "castImage";
    container.appendChild(image)

    var castName = document.createElement("div")
    castName.innerHTML = resCredits["cast"][i]["name"];
    castName.id = "castName";
    container.appendChild(castName);

    var x = document.createElement("div")
    x.innerHTML = "AS"
    x.id = "AS"
    container.appendChild(x)

    var credit = document.createElement("div")
    credit.innerHTML = resCredits["cast"][i]["character"];
    credit.id = "castChName"
    container.appendChild(credit)
    main.appendChild(container);
  }

  // get movie reviews
  removeReviews()
  link = homeLink + '/getReviews?key=' + id + "&type=" + type;;
  const response3 = await fetch(link, {
    method: 'GET',
    mode: 'cors',
  });
  let f = await response3.text();
  var resReviews = JSON.parse(f);
  len = Math.min(resReviews["results"].length, 5)
  for (i = 0; i < len; i++){
    main = document.getElementById("popupReviews");

    container = document.createElement("div")
    container.className = "reviews"

    var userBlock = document.createElement("div");
    userBlock.id = "userBlock";

    var username = document.createElement("div");
    username.innerHTML =  resReviews["results"][i]["author_details"]["username"] || "N/A";

    username.id = "username"
    userBlock.appendChild(username)

    var userCreation = document.createElement("div");
    userCreation.innerHTML = "  on "
    var c = resReviews["results"][i]["created_at"] || "N/A"
    if (c != "N/A") {
      userCreation.innerHTML += c.split('-')[1] + '/'
      userCreation.innerHTML += c.split('-')[2].split('T')[0] + '/'
      userCreation.innerHTML += c.split('-')[0]
    }else {
      userCreation.innerHTML += "N/A"
    }


    userCreation.id = "created"
    userBlock.appendChild(userCreation)
    container.appendChild(userBlock)

    var userRating = document.createElement("div")

    userRating.innerHTML = resReviews["results"][i]["author_details"]["rating"] || "N/A"
    userRating.id = "rating"
    //userBlock.appendChild(user)
    if (userRating.innerHTML != "N/A") {
      var ratingContainer = document.createElement("div");
      ratingContainer.id = "ratingContainer";
      var star = document.createElement("div");
      star.id = "star"
      star.innerHTML = "&#9733; "
      ratingContainer.appendChild(star)

      userRating.innerHTML = (parseFloat(userRating.innerHTML) / 2).toFixed(2).replace(/0+$/, "");
      if (userRating.innerHTML.split('.')[1].length == 0) {
        userRating.innerHTML = userRating.innerHTML.split('.')[0] + '.0'
      }

      userRating.innerHTML += '/5'
      ratingContainer.appendChild(userRating)
      container.appendChild(ratingContainer);
    }

    //container.appendChild(userBlock)
    var userReview = document.createElement("div")
    userReview.innerHTML = resReviews["results"][i]["content"]
    userReview.id = "ucontent"
    userReview.className = "ratingContainer clipped"
    container.appendChild(userReview)

    var line = document.createElement("HR");
    line.id = "ratingLine"
    container.appendChild(line)

    main.appendChild(container)
  }
}

async function getGenresId(type) {
  var link = homeLink + '/getGenres?type=' + type;

  const response = await fetch(link, {
    method: 'GET',
    mode: 'cors',
  });
  let d = await response.text();
  var res = JSON.parse(d);
  var i;
  if (type == "movie"){
    for (i = 0; i < res["genres"].length; i++){
      movie_genres[res["genres"][i]["id"]] = res["genres"][i]["name"]
    }
  }
  else {
    for (i = 0; i < res["genres"].length; i++){
      tv_genres[res["genres"][i]["id"]] = res["genres"][i]["name"]
    }
  }
}
