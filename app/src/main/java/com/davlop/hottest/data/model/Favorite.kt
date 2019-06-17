package com.davlop.hottest.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Favorites",
    foreignKeys = [ForeignKey(
    entity = Product::class,
    parentColumns = arrayOf("_id"),
    childColumns = arrayOf("product_id"))]
)
data class Favorite(@PrimaryKey @ColumnInfo(name = "product_id") val productId: String = "NO_ID")