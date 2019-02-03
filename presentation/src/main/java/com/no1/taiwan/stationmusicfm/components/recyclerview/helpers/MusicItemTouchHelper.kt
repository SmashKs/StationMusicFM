package com.no1.taiwan.stationmusicfm.components.recyclerview.helpers

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.UP
import androidx.recyclerview.widget.RecyclerView

class MusicItemTouchHelper(
    private val adapterHelper: AdapterItemTouchHelper,
    private val viewHelper: ViewItemTouchCallback? = null
) : ItemTouchHelper.SimpleCallback(UP or DOWN, LEFT) {
    /**
     * Called when ItemTouchHelper wants to move the dragged item from its old position to
     * the add position.
     *
     *
     * If this method returns true, ItemTouchHelper assumes `viewHolder` has been moved
     * to the adapter position of `target` ViewHolder
     * ([ ViewHolder#getAdapterPosition()][ViewHolder.getAdapterPosition]).
     *
     *
     * If you don't support drag & drop, this method will never be called.
     *
     * @param recyclerView The RecyclerView to which ItemTouchHelper is attached to.
     * @param viewHolder   The ViewHolder which is being dragged by the user.
     * @param target       The ViewHolder over which the currently active item is being
     * dragged.
     * @return True if the `viewHolder` has been moved to the adapter position of
     * `target`.
     * @see .onMoved
     */
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        viewHelper?.onItemMoved(viewHolder.adapterPosition, target.adapterPosition)
        adapterHelper.onItemMoved(viewHolder.adapterPosition, target.adapterPosition)

        return true
    }

    /**
     * Called when a ViewHolder is swiped by the user.
     *
     *
     * If you are returning relative directions ([.START] , [.END]) from the
     * [.getMovementFlags] method, this method
     * will also use relative directions. Otherwise, it will use absolute directions.
     *
     *
     * If you don't support swiping, this method will never be called.
     *
     *
     * ItemTouchHelper will keep a reference to the View until it is detached from
     * RecyclerView.
     * As soon as it is detached, ItemTouchHelper will call
     * [.clearView].
     *
     * @param viewHolder The ViewHolder which has been swiped by the user.
     * @param direction  The direction to which the ViewHolder is swiped. It is one of
     * [.UP], [.DOWN],
     * [.LEFT] or [.RIGHT]. If your
     * [.getMovementFlags]
     * method
     * returned relative flags instead of [.LEFT] / [.RIGHT];
     * `direction` will be relative as well. ([.START] or [                   ][.END]).
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition

        viewHelper?.onItemSwiped(position)
        adapterHelper.onItemSwiped(position)
    }
}
