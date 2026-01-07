package com.tasnimulhasan.data.repoimpl.remote

import com.tasnimulhasan.data.NetworkBoundResource
import com.tasnimulhasan.data.apiservice.HadithApiService
import com.tasnimulhasan.data.mapper.FetchHadithApiMapper
import com.tasnimulhasan.data.mapper.FetchHadithBookChaptersApiMapper
import com.tasnimulhasan.data.mapper.FetchHadithBooksApiMapper
import com.tasnimulhasan.data.mapper.mapFromApiResponse
import com.tasnimulhasan.domain.apiusecase.hadith.FetchHadithBookChaptersUseCase
import com.tasnimulhasan.domain.apiusecase.hadith.FetchHadithsUseCase
import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.domain.repository.remote.HadithRepository
import com.tasnimulhasan.entity.hadith.HadithApiEntity
import com.tasnimulhasan.entity.hadith.HadithBookApiEntity
import com.tasnimulhasan.entity.hadith.HadithChaptersApiEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HadithRepoImpl @Inject constructor(
    private val apiService: HadithApiService,
    private val fetchHadithBooksApiMapper: FetchHadithBooksApiMapper,
    private val fetchHadithBookChaptersApiMapper: FetchHadithBookChaptersApiMapper,
    private val fetchHadithApiMapper: FetchHadithApiMapper,
    private val networkBoundResources: NetworkBoundResource
) : HadithRepository {

    override suspend fun fetchHadithBooks(): Flow<DataResult<List<HadithBookApiEntity>>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.fetchHadithBooks()
            }, mapper = fetchHadithBooksApiMapper
        )
    }

    override suspend fun fetchHadithBookChapters(params: FetchHadithBookChaptersUseCase.Params): Flow<DataResult<List<HadithChaptersApiEntity>>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.fetchHadithBookChapters(
                    bookSlug = params.bookSlug
                )
            }, mapper = fetchHadithBookChaptersApiMapper
        )
    }

    override suspend fun fetchHadiths(params: FetchHadithsUseCase.Params): Flow<DataResult<HadithApiEntity>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.fetchHadithsByChapter(
                    bookSlug = params.bookSlug,
                    chapterNumber = params.chapterNumber,
                    page = params.page,
                    limit = params.limit
                )
            }, mapper = fetchHadithApiMapper
        )
    }

}