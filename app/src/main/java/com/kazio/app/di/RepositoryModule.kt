package com.kazio.app.di

import com.kazio.app.data.repository.ExpenseRepositoryImpl
import com.kazio.app.data.repository.IncomeRepositoryImpl
import com.kazio.app.data.repository.PlatformRepositoryImpl
import com.kazio.app.data.repository.ShiftRepositoryImpl
import com.kazio.app.domain.repository.ExpenseRepository
import com.kazio.app.domain.repository.IncomeRepository
import com.kazio.app.domain.repository.PlatformRepository
import com.kazio.app.domain.repository.ShiftRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindIncomeRepository(
        incomeRepositoryImpl: IncomeRepositoryImpl
    ): IncomeRepository

    @Binds
    @Singleton
    abstract fun bindExpenseRepository(
        expenseRepositoryImpl: ExpenseRepositoryImpl
    ): ExpenseRepository

    @Binds
    @Singleton
    abstract fun bindShiftRepository(
        shiftRepositoryImpl: ShiftRepositoryImpl
    ): ShiftRepository

    @Binds
    @Singleton
    abstract fun bindPlatformRepository(
        platformRepositoryImpl: PlatformRepositoryImpl
    ): PlatformRepository
}
