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

package com.no1.taiwan.stationmusicfm.widget.components.dialog

import androidx.constraintlayout.widget.ConstraintProperties
import com.devrapid.dialogbuilder.support.DialogFragmentTemplate
import com.no1.taiwan.stationmusicfm.widget.R

internal fun DialogFragmentTemplate.Builder.defaultBuilder() {
    onTransitionBlock = {
        // Set the dialog transition animation.
        it.window?.attributes?.windowAnimations = R.style.Dialog_Fragment_Animation
    }
    onStartBlock = {
        val realWidth = ConstraintProperties.WRAP_CONTENT
        val realHeight = ConstraintProperties.WRAP_CONTENT
        it.dialog?.window?.apply {
            // Set the dialog size.
            setLayout(realWidth, realHeight)
            // Set the dialog to round background.
            setBackgroundDrawableResource(R.drawable.dialog_round_background)
        }
    }
}
