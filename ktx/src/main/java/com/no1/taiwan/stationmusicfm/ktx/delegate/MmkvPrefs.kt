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

package com.no1.taiwan.stationmusicfm.ktx.delegate

import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/** Delegate the shared preferences variable. */
class MmkvPrefs<T>(
    private var defaultValue: T,
    private val objectType: Class<T>? = null,
    private var onChange: (() -> Unit)? = null
) : ReadWriteProperty<Any, T> {
    companion object {
        private var prefs: MMKV by Delegates.notNull()
        fun setPrefSettings(mmkv: MMKV = MMKV.defaultMMKV()) {
            synchronized(this) { prefs = mmkv }
        }
    }

    // For storing an object to the preferences. This method needs to add the `Gson` dependency.
    private val gson by lazy { Gson() }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        val name = property.name

        return when (defaultValue) {
            is Boolean -> prefs.decodeBool(name, defaultValue as Boolean) as T
            is Int -> prefs.decodeInt(name, defaultValue as Int) as T
            is Long -> prefs.decodeLong(name, defaultValue as Long) as T
            is Float -> prefs.decodeFloat(name, defaultValue as Float) as T
            is Double -> prefs.decodeDouble(name, defaultValue as Double) as T
            is String -> prefs.decodeString(name, defaultValue as String) as T
            is Set<*> -> prefs.decodeStringSet(name, defaultValue as Set<String>) as T
            // Using json format to deserialize a string to an object.
            else -> prefs.decodeString(name, null)?.let {
                gson.fromJson(it,
                              objectType)
            } ?: defaultValue
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        val name = property.name

        prefs.also { mmkv ->
            @Suppress("UNCHECKED_CAST")
            when (defaultValue) {
                is Boolean -> mmkv.encode(name, value as Boolean)
                is Int -> mmkv.encode(name, value as Int)
                is Long -> mmkv.encode(name, value as Long)
                is Float -> mmkv.encode(name, value as Float)
                is Double -> mmkv.encode(name, defaultValue as Double)
                is String -> mmkv.encode(name, value as String)
                is ByteArray -> mmkv.encode(name, value as ByteArray)
                is Set<*> -> mmkv.encode(name, value as Set<String>)
                // Using json format to serialize an object to a string.
                else -> mmkv.encode(name, gson.toJson(value))
            }
            this.onChange?.invoke()
        }.apply()
    }
}
