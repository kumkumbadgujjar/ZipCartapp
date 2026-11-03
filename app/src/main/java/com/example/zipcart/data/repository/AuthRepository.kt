package com.example.zipcart.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Local auth: accounts are stored on the device and the password is stored as a SHA-256 hash.
 * To use Firebase later, keep this public API and swap the internals.
 */
@Singleton
class AuthRepository @Inject constructor(@ApplicationContext context: Context) {

    private val prefs = context.getSharedPreferences("zipcart_auth", Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean = prefs.getString(KEY_SESSION_EMAIL, null) != null

    fun currentUserName(): String = prefs.getString(KEY_SESSION_NAME, "") ?: ""

    fun signUp(name: String, email: String, password: String): Result<Unit> {
        val key = userKey(email)
        if (prefs.contains(key)) {
            return Result.failure(IllegalStateException("An account with this email already exists"))
        }
        prefs.edit().putString(key, "${hash(password)}|$name").apply()
        saveSession(email, name)
        return Result.success(Unit)
    }

    fun login(email: String, password: String): Result<Unit> {
        val stored = prefs.getString(userKey(email), null)
            ?: return Result.failure(IllegalStateException("No account found for this email"))
        val (savedHash, name) = stored.split("|", limit = 2).let { it[0] to it.getOrElse(1) { "" } }
        if (savedHash != hash(password)) {
            return Result.failure(IllegalStateException("Incorrect password"))
        }
        saveSession(email, name)
        return Result.success(Unit)
    }

    fun logout() {
        prefs.edit().remove(KEY_SESSION_EMAIL).remove(KEY_SESSION_NAME).apply()
    }

    private fun saveSession(email: String, name: String) {
        prefs.edit()
            .putString(KEY_SESSION_EMAIL, email.lowercase())
            .putString(KEY_SESSION_NAME, name)
            .apply()
    }

    private fun userKey(email: String) = "user_${email.trim().lowercase()}"

    private fun hash(value: String): String =
        MessageDigest.getInstance("SHA-256")
            .digest(value.toByteArray())
            .joinToString("") { "%02x".format(it) }

    private companion object {
        const val KEY_SESSION_EMAIL = "session_email"
        const val KEY_SESSION_NAME = "session_name"
    }
}