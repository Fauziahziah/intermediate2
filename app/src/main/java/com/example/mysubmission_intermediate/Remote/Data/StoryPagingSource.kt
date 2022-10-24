package com.example.mysubmission_intermediate.Remote.Data

import com.example.mysubmission_intermediate.Api.ApiService
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mysubmission_intermediate.Api.ApiConfig
import com.example.mysubmission_intermediate.Api.StoryResponseItem
import com.example.mysubmission_intermediate.Model.UserPreference
import kotlinx.coroutines.flow.first

class StoryPagingSource(private val pref: UserPreference):
    PagingSource<Int, StoryResponseItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryResponseItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val token = pref.getUser().first().token
            if (token.isNotEmpty()) {
                val response = ApiConfig.getApiService().getAllStories(
                    "Bearer $token",
                    position,
                    params.loadSize
                )

                LoadResult.Page(
                    data = response.storyResponseItems,
                    prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                    nextKey = if (response.storyResponseItems.isNullOrEmpty()) null else position + 1
                )
            } else {
                LoadResult.Error(Exception("Failed"))
            }
        } catch (execption: Exception) {
           return LoadResult.Error(execption)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, StoryResponseItem>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}


