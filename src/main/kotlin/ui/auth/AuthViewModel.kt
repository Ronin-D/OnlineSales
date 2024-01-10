package ui.auth

import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import database.repository.UsersDatabaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import util.Mode

class AuthViewModel(
    componentContext: ComponentContext,
   val  onNavigateToHome:(String)->Unit
): ComponentContext by componentContext {
    private val databaseRepository = UsersDatabaseRepository()
    val isDropMenuExpanded = mutableStateOf(false)
    val workMode = mutableStateOf("work mode")
    val login =  mutableStateOf("")
    val password = mutableStateOf("")
    val isLoading = mutableStateOf(false)
    val isUserFound = mutableStateOf<Boolean?>(null)
    val isDialogShown = mutableStateOf(false)

    fun login(){
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            isUserFound.value =  databaseRepository.signIn(login.value,password.value,workMode.value)
            isLoading.value=false
        }
    }
}