package com.battagliandrea.galleryappandroid.data.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.battagliandrea.domain.model.Image
import org.apache.commons.text.StringEscapeUtils

@Entity(tableName = "bookmarks")

data class BookmarkEntity constructor(
        @PrimaryKey @NonNull
        @ColumnInfo(name = "id") val id: String = "",
        @ColumnInfo(name = "author") val author: String = "",
        @ColumnInfo(name = "title") val title: String = "",
        @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String = "",
        @ColumnInfo(name = "image_url") val imageUrl: String = ""
)

fun List<BookmarkEntity>.toImages(): List<Image>{
        return this
                .asSequence()
                .filterNotNull()
                .map { it.toImage() }
                .toList()
}

fun BookmarkEntity.toImage(): Image {
        return Image(
                id = this.id,
                author = this.author,
                title = this.title,
                thumbnailUrl = this.thumbnailUrl,
                imageUrl = this.imageUrl
        )
}

fun List<Image>.toBookmarks(): List<BookmarkEntity>{
        return this
                .asSequence()
                .filterNotNull()
                .map { it.toBookmark() }
                .toList()
}

fun Image.toBookmark(): BookmarkEntity {
        return BookmarkEntity(
                id = this.id,
                author = this.author,
                title = this.title,
                thumbnailUrl = this.thumbnailUrl,
                imageUrl = this.imageUrl
        )
}