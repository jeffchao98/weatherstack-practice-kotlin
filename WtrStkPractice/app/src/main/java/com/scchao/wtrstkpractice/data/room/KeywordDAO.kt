package com.scchao.wtrstkpractice.data.room

import androidx.room.*
import com.scchao.wtrstkpractice.data.model.KeyWord

@Dao
interface KeywordDAO {
    @Query("SELECT * FROM keyword_table")
    suspend fun getAllWords(): List<KeyWord>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(keyword: KeyWord)

    @Delete
    suspend fun delete(keyword: KeyWord)

}