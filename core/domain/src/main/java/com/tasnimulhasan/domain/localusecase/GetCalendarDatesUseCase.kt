package com.tasnimulhasan.domain.localusecase

import com.tasnimulhasan.domain.repository.CalendarRepository
import com.tasnimulhasan.entity.calendar.CalendarDateEntity
import javax.inject.Inject

class GetCalendarDatesUseCase @Inject constructor(
    private val repository: CalendarRepository
) {
    suspend operator fun invoke(
        month: Int,
        year: Int,
        isHijriPrimary: Boolean
    ): List<CalendarDateEntity> {
        return repository.getCalendarData(month, year, isHijriPrimary)
    }
}