from flask import Flask
from flask_cors import CORS
app = Flask(__name__,static_url_path='', static_folder="static",template_folder='templ')
CORS(app)
import requests
from flask import Flask, request
import json

# used for URL's
api_key = "?api_key=eb04bea4519a4b07b0788e5cbcfa3e41"
base_url = "https://api.themoviedb.org/3/"
backdrop_URL = "https://image.tmdb.org/t/p/w500"


@app.route('/', methods=['GET'])
def home():
    return app.send_static_file("HW4_home.html")

@app.route('/home_trendingMovies', methods=['GET'])
def home_trending():
    trending_url = base_url + 'trending/movie/week' + api_key
    r = requests.get(trending_url)

    response = app.response_class(
        response = r,
        status = 200,
        mimetype = 'application/json'
    )
    return response

@app.route('/home_airingMovies', methods=['GET'])
def home_airing():
    today_url = base_url + 'tv/airing_today' + api_key
    r = requests.get(today_url)
    response = app.response_class(
        response = r,
        status = 200,
        mimetype = 'application/json'
    )
    return response

@app.route('/search_movie', methods=['GET'])
def search_movies():
    keyword = request.args.get('key');
    keyword = '%20'.join(keyword.split(' '))
    keywordUrl = '&query=' + keyword + '&language=en-US&page=1&include_adult=false'

    searchUrl = base_url + 'search/' + 'movie' + api_key + keywordUrl
    r = requests.get(searchUrl)
    response = app.response_class(
        response = r,
        status = 200,
        mimetype = 'application/json'
    )
    return response

@app.route('/search_tvShow', methods=['GET'])
def search_tv():
    keyword = request.args.get('key');
    keyword = '%20'.join(keyword.split(' '))
    keywordUrl = '&query=' + keyword + '&language=en-US&page=1&include_adult=false'

    searchUrl = base_url + 'search/' + 'tv' + api_key + keywordUrl
    r = requests.get(searchUrl)
    response = app.response_class(
        response = r,
        status = 200,
        mimetype = 'application/json'
    )
    return response

@app.route('/search_both', methods=['GET'])
def search_both():
    keyword = request.args.get('key');
    keyword = '%20'.join(keyword.split(' '))
    keywordUrl = '&query=' + keyword + '&language=en-US&page=1&include_adult=false'

    searchUrl = base_url + 'search/' + 'multi' + api_key + keywordUrl
    r = requests.get(searchUrl)
    j = json.loads(r.text);

    l = min(len(j["results"]), 10)
    toRet = {}
    toRet["results"] = []

    for i in range(0, l):
        if j["results"][i]["media_type"] == "movie" or j["results"][i]["media_type"] == "tv":
            toRet["results"].append(j["results"][i])


    r = json.dumps(toRet)
    response = app.response_class(
        response = r,
        status = 200,
        mimetype = 'application/text'
    )
    return response

@app.route('/popup_movie', methods=['GET'])
def search_popup():
    keyword = request.args.get('key')
    type = request.args.get('type')
    url = base_url + type + '/' + keyword + api_key + '&language=en-US'

    r = requests.get(url)

    response = app.response_class(
        response = r,
        status = 200,
        mimetype = 'application/json'
    )
    return response

@app.route('/getCredits', methods=["GET"])
def get_credits():
    keyword = request.args.get('key')
    type = request.args.get('type')
    url = base_url + type + '/' + keyword +'/credits' + api_key + '&language=en-US'
    r = requests.get(url)

    response = app.response_class(
        response = r,
        status = 200,
        mimetype = 'application/json'
    )
    return response

@app.route('/getReviews', methods=["GET"])
def get_review():
    keyword = request.args.get('key')
    type = request.args.get('type')
    url = base_url + type + '/' + keyword +'/reviews' + api_key + '&language=en-US&page=1'
    r = requests.get(url)

    response = app.response_class(
        response = r,
        status = 200,
        mimetype = 'application/json'
    )
    return response

@app.route('/getGenres', methods=["GET"])
def get_genres():
    type = request.args.get('type')
    url = base_url + 'genre/' + type + '/list' + api_key
    r = requests.get(url)


    response = app.response_class(
        response = r,
        status = 200,
        mimetype = 'application/json'
    )

    return response
