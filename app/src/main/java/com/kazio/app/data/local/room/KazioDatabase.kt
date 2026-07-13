package com.kazio.app.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        IncomeEntity::class,
        ExpenseEntity::class,
        ShiftEntity::class,
        PlatformEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class KazioDatabase : RoomDatabase() {
    abstract fun incomeDao(): IncomeDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun shiftDao(): ShiftDao
    abstract fun platformDao(): PlatformDao
}
