package com.scchao.wtrstkpractice.data.repository

import androidx.annotation.WorkerThread
import com.scchao.wtrstkpractice.data.room.KeywordDAO
import com.scchao.wtrstkpractice.data.model.KeyWord
import com.scchao.wtrstkpractice.data.room.KeywordRoomDatabase
import org.koin.dsl.module

val keywordRepoModule = module {
    factory { KeyWordRepository(get()) }
    factory { get<KeywordRoomDatabase>().keywordDAO() }
}

class KeyWordRepository(private val keywordDao: KeywordDAO) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun loadAll() = keywordDao.getAllWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(keyword: String) = keywordDao.insert(KeyWord((keyword)))

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun remove(keyword: String) = keywordDao.delete(KeyWord((keyword)))
}