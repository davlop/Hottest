package com.davlop.hottest.data.model

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

@Entity(tableName = "Products")
data class Product(
    @PrimaryKey @ColumnInfo(name = "_id") val id: String = "NO_ID",
    val name: String = "DEFAULT_NAME",
    val brand: String = "DEFAULT_BRAND",
    val description: String = "DEFAULT_DESCRIPTION",
    val heat: Int? = null,
    val ingredients: List<String>? = null,
    val imageRef: String?,
    val categories: List<String> = listOf(),
    val price: Double? = null,
    var rating: Double? = 0.0,
    var ratingTotals: Int = 0,
    val origin: String? = null,
    val size: String? = null,
    val sellers: List<String>? = null,
    var ratings: Map<String, Int>? = null,
    val timestamp: OffsetDateTime = OffsetDateTime.now()
) {
    override fun toString() = id

    companion object {
        fun fromFirestoreMap(map: Map<String, Any>): Product {
            var rating = 0.0
            var ratingsNumber = 0
            val ratings = map["ratings"] as? Map<String, Int>
            ratings?.let {
                var ratingsTotal = 0.0
                it.forEach { entry ->
                    ratingsNumber++
                    ratingsTotal += entry.value
                }
                rating = ratingsTotal / ratingsNumber
            }

            return Product(
                map["name"] as String,
                map["name"] as String,
                map["brand"] as String,
                map["description"] as String,
                (map["heat"] as? String)?.toInt(),
                map["ingredients"] as? List<String>,
                map["imageRef"] as? String,
                map["categories"] as List<String>,
                (map["price"] as? String)?.toDouble(),
                rating,
                ratingsNumber,
                map["origin"] as? String,
                map["size"] as? String,
                map["sellers"] as? List<String>,
                ratings
            )
        }
    }
}

class ProductConverters {
    private val gson = Gson()
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return formatter.parse(value, OffsetDateTime::from)
        }
    }

    @TypeConverter
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }

    @TypeConverter
    fun listToJson(list: List<String>?): String = gson.toJson(list)

    @TypeConverter
    fun jsonToList(string: String): List<String>? {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun mapToString(map: Map<String, Int>?): String? {
        return gson.toJson(map)
    }

    @TypeConverter
    fun stringToMap(map: String?): Map<String, Int>? {
        val type = object : TypeToken<Map<String, Int>>() {}.type
        return gson.fromJson(map, type)
    }

}