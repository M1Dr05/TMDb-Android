# TMDb-Kotlin
A simple Android client for The Movie DB

This project is an Android app which displays data from [The Movie Database](https://www.themoviedb.org/) The Movie Database API.

<a href="https://play.google.com/store/apps/details?id=com.github.midros.tmdb">
  <img src="https://github.com/M1Dr05/TMDb/blob/master/screenshots/unnamed.png" width="150" height="54">
</a>

# Features
- Get Movies
  - Get the Popular Movies
  - Get the Top Rated Movies
  - Get the Upoming
  - Get the Now Playing
- Get Tv shows
  - Get the Popular Tv shows
  - Get the Top Rated
  - Get the On Tv
  - Get the Airing Today
- Get People
- Show the movie detail and tv shows details
- Get the images from a movie and tv shows
- Get the trailers/clips from a movie and tv show
- Search Movies, tv shows and persons

# Build this project
In the `build.gradle` assign your `API_KEY_TMDB`

- Get the TheMoviedb API KEY [here](https://developers.themoviedb.org/3/getting-started)

```java
ext {
        API_KEY_TMDB = "\"YOU_API_KEY\""
}
```

# Implementations
- Fully written in [Kotlin](https://kotlinlang.org/) language
- [Retrofit 2](http://square.github.io/retrofit)
- [Dagger 2](https://google.github.io/dagger/)
- [RxKotlin 2](https://github.com/ReactiveX/RxKotlin)
- [RxAndroid 2](https://github.com/ReactiveX/RxAndroid)
- [DraggablePanel](https://github.com/pedrovgs/DraggablePanel) - DEPRECATED
- [QSVideoPlayer](https://github.com/tohodog/QSVideoPlayer)
- [Gson](https://github.com/google/gson)
- [Glide](https://github.com/bumptech/glide)
- [MaterialSearchView](https://github.com/MiguelCatalan/MaterialSearchView)
- [KAndroid](https://github.com/pawegio/KAndroid)
- [Android Ktx](https://github.com/android/android-ktx)
- [Android View Animations](https://github.com/daimajia/AndroidViewAnimations)
- [Circle-Progress-View](https://github.com/jakob-grabner/Circle-Progress-View)
- Other support libraries Androidx - AppCompat, RecyclerView, CardView, Design
