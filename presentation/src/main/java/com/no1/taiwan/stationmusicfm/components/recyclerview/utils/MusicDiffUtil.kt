package com.no1.taiwan.stationmusicfm.components.recyclerview.utils

import com.no1.taiwan.stationmusicfm.components.recyclerview.MusicDiffUtil
import com.no1.taiwan.stationmusicfm.components.recyclerview.MusicMultiVisitable

class MusicDiffUtil : MusicDiffUtil() {
    override var newList = mutableListOf<MusicMultiVisitable>()
    override var oldList = mutableListOf<MusicMultiVisitable>()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        newList[newItemPosition].hashCode() == oldList[oldItemPosition].hashCode()

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        newList[newItemPosition] == oldList[oldItemPosition]

    override fun getNewListSize() = newList.size

    override fun getOldListSize() = oldList.size
}
