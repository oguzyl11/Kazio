package com.kazio.app.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazio.app.data.local.datastore.DataStoreRepository
import com.kazio.app.domain.model.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val nameInput: String = "",
    val emailInput: String = "",
    val pinInput: String = "",
    val errorMessage: String? = null,
    val isAuthenticated: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val userPreferences: StateFlow<UserPreferences?> = dataStoreRepository.userPreferencesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.update { it.copy(nameInput = name, errorMessage = null) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(emailInput = email, errorMessage = null) }
    }

    fun onPinChange(pin: String) {
        if (pin.length <= 4) { // 4 haneli PIN
            _uiState.update { it.copy(pinInput = pin, errorMessage = null) }
        }
    }

    fun register() {
        val name = _uiState.value.nameInput
        val email = _uiState.value.emailInput
        val pin = _uiState.value.pinInput

        if (name.isBlank() || email.isBlank() || pin.length < 4) {
            _uiState.update { it.copy(errorMessage = "Lütfen tüm alanları doldurun ve 4 haneli PIN girin.") }
            return
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.update { it.copy(errorMessage = "Lütfen geçerli bir e-posta adresi girin.") }
            return
        }

        viewModelScope.launch {
            dataStoreRepository.registerUser(name, email, pin)
            _uiState.update { it.copy(isAuthenticated = true) }
        }
    }

    fun login() {
        val enteredEmail = _uiState.value.emailInput
        val enteredPin = _uiState.value.pinInput
        val savedEmail = userPreferences.value?.userEmail
        val savedPin = userPreferences.value?.userPin

        if (enteredEmail.isBlank() || enteredPin.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Lütfen e-posta adresinizi ve PIN kodunuzu girin.") }
            return
        }

        if (enteredEmail.equals(savedEmail, ignoreCase = true) && enteredPin == savedPin) {
            _uiState.update { it.copy(isAuthenticated = true) }
        } else {
            _uiState.update { it.copy(errorMessage = "Hatalı e-posta veya PIN.") }
        }
    }

    fun logout() {
        _uiState.update { it.copy(isAuthenticated = false, pinInput = "", nameInput = "", emailInput = "", errorMessage = null) }
    }

    fun resetAccount() {
        viewModelScope.launch {
            dataStoreRepository.clearUser()
        }
    }

    fun restartOnboarding() {
        viewModelScope.launch {
            dataStoreRepository.updateOnboardingSeen(false)
        }
    }
}
