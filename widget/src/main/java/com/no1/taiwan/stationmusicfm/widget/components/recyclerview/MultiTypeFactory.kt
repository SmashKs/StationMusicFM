/*
 * Copyright (C) 2019 The Smash Ks Open Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.no1.taiwan.stationmusicfm.widget.components.recyclerview

import com.devrapid.adaptiverecyclerview.ViewTypeFactory
import com.no1.taiwan.stationmusicfm.ext.UnsupportedOperation

/**
 * A factory for providing the viewholder from an object data type to the recyclerview.
 */
class MultiTypeFactory(
    private val viewHolders: ViewHolderEntries
) : ViewTypeFactory() {
    override var transformMap
        get() = viewHolders.toMap().toMutableMap()
        set(_) = UnsupportedOperation("We don't allow this method to use!")

    // *** Here are the entity binding the specific hashcode. ***
    fun type(entity: MusicMultiVisitable) = entity.javaClass.hashCode()
}
