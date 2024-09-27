package com.rendox.videoplayer.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rendox.videoplayer.model.VideoMetadata


@Entity(tableName = "video_metadata")
data class VideoMetadataEntity(
    @PrimaryKey val url: String,
    val thumbUrl: String,
    val title: String,
    val subtitle: String,
    val description: String
)

fun VideoMetadataEntity.asExternalModel() = VideoMetadata(
    url = url,
    thumbUrl = thumbUrl,
    title = title,
    subtitle = subtitle,
    description = description,
)

fun VideoMetadata.asEntity() = VideoMetadataEntity(
    url = url,
    thumbUrl = thumbUrl,
    title = title,
    subtitle = subtitle,
    description = description,
)