package com.example.movies.util

class Constants {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val PARAM_API_KEY = "api_key"
        const val PARAM_LANGUAGE = "language"
        const val API_KEY = "6675b80b92722b89ee281264add1a4e6"
        const val LANGUAGE  = "en-US"
        const val PAGE = 1
        /*CodeReview images dimensions are too big to be used which will take a lot of time and network data to be loaded
            check this link to see how to get a smaller size https://developers.themoviedb.org/3/getting-started/images
            And you can use a larger size for tablets
         */

        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original/"
        const val YOUTUBE_IMAGE_BASE_URL = "https://img.youtube.com/vi/"
        const val YOUTUBE_IMAGE_HIGH_QUALITY = "hqdefault.jpg"
        const val TAG_OK_HTTP = "OkHttp"
        const val REQUEST_SHOW_FAVORITES = "com.example.movieapp.ui.details.REQUEST_SHOW_FAVORITES"
        const val RESULT_SHOW_FAVORITES = "com.example.movieapp.ui.details.RESULT_SHOW_FAVORITES"
    }
}