package com.example.movies.util.constants

class AppConstants {
    companion object {
        /*CodeReview images dimensions are too big to be used which will take a lot of time and network data to be loaded
            check this link to see how to get a smaller size https://developers.themoviedb.org/3/getting-started/images
            And you can use a larger size for tablets
         */
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original/"
        const val YOUTUBE_IMAGE_BASE_URL = "https://img.youtube.com/vi/"
        const val YOUTUBE_IMAGE_HIGH_QUALITY = "hqdefault.jpg"
        const val REQUEST_SHOW_FAVORITES = "REQUEST_SHOW_FAVORITES"
        const val RESULT_SHOW_FAVORITES = "RESULT_SHOW_FAVORITES"

        const val KEY_STATE_SELECTED_MOVIE="KEY_STATE_SELECTED_MOVIE"
        const val KEY_LAST_EMITTED_VALUE="KEY_LAST_EMITTED_VALUE"
    }
}