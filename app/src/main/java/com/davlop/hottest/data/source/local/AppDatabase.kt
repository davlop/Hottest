package com.davlop.hottest.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.davlop.hottest.data.model.Favorite
import com.davlop.hottest.data.model.Product
import com.davlop.hottest.data.model.ProductConverters
import com.davlop.hottest.data.source.local.room.ProductDao

@Database(entities = [Product::class, Favorite::class], version = 1, exportSchema = false)
@TypeConverters(ProductConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

}
