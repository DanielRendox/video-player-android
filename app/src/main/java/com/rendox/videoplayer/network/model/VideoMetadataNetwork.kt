package com.rendox.videoplayer.network.model

import com.rendox.videoplayer.model.VideoMetadata
import kotlinx.serialization.Serializable

@Serializable
data class VideoMetadataNetwork(
    val source: String,
    val thumb: String,
    val title: String,
    val subtitle: String,
    val description: String,
)

fun VideoMetadataNetwork.asExternalModel() = VideoMetadata(
    url = source,
    thumbUrl = thumb,
    title = title,
    subtitle = subtitle,
    description = description,
)