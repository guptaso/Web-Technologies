var express = require('express');
const path = require('path');
var app = express();
var posts = require('./routes/posts');
const cors = require('cors');

app.use(cors());

app.use('/apis/posts', posts);


var server = app.listen(8080, function() {
    console.log("Backend Application listening at http://localhost:8080")
})

module.exports = app;
