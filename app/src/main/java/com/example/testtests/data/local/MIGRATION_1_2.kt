package com.example.testtests.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Пример: добавление нового столбца в таблицу
        database.execSQL("ALTER TABLE CourseEntity ADD COLUMN newColumn TEXT")
    }
}
