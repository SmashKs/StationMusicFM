package com.no1.taiwan.stationmusicfm.components.recyclerview

import com.devrapid.adaptiverecyclerview.AdaptiveAdapter
import com.devrapid.adaptiverecyclerview.AdaptiveDiffUtil
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import com.devrapid.adaptiverecyclerview.IVisitable

typealias MusicViewHolder = AdaptiveViewHolder<MultiTypeFactory, MusicMultiVisitable>
typealias MusicMultiVisitable = IVisitable<MultiTypeFactory>
typealias MusicAdapter = AdaptiveAdapter<MultiTypeFactory, MusicMultiVisitable, MusicViewHolder>
typealias MusicDiffUtil = AdaptiveDiffUtil<MultiTypeFactory, MusicMultiVisitable>
typealias MultiData = MutableList<MusicMultiVisitable>
