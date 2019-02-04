package com.no1.taiwan.stationmusicfm.data.data.lastfm

import android.media.Image
import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR

data class ArtistData(
    val artist: Artist?
) : Data {
    data class Artist(
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
        val stats: Stats? = null,
        val similar: Similar? = null,
        val tags: Tags? = null,
        val bio: Bio? = null
    ) : Data

    data class Bio(
        val links: Links?,
        val published: String?,
        val summary: String?,
        val content: String?
    ) : Data

    data class Links(val link: Link?)

    data class Link(
        @SerializedName("#text")
        val text: String?,
        val rel: String?,
        val href: String?
    ) : Data

    data class Stats(
        val listeners: String?,
        @SerializedName("playcount")
        val playCount: String?
    ) : Data

    data class Similar(
        @SerializedName("artist")
        val artists: List<Artist>?
    ) : Data
}
