package com.rendox.videoplayer.model

import kotlin.Exception

sealed interface VpResult <out T> {
    data class Success <out T> (val data: T) : VpResult <T>
    data class Error (val exception: Exception): VpResult <Nothing>
}