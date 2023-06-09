package com.bridge.community.utils

import java.nio.charset.StandardCharsets
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.Key
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AES128(key: String) {
    private val ips: String
    private val keySpec: Key

    init {
        val keyBytes = ByteArray(16)
        val b = key.toByteArray(StandardCharsets.UTF_8)
        System.arraycopy(b, 0, keyBytes, 0, keyBytes.size)
        val keySpec = SecretKeySpec(keyBytes, "AES")
        ips = key.substring(0, 16)
        this.keySpec = keySpec
    }

    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class,
        InvalidAlgorithmParameterException::class,
        InvalidKeyException::class
    )
    fun encrypt(value: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, IvParameterSpec(ips.toByteArray()))
        val encrypted = cipher.doFinal(value.toByteArray(StandardCharsets.UTF_8))
        return String(Base64.getEncoder().encode(encrypted))
    }

    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class,
        InvalidAlgorithmParameterException::class,
        InvalidKeyException::class
    )
    fun decrypt(value: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, keySpec, IvParameterSpec(ips.toByteArray(StandardCharsets.UTF_8)))
        val decrypted = Base64.getDecoder().decode(value.toByteArray())
        return String(cipher.doFinal(decrypted), StandardCharsets.UTF_8)
    }
}