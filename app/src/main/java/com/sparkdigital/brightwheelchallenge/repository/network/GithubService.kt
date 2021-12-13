package com.sparkdigital.brightwheelchallenge.repository.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories?q=stars:>0")
    suspend fun getRepositories(@Query("per_page") perPage: Int = 10, @Query("page") page: Int = 1): Response<RepoResponseWrapper>

    @GET("repos/{owner}/{repo}/contributors?per_page=1")
    suspend fun getRepoTopContributor(@Path("owner") owner: String, @Path("repo") repo: String): Response<List<ContributorResponse>>

}