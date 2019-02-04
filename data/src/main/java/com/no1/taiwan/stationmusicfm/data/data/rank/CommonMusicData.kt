package com.no1.taiwan.stationmusicfm.data.data.rank

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR

object CommonMusicData {
    data class UserData(
        val address: String = DEFAULT_STR,
        @SerializedName("avatar_url")
        val avatarUrl: String = DEFAULT_STR,
        val birthday: Int = 0,
        val email: String = DEFAULT_STR,
        val gender: Int = 0,
        val phone: String = DEFAULT_STR,
        val platform: Int = 0,
        @SerializedName("platform_uid")
        val platformUid: String = DEFAULT_STR,
        @SerializedName("screen_name")
        val screenName: String = DEFAULT_STR,
        val uid: String = DEFAULT_STR
    ) : Data

    data class SongData(
        val artist: String = DEFAULT_STR,
        @SerializedName("cdn_coverURL")
        val cdnCoverUrl: String = DEFAULT_STR,
        @SerializedName("copyright_type")
        val copyrightType: Int = 0,
        val coverURL: String = DEFAULT_STR,
        val flag: Int = 0,
        val length: Int = 0,
        val lyricURL: String = DEFAULT_STR,
        val mv: MvData = MvData(),
        @SerializedName("ori_coverURL")
        val oriCoverUrl: String = DEFAULT_STR,
        @SerializedName("other_sources")
        val otherSources: List<Any> = emptyList(),
        @SerializedName("share_uri")
        val shareUri: String = DEFAULT_STR,
        val sid: Int = 0,
        @SerializedName("song_id_ext")
        val songIdExt: String = DEFAULT_STR,
        val source: SourceData = SourceData(),
        val title: String = DEFAULT_STR,
        val uploader: String = DEFAULT_STR,
        val url: String = DEFAULT_STR
    ) : Data

    data class PlayListData(
        @SerializedName("comment_count")
        val commentCount: Int = 0,
        @SerializedName("fav_count")
        val favCount: Int = 0,
        @SerializedName("has_fav")
        val hasFav: Boolean = false,
        @SerializedName("is_cover_modified")
        val isCoverModified: Boolean = false,
        val permission: Int = 0,
        @SerializedName("played_count")
        val playedCount: String = DEFAULT_STR,
        @SerializedName("share_count")
        val shareCount: Int = 0,
        @SerializedName("share_link")
        val shareLink: String = DEFAULT_STR,
        @SerializedName("share_uri")
        val shareUri: String = DEFAULT_STR,
        @SerializedName("song_list_cover")
        val songListCover: String = DEFAULT_STR,
        @SerializedName("song_list_desc")
        val songListDesc: String = DEFAULT_STR,
        @SerializedName("song_list_id")
        val songListId: String = DEFAULT_STR,
        @SerializedName("song_list_name")
        val songListName: String = DEFAULT_STR,
        @SerializedName("song_list_type")
        val songListType: Int = 0,
        @SerializedName("song_num")
        val songNum: Int = 0,
        val songs: List<SongData> = emptyList(),
        @SerializedName("tag_ids")
        val tagIds: List<Any> = emptyList(),
        val tags: List<Any> = emptyList(),
        val user: UserData = UserData()
    ) : Data

    data class MvData(
        val comments: Int = 0,
        @SerializedName("cover_image")
        val coverImage: String = DEFAULT_STR,
        val ctime: String = DEFAULT_STR,
        val description: String = DEFAULT_STR,
        val dislikes: Int = 0,
        val duration: String = DEFAULT_STR,
        val embeddable: Int = 0,
        @SerializedName("fm_mv_active")
        val fmMvActive: Int = 0,
        val id: Int = 0,
        @SerializedName("is_active")
        val isActive: Int = 0,
        @SerializedName("is_public")
        val isPublic: Int = 0,
        @SerializedName("language_id")
        val languageId: Int = 0,
        val likes: Int = 0,
        val mtime: String = DEFAULT_STR,
        @SerializedName("published_at")
        val publishedAt: String = DEFAULT_STR,
        val rate: Int = 0,
        @SerializedName("region_allowed")
        val regionAllowed: String = DEFAULT_STR,
        @SerializedName("region_blocked")
        val regionBlocked: String = DEFAULT_STR,
        @SerializedName("review_info")
        val reviewInfo: String = DEFAULT_STR,
        val source: Int = 0,
        val title: String = DEFAULT_STR,
        val views: Long = 0,
        @SerializedName("y_video_id")
        val yVideoId: String = DEFAULT_STR
    ) : Data

    data class SourceData(
        val unknown: Any? = null
    ) : Data
}
