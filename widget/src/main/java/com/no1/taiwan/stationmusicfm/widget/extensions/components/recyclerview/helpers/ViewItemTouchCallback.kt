package com.no1.taiwan.stationmusicfm.widget.extensions.components.recyclerview.helpers

interface ViewItemTouchCallback {
    fun onItemSwiped(position: Int)
    fun onItemMoved(fromPosition: Int, toPosition: Int)
}
