package com.battagliandrea.data.networking.model


import com.battagliandrea.domain.model.Image
import com.google.gson.annotations.SerializedName
import org.apache.commons.text.StringEscapeUtils

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
            val `data`: Data? = Data(),
            @SerializedName("kind")
            val kind: String? = ""
        ) {
            data class Data(
                @SerializedName("author")
                val author: String? = "",
                @SerializedName("author_fullname")
                val authorFullname: String? = "",
                @SerializedName("domain")
                val domain: String? = "",
                @SerializedName("id")
                val id: String? = "",
                @SerializedName("preview")
                val preview: Preview? = Preview(),
                @SerializedName("thumbnail")
                val thumbnail: String? = "",
                @SerializedName("thumbnail_height")
                val thumbnailHeight: Any? = Any(),
                @SerializedName("thumbnail_width")
                val thumbnailWidth: Any? = Any(),
                @SerializedName("title")
                val title: String? = "",
                @SerializedName("url")
                val url: String? = ""
            ) {
                data class Preview(
                    @SerializedName("enabled")
                    val enabled: Boolean? = false,
                    @SerializedName("images")
                    val images: List<Image?>? = listOf()
                ) {
                    data class Image(
                        @SerializedName("id")
                        val id: String? = "",
                        @SerializedName("resolutions")
                        val resolutions: List<Resolution?>? = listOf(),
                        @SerializedName("source")
                        val source: Source? = Source(),
                        @SerializedName("variants")
                        val variants: Variants? = Variants()
                    ) {
                        data class Resolution(
                            @SerializedName("height")
                            val height: Int? = 0,
                            @SerializedName("url")
                            val url: String? = "",
                            @SerializedName("width")
                            val width: Int? = 0
                        )

                        data class Source(
                            @SerializedName("height")
                            val height: Int? = 0,
                            @SerializedName("url")
                            val url: String? = "",
                            @SerializedName("width")
                            val width: Int? = 0
                        )

                        class Variants(
                        )
                    }
                }
            }
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
        id = this?.data?.author.orEmpty(),
        author = this?.data?.author.orEmpty(),
        title = this?.data?.title.orEmpty(),
        thumbnailUrl = this?.data?.thumbnail.orEmpty(),
        imageUrl = StringEscapeUtils.unescapeHtml4(this?.data?.preview?.images?.firstOrNull()?.source?.url.orEmpty())
    )
}