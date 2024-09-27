An app that plays sample videos. 

It fetches video metadata and their links directly from [assets/videos.json](https://github.com/DanielRendox/video-player-android/blob/main/assets/videos.json)
file in this repo with the help of Retrofit. This data is then cached in the local Room database.

FeedScreen displays list of video thumbnails and titles. Clicking on a thumbnail opens VideoPlayerScreen where the actual video is played. From there, users can switch videos forwards or backwards using player buttons. The player stops automatically when the VideoPlayerScreen is no longer visible.

## This project uses

- Jetpack Compose for the user interface, with a single Activity and no Fragments
- Room database for local data storage
- Koin for dependency injection
- Retrofit for working with REST API
- Kotlin coroutines and flow for asynchronous requests
- kotlinx-serialization for decoding JSON files into Kotlin objects
- Coil for loading and caching images
- Media3's ExoPlayer for video player functionality
- androidx-navigation for navigating between screens
- MVI pattern
- CLEAN architecture with data and presentation layer
