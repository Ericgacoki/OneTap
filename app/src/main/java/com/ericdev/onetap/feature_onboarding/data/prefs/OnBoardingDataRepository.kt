package com.ericdev.onetap.feature_onboarding.data.prefs

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.ericdev.onetap.feature_onboarding.domain.repository.OnBoardingRepository
import com.ericdev.onetap.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OnBoardingDataRepository @Inject constructor(val app: Application) : OnBoardingRepository {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "prefs")
        private val FINISHED_ON_BOARDING = booleanPreferencesKey(Constants.FINISHED_ON_BOARDING)
    }

    override suspend fun setOnBoardingFinished(finished: Boolean) {
        app.applicationContext.dataStore.edit { prefs ->
            prefs[FINISHED_ON_BOARDING] = finished
        }
    }

    override fun getOnBoardingFinished(): Flow<Boolean> {
        val finishedOnBoarding: Flow<Boolean> = app.applicationContext.dataStore.data
            .map { prefs ->
                prefs[FINISHED_ON_BOARDING] ?: false
            }
        return finishedOnBoarding
    }
}
