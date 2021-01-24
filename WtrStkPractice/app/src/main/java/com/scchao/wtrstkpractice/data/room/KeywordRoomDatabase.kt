package com.scchao.wtrstkpractice.data.room

import androidx.room.*
import com.scchao.wtrstkpractice.data.model.KeyWord
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun dbSetupModule() = module {
    single {
        Room.databaseBuilder(androidContext(), KeywordRoomDatabase::class.java, "wtrstk.db")
            .build()
    }
}

@Database(entities = [KeyWord::class], version = 1)
abstract class KeywordRoomDatabase : RoomDatabase() {
    abstract fun keywordDAO(): KeywordDAO
}
