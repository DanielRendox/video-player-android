package com.rendox.videoplayer.data

import com.rendox.videoplayer.model.VideoMetadata
import com.rendox.videoplayer.model.VpResult
import com.rendox.videoplayer.network.GitHubApi
import com.rendox.videoplayer.network.model.asExternalModel
import kotlinx.coroutines.CancellationException

class VideoRepositoryImpl(
    private val api: GitHubApi,
) : VideoRepository {

    override suspend fun getVideos(): VpResult<List<VideoMetadata>> = try {
        VpResult.Success(data = api.getVideos().map { it.asExternalModel() })
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        VpResult.Error(e)
    }

    override suspend fun getVideoByUrl(url: String): VpResult<VideoMetadata?> =
        when (val result = getVideos()) {
            is VpResult.Error -> result
            is VpResult.Success -> VpResult.Success(data = result.data.find { it.url == url })
        }
}