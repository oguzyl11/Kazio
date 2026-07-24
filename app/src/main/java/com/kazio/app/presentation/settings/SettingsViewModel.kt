package com.kazio.app.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazio.app.data.local.datastore.DataStoreRepository
import com.kazio.app.data.local.room.KazioDatabase
import com.kazio.app.domain.model.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val database: KazioDatabase
) : ViewModel() {

    val userPreferences: StateFlow<UserPreferences?> = dataStoreRepository.userPreferencesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun updateProfile(name: String, vehicleModel: String, vehiclePlate: String) {
        viewModelScope.launch {
            dataStoreRepository.updateProfile(
                name = name.trim(),
                vehicleModel = vehicleModel.trim(),
                vehiclePlate = vehiclePlate.trim()
            )
        }
    }

    fun restartOnboarding() {
        viewModelScope.launch {
            dataStoreRepository.updateOnboardingSeen(false)
        }
    }

    fun deleteAccount(onComplete: () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.clearAllTables()
                val platformDao = database.platformDao()
                platformDao.insertPlatform(com.kazio.app.data.local.room.PlatformEntity(name = "Trendyol Go", colorTag = "#FF8A65", isCustom = false))
                platformDao.insertPlatform(com.kazio.app.data.local.room.PlatformEntity(name = "Getir", colorTag = "#9575CD", isCustom = false))
                platformDao.insertPlatform(com.kazio.app.data.local.room.PlatformEntity(name = "Yemeksepeti", colorTag = "#E57373", isCustom = false))
                platformDao.insertPlatform(com.kazio.app.data.local.room.PlatformEntity(name = "Uber", colorTag = "#90A4AE", isCustom = false))
                platformDao.insertPlatform(com.kazio.app.data.local.room.PlatformEntity(name = "Bolt", colorTag = "#81C784", isCustom = false))
                platformDao.insertPlatform(com.kazio.app.data.local.room.PlatformEntity(name = "BiTaksi", colorTag = "#FFD54F", isCustom = false))
            }
            dataStoreRepository.clearUser()
            onComplete()
        }
    }
}
