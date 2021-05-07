const express = require('express');
const router = express.Router();
const axios = require('axios');
var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
var FileReader = require("filereader");
token = "token=uGdPGZ2KCknZO9-gm6jjzvxQhhIgKhaYXSI0DyQEIEs"
base_url = "https://trefle.io/api/v1/"


/*  Get the id of the plant
   i.e. https://trefle.io/api/v1/plants/search/?q=Cocos%20nucifera&token=uGdPGZ2KCknZO9-gm6jjzvxQhhIgKhaYXSI0DyQEIEs
   http://localhost:8080/apis/posts/get/Tulipa gesneriana Sorbus Aucuparia
   Tulipa gesneriana
 */
router.get('/get/:scientificName', function(req, res){
  console.log("HERE")
  let query = req.params.scientificName;
  //let query = "Sorbus%20aucuparia"
  let url = base_url + 'species/search/?q=' + query + '&' + token
  var obj = [];
  axios.get(url)
       .then((response) => {
         res.json(response.data["data"][0].id);
       })
});

/* Retrieve a plant by ID
   i.e. https://trefle.io/api/v1/species/183086?token=uGdPGZ2KCknZO9-gm6jjzvxQhhIgKhaYXSI0DyQEIEs
   http://localhost:8080/apis/posts/find/183086
*/
router.get('/find/:id', function(req, res){
  let query = req.params.id;
  let url = base_url + 'species/' + query + '?' + token
  var obj = {};
    axios.get(url)
         .then((response) => {
           //res.json(response.data["data"]["growth"].ph_maximum);
           x = response.data["data"]["growth"].ph_maximum;
           obj["ph_max"] = x;
           x = response.data["data"]["growth"].ph_minimum;
           obj["ph_min"] = x;
           x = response.data["data"]["growth"].light;
           obj["light"] = x;
           x = response.data["data"]["growth"].minimum_temperature.deg_f;
           obj["temp_min"] = x;
           x = response.data["data"]["growth"].maximum_temperature.deg_f;
           obj["temp_max"] = x;
           x = response.data["data"]["growth"].soil_texture;
           obj["soil_texture"] = x;
           x = response.data["data"].image_url;
           obj["image"] = x;
           x = response.data["data"].scientific_name;
           obj["name"] = x;
           res.json(obj)
           console.log("Retrieving: " + x)
         })
});

router.get('/dummyData', function(req, res){
  var obj = {};
  obj["ph"] = "6";
  obj["temperature"] = "0"
  obj["soil_moisture"] = "4";
  obj["light"] = "8";
  console.log("Retrieving (Fake) Sensor Data")
  res.json(obj);
})

function toDataURL(url, callback) {
  var xhr = new XMLHttpRequest();
  xhr.onload = function() {
    var reader = new FileReader();
    reader.onloadend = function() {
      callback(reader.result);
    }
    reader.readAsDataURL(xhr.response);
  };
  xhr.open('GET', url);
  xhr.responseType = 'blob';
  xhr.send();
}

router.get('/test', function(req,res){
  console.log("HERE")
  let url = "https://api.plant.id/v2/identify"
/*
toDataURL('https://www.gravatar.com/avatar/d50c83cc0c6523b4d3f6085295c953e0', function(dataUrl) {
  console.log('RESULT:', dataUrl)
})*/
})

module.exports = router;
