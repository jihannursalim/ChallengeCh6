package jihan.binar.challengechapter6.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey val id: Long,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val posterUrl: String
)
