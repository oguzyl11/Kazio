package com.kazio.app.di

import android.content.Context
import androidx.room.Room
import com.kazio.app.data.local.room.ExpenseDao
import com.kazio.app.data.local.room.IncomeDao
import com.kazio.app.data.local.room.KazioDatabase
import com.kazio.app.data.local.room.PlatformDao
import com.kazio.app.data.local.room.ShiftDao
import com.kazio.app.data.local.room.VehicleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideKazioDatabase(@ApplicationContext context: Context): KazioDatabase {
        return Room.databaseBuilder(
            context,
            KazioDatabase::class.java,
            "kazio_db"
        ).build()
    }

    @Provides
    fun provideIncomeDao(database: KazioDatabase): IncomeDao = database.incomeDao()

    @Provides
    fun provideExpenseDao(database: KazioDatabase): ExpenseDao = database.expenseDao()

    @Provides
    fun provideShiftDao(database: KazioDatabase): ShiftDao = database.shiftDao()

    @Provides
    fun providePlatformDao(database: KazioDatabase): PlatformDao = database.platformDao()

    @Provides
    fun provideVehicleDao(database: KazioDatabase): VehicleDao = database.vehicleDao()
}
