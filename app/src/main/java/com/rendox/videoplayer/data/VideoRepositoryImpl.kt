package com.rendox.videoplayer.data

import com.rendox.videoplayer.database.VideoMetadataDao
import com.rendox.videoplayer.database.asEntity
import com.rendox.videoplayer.database.asExternalModel
import com.rendox.videoplayer.model.VideoMetadata
import com.rendox.videoplayer.model.VpResult
import com.rendox.videoplayer.network.GitHubApi
import com.rendox.videoplayer.network.model.asExternalModel
import kotlinx.coroutines.CancellationException

class VideoRepositoryImpl(
    private val api: GitHubApi,
    private val videoMetadataDao: VideoMetadataDao,
) : VideoRepository {

    override suspend fun getVideos(): VpResult<List<VideoMetadata>> {
        val videos = videoMetadataDao.getAllVideos()
        return if (videos.isEmpty()) {
            try {
                VpResult.Success(data = api.getVideos().map { it.asExternalModel() }).also { result ->
                    videoMetadataDao.insertVideos(result.data.map { it.asEntity() })
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                VpResult.Error(e)
            }
        } else {
            VpResult.Success(data = videos.map { it.asExternalModel() })
        }
    }

    override suspend fun getVideoByUrl(url: String): VpResult<VideoMetadata?> {
        val video = videoMetadataDao.getVideoMetadata(url)
        if (video != null) return VpResult.Success(video.asExternalModel())
        return when (val result = getVideos()) {
            is VpResult.Error -> result
            is VpResult.Success -> {
                VpResult.Success(data = result.data.find { it.url == url }).also {
                    val videoEntity = it.data?.asEntity()
                    if (videoEntity != null) videoMetadataDao.insertVideos(listOf(videoEntity))
                }
            }
        }
    }
}