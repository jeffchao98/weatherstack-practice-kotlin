package com.scchao.wtrstkpractice.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keyword_table")
data class KeyWord(@PrimaryKey @ColumnInfo(name = "keyword") val keyword: String)