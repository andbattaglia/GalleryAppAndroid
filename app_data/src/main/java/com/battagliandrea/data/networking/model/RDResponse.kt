package com.battagliandrea.data.networking.model


import com.battagliandrea.domain.model.Image
import com.google.gson.annotations.SerializedName

data class RDResponse(
    @SerializedName("data")
    val `data`: Data? = Data()
) {
    data class Data(
        @SerializedName("children")
        val children: List<Children?>? = listOf()
    ) {
        data class Children(
            @SerializedName("data")
            val `data`: Data? = Data()
        ) {
            data class Data(
                @SerializedName("author_fullname")
                val authorFullname: String? = "",
                @SerializedName("title")
                val title: String? = "",
                @SerializedName("thumbnail_height")
                val thumbnailHeight: Int? = 0,
                @SerializedName("thumbnail_width")
                val thumbnailWidth: Int? = 0,
                @SerializedName("name")
                val name: String? = "",
                @SerializedName("score")
                val score: Int? = 0,
                @SerializedName("author_premium")
                val authorPremium: Boolean? = false,
                @SerializedName("thumbnail")
                val thumbnail: String? = "",
                @SerializedName("domain")
                val domain: String? = "",
                @SerializedName("id")
                val id: String? = "",
                @SerializedName("author")
                val author: String? = "",
                @SerializedName("url")
                val url: String? = ""
            )
        }
    }
}

fun List<RDResponse.Data.Children?>.toImages(): List<Image>{
    return this
        .asSequence()
        .filterNotNull()
        .map { it.toImage() }
        .toList()
}

fun RDResponse.Data.Children?.toImage(): Image {
    return Image(
        author = this?.data?.author.orEmpty(),
        title = this?.data?.title.orEmpty(),
        thumbnail = this?.data?.thumbnail.orEmpty(),
        url = this?.data?.url.orEmpty()
    )
}