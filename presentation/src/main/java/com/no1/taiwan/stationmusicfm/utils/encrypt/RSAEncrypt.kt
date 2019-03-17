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

package com.no1.taiwan.stationmusicfm.utils.encrypt

import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

class RSAEncrypt private constructor() {
    companion object {
        private const val CRYPTO_METHOD = "RSA"
        private const val CRYPTO_BITS = 2048
        private const val CRYPTO_TRANSFORM = "RSA/ECB/OAEPWithSHA1AndMGF1Padding"
        @Volatile private var INSTANCE: RSAEncrypt? = null

        fun getInstance(): RSAEncrypt {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = RSAEncrypt()
                INSTANCE = instance
                return instance
            }
        }
    }

    // Generates the key pair
    fun generateKeyPair(): Pair<String, String> {
        val kp: KeyPair
        val kpg = KeyPairGenerator.getInstance(CRYPTO_METHOD)

        kpg.initialize(CRYPTO_BITS)
        kp = kpg.genKeyPair()

        return kp.public.key() to kp.private.key()
    }

    // Encrypts a string
    fun encrypt(message: String, key: String): String {
        val encryptedBytes: ByteArray
        val pubKey = key.toPublicKey()
        val cipher = Cipher.getInstance(CRYPTO_TRANSFORM)

        cipher.init(Cipher.ENCRYPT_MODE, pubKey)
        encryptedBytes = cipher.doFinal(message.toByteArray(StandardCharsets.UTF_8))

        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    // Decrypts a message
    fun decrypt(message: String, privateKey: String): String {
        val decryptedBytes: ByteArray
        val key = privateKey.toPrivateKey()
        val cipher = Cipher.getInstance(CRYPTO_TRANSFORM)

        cipher.init(Cipher.DECRYPT_MODE, key)
        decryptedBytes = cipher.doFinal(Base64.decode(message, Base64.DEFAULT))

        return String(decryptedBytes)
    }

    // Converts a string to a PublicKey object
    private fun String.toPublicKey(): PublicKey {
        val keyBytes: ByteArray = Base64.decode(this, Base64.DEFAULT)
        val spec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(CRYPTO_METHOD)

        return keyFactory.generatePublic(spec)
    }

    // Converts a string to a PrivateKey object
    private fun String.toPrivateKey(): PrivateKey {
        val keyBytes: ByteArray = Base64.decode(this, Base64.DEFAULT)
        val spec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(CRYPTO_METHOD)

        return keyFactory.generatePrivate(spec)
    }

    private fun PublicKey.key() = Base64.encodeToString(this.encoded, Base64.DEFAULT)

    private fun PrivateKey.key() = Base64.encodeToString(this.encoded, Base64.DEFAULT)
}
