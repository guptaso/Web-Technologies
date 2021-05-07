const express = require('express');
const router = express.Router();
const axios = require('axios');

api_key = "?api_key=eb04bea4519a4b07b0788e5cbcfa3e41"
base_url = "https://api.themoviedb.org/3/"
end_url = "&language=en-US"

/* 4.1.1: Multi-Search Endpoint to search for Movies and TV Shows
   i.e.: http://localhost:8080/posts/multisearch/<query>
 */
router.get('/multisearch/:query', function(req, res) {

  let query = req.params.query;
  let url = base_url + 'search/multi' + api_key + end_url + '&query=' + query
  var obj = [];
  var index = 0;
    axios.get(url)
    .then((response) => {
      var maxCount = 7;
      for (r in response.data["results"]){
        maxCount -= 1;
        if (maxCount == -1) {
          break;
        }

        if (response.data["results"][r].media_type=='movie' || response.data["results"][r].media_type == 'tv'){
          console.log(response.data["results"][r].backdrop_path, r)
          if (response.data["results"][r].backdrop_path){
              obj[index] = {}
              console.log(response.data["results"][r].backdrop_path)
            if (response.data["results"][r].name){
              obj[index].name = response.data["results"][r].name;

            } else {
              obj[index].name = response.data["results"][r].title
            }
            obj[index].backdrop_path = response.data["results"][r].backdrop_path;
            obj[index].id = response.data["results"][r].id;
            obj[index].media_type = response.data["results"][r].media_type;
            index += 1;
          }
        }
      }

      console.log("o: ", obj)
      res.json(obj)
    })

});

/* 4.1.2: Trending Movies Endpoint
    i.e.: http://localhost:8080/posts/trending/movie
   4.1.12: Trending TV Shows Endpoint
    i.e.: http://localhost:8080/posts/trending/tv
 */
router.get('/trending/:type', function(req, res) {
  let type = req.params.type;
  let url = base_url + 'trending/' + type + '/day' + api_key;
  axios.get(url).then(posts => {
    res.json(posts.data);
  });
});

/* 4.1.3: Top-Rated Movies Endpoint
    i.e.: http://localhost:8080/posts/topRated/movie
   4.1.13: Top-Rated TV shows Endpoint
    i.e.: http://localhost:8080/posts/topRated/tv
 */
router.get('/topRated/:type', function(req, res) {
  let type = req.params.type;
  let url = base_url + type + '/top_rated' + api_key + end_url + '&page=1'
  axios.get(url).then(posts => {
    res.json(posts.data);
  });
})

/* 4.1.4: Currently Playing Movies Endpoint
    i.e. http://localhost:8080/posts/current
 */
router.get('/current', function(req, res) {
   let url = base_url + 'movie/now_playing' + api_key + end_url + '&page=1'
   axios.get(url).then(posts => {
     res.json(posts.data);
   });
 })

/* 4.1.5: Popular Movies Endpoint
    i.e.: http://localhost:8080/posts/popular/movie
   4.1.14: Popular TV shows Endpoint
    i.e.: http://localhost:8080/posts/popular/tv
 */
router.get('/popular/:type', function(req, res) {
  let type = req.params.type;
  let url = base_url + type + '/popular' + api_key + end_url + '&page=1';
  axios.get(url).then(posts => {
    res.json(posts.data);
  });
});

/* 4.1.6: Recommended Movies Endpoint
    i.e.: http://localhost:8080/posts/recommended/movie/464052
   4.1.15: Recommended TV shows Endpoint
    i.e.: http://localhost:8080/posts/recommended/tv/85271
 */
router.get('/recommended/:type/:id', function(req, res){
  let type = req.params.type;
  let id = req.params.id;
  let url = base_url + type + '/' + id + '/recommendations' + api_key + end_url + '&page=1'
  axios.get(url).then(posts => {
    res.json(posts.data);
  });
});

/* 4.1.7: Similar Movies Endpoint
    i.e. http://localhost:8080/posts/similar/movie/464052
   4.1.16: Similar TV shows Endpoint
    i.e. http://localhost:8080/posts/similar/tv/85271
 */
 router.get('/similar/:type/:id', function(req, res){
   let type = req.params.type;
   let id = req.params.id;
   let url = base_url + type + '/' + id + '/similar' + api_key + end_url + '&page=1'
   axios.get(url).then(posts => {
     res.json(posts.data);
   });
 });

/* 4.1.8: Movies Video Endpoint
    i.e.: http://localhost:8080/posts/video/movie/464052
   4.1.17: TV Show Video Endpoint
    i.e. http://localhost:8080/posts/video/tv/85271
 */
router.get('/video/:type/:id', function(req, res){
   let type = req.params.type;
   let id = req.params.id;
   let url = base_url + type + '/' + id + '/videos' + api_key + end_url + '&page=1'
   axios.get(url).then(posts => {
     res.json(posts.data);
   });
 });

/* 4.1.9 Movie Details Endpoint
    i.e.: http://localhost:8080/posts/detail/movie/464052
   4.1.18 TV Show Details Endpoint
    i.e. http://localhost:8080/posts/detail/tv/85271
 */
router.get('/detail/:type/:id', function(req, res){
    let type = req.params.type;
    let id = req.params.id;
    let url = base_url + type + '/' + id + api_key + end_url + '&page=1'
    axios.get(url).then(posts => {
      res.json(posts.data);
    });
  });

/* 4.1.10 Movie Reviews Endpoint
    i.e.: http://localhost:8080/posts/review/movie/464052
   4.1.19 TV Show Reviews Endpoint
    i.e. http://localhost:8080/posts/review/tv/85271
 */
router.get('/review/:type/:id', function(req, res){
     let type = req.params.type;
     let id = req.params.id;
     let url = base_url + type + '/' + id + '/reviews' + api_key + end_url + '&page=1'
     axios.get(url).then(posts => {
       res.json(posts.data);
     });
   });

/* 4.1.11 Movie Cast Endpoint
    i.e.: http://localhost:8080/posts/cast/movie/464052
   4.1.20 TV Show Cast Endpoing
    i.e. http://localhost:8080/posts/cast/tv/85271
 */
router.get('/cast/:type/:id', function(req, res){
      let type = req.params.type;
      let id = req.params.id;
      let url = base_url + type + '/' + id + '/credits' + api_key + end_url + '&page=1'
      axios.get(url).then(posts => {
        res.json(posts.data);
      });
    });

/* 4.1.21 Cast Details Endpoint
    i.e. http://localhost:8080/posts/castDetails/550843
 */
router.get('/castDetails/:id', function(req, res) {
  let id = req.params.id;
  let url = base_url + 'person/' + id + api_key + end_url + '&page=1'
  axios.get(url).then(posts => {
    res.json(posts.data);
  });
})

/* 4.1.22 Cast external ids Endpoint
    i.e. http://localhost:8080/posts/castExternalId/550843
 */
router.get('/castExternalId/:id', function(req, res) {
  let id = req.params.id;
  let url = base_url + 'person/' + id + '/external_ids' + api_key + end_url + '&page=1'
  axios.get(url).then(posts => {
    res.json(posts.data);
  });
})

module.exports = router;
