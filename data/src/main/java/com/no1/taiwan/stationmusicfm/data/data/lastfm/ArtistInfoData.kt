package com.no1.taiwan.stationmusicfm.data.data.lastfm

import android.media.Image
import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR

data class ArtistInfoData(
    val artist: ArtistData?
) : Data {
    data class ArtistData(
        val name: String? = DEFAULT_STR,
        val mbid: String? = DEFAULT_STR,
        val match: String? = DEFAULT_STR,
        val url: String? = DEFAULT_STR,
        @SerializedName("image")
        val images: List<Image>? = listOf(),
        val streamable: String? = DEFAULT_STR,
        val listeners: String? = DEFAULT_STR,
        @SerializedName("ontour")
        val onTour: String? = DEFAULT_STR,
        @SerializedName("playcount")
        val playCount: String? = DEFAULT_STR,
        val stats: StatsData? = null,
        val similar: SimilarData? = null,
        val tags: CommonLastFmData.TagsData? = null,
        val bio: BioData? = null
    ) : Data

    data class BioData(
        val links: LinksData?,
        val published: String?,
        val summary: String?,
        val content: String?
    ) : Data

    data class LinksData(
        val link: LinkData?
    ) : Data

    data class LinkData(
        @SerializedName("#text")
        val text: String?,
        val rel: String?,
        val href: String?
    ) : Data

    data class StatsData(
        val listeners: String?,
        @SerializedName("playcount")
        val playCount: String?
    ) : Data

    data class SimilarData(
        @SerializedName("artist")
        val artists: List<ArtistData>?
    ) : Data
}
