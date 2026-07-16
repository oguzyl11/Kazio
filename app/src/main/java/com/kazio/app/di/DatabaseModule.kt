package com.kazio.app.di

import android.content.Context
import androidx.room.Room
import com.kazio.app.data.local.room.ExpenseDao
import com.kazio.app.data.local.room.IncomeDao
import com.kazio.app.data.local.room.KazioDatabase
import com.kazio.app.data.local.room.PlatformDao
import com.kazio.app.data.local.room.ShiftDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideKazioDatabase(
        @ApplicationContext context: Context,
        provider: Provider<KazioDatabase>
    ): KazioDatabase {
        return Room.databaseBuilder(
            context,
            KazioDatabase::class.java,
            "kazio_db"
        ).addCallback(object : androidx.room.RoomDatabase.Callback() {
            override fun onCreate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                super.onCreate(db)
                GlobalScope.launch(Dispatchers.IO) {
                    val platformDao = provider.get().platformDao()
                    platformDao.insertPlatform(com.kazio.app.data.local.room.PlatformEntity(name = "Trendyol Go", colorTag = "#FF8A65", isCustom = false))
                    platformDao.insertPlatform(com.kazio.app.data.local.room.PlatformEntity(name = "Getir", colorTag = "#9575CD", isCustom = false))
                    platformDao.insertPlatform(com.kazio.app.data.local.room.PlatformEntity(name = "Yemeksepeti", colorTag = "#E57373", isCustom = false))
                    platformDao.insertPlatform(com.kazio.app.data.local.room.PlatformEntity(name = "Uber", colorTag = "#90A4AE", isCustom = false))
                    platformDao.insertPlatform(com.kazio.app.data.local.room.PlatformEntity(name = "Bolt", colorTag = "#81C784", isCustom = false))
                    platformDao.insertPlatform(com.kazio.app.data.local.room.PlatformEntity(name = "BiTaksi", colorTag = "#FFD54F", isCustom = false))
                }
            }
        }).addMigrations(
            object : androidx.room.migration.Migration(1, 2) {
                override fun migrate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                    db.execSQL("ALTER TABLE shifts ADD COLUMN totalPausedDuration INTEGER NOT NULL DEFAULT 0")
                    db.execSQL("ALTER TABLE shifts ADD COLUMN isPaused INTEGER NOT NULL DEFAULT 0")
                    db.execSQL("ALTER TABLE shifts ADD COLUMN lastPausedAt INTEGER DEFAULT NULL")
                }
            }
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
}
