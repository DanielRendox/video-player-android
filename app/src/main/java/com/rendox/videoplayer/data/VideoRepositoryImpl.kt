package com.rendox.videoplayer.data

import com.rendox.videoplayer.model.VideoMetadata
import com.rendox.videoplayer.model.VpResult

class VideoRepositoryImpl : VideoRepository {

    override fun getVideoByUrl(url: String) = VpResult.Success(
        data = VideoMetadata(
            url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            thumbUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg",
            title = "Big Buck Bunny",
            subtitle = "By Blender Foundation",
            description = "Big Buck Bunny tells the story of a giant rabbit with a heart bigger than himself. When one sunny day three rodents rudely harass him, something snaps... and the rabbit ain't no bunny anymore! In the typical cartoon tradition he prepares the nasty rodents a comical revenge.\n\nLicensed under the Creative Commons Attribution license\nhttp://www.bigbuckbunny.org",
        )
    )
}