package com.example.vitalflow.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.*

object LanguageUtils {
    // Supported languages
    enum class Language(val code: String, val displayName: Int) {
        SYSTEM("system", com.example.vitalflow.R.string.settings_language_system),
        ENGLISH("en", com.example.vitalflow.R.string.settings_language_en),
        GERMAN("de", com.example.vitalflow.R.string.settings_language_de),
        RUSSIAN("ru", com.example.vitalflow.R.string.settings_language_ru)
    }

    // Get current app language
    fun getCurrentLanguage(context: Context): Language {
        val currentLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            @Suppress("DEPRECATION")
            context.resources.configuration.locale
        }

        return when (currentLocale.language) {
            "de" -> Language.GERMAN
            "ru" -> Language.RUSSIAN
            "en" -> Language.ENGLISH
            else -> Language.SYSTEM
        }
    }

    // Set app language
    fun setLanguage(context: Context, language: Language) {
        val localeList = when (language) {
            Language.SYSTEM -> {
                // Use system default
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    context.resources.configuration.locales
                } else {
                    @Suppress("DEPRECATION")
                    LocaleListCompat.create(context.resources.configuration.locale)
                }
            }
            else -> {
                LocaleListCompat.create(Locale(language.code))
            }
        }

        AppCompatDelegate.setApplicationLocales(localeList)
    }

    // Update configuration with selected locale
    fun updateConfiguration(context: Context, language: Language): Configuration {
        val locale = when (language) {
            Language.SYSTEM -> getSystemLocale(context)
            else -> Locale(language.code)
        }

        return updateConfigurationWithLocale(context, locale)
    }

    // Get system locale
    private fun getSystemLocale(context: Context): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            @Suppress("DEPRECATION")
            context.resources.configuration.locale
        }
    }

    // Update configuration with specific locale
    private fun updateConfigurationWithLocale(context: Context, locale: Locale): Configuration {
        Locale.setDefault(locale)
        val config = context.resources.configuration
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocales(LocaleListCompat.create(locale).unwrap())
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
        }
        
        return config
    }

    // Get supported languages
    fun getSupportedLanguages(): List<Language> = Language.values().toList()

    // Check if the language is supported
    fun isLanguageSupported(languageCode: String): Boolean {
        return Language.values().any { it.code == languageCode }
    }

    // Get fallback language (English)
    fun getFallbackLanguage(): Language = Language.ENGLISH
}

// Extension function to easily update activity locale
fun Activity.updateLocale(language: LanguageUtils.Language) {
    val config = LanguageUtils.updateConfiguration(this, language)
    resources.updateConfiguration(config, resources.displayMetrics)
    recreate()
}
