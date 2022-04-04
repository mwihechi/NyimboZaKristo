package com.tansoften.nyimbozakristo.storage

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "com.tansoften.nyimbo.storage.settings")
private const val TAG = "PreferencesManager"

enum class SortOrder { BY_NUMBER, BY_NAME }
enum class FontStyle { QUICK_SAND, ROBOTO }

data class FilterPreferences(val sortOrder: SortOrder)
data class FontPreferences(val fontStyle: FontStyle)


@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext private val context: Context) {

    // Retrieving search sort order
    val preferencesFlow = context.dataStore.data.catch { exception ->
        if (exception is IOException) {
            Log.e(TAG, "Error while reading preferences ", exception)
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val sortOrder = SortOrder.valueOf(
            preferences[PreferencesKeys.SORT_ORDER] ?: SortOrder.BY_NUMBER.name
        )
        FilterPreferences(sortOrder)
    }

    // Retrieving font style
    val fontPreferencesFlow = context.dataStore.data.catch { exception ->
        if (exception is IOException) {
            Log.e(TAG, "Error while reading preferences ", exception)
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val fontStyle = FontStyle.valueOf(
            preferences[PreferencesKeys.FONT_STYLE] ?: FontStyle.QUICK_SAND.name
        )
        FontPreferences(fontStyle = fontStyle)
    }

    // updating value of sort order
    suspend fun updateSortOrder(sortOrder: SortOrder) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SORT_ORDER] = sortOrder.name
        }
    }

    // updating value of font style
    suspend fun updateFontStyle(fontStyle: FontStyle) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.FONT_STYLE] = fontStyle.name
        }
    }


    // storing preference keys
    private object PreferencesKeys {
        val SORT_ORDER = stringPreferencesKey("sort_order")
        val FONT_STYLE = stringPreferencesKey("font_style")
    }
}