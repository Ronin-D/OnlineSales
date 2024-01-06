import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import navigation.DefaultRootComponent
import navigation.RootComponent
import navigation.RootContent
import ui.auth.AuthScreen
import ui.home.HomeScreen
import util.Screens
import java.util.Stack

@Composable
fun App(root: RootComponent) {
    val backStack = remember {
        mutableStateOf(Stack<Screens>())
    }
    MaterialTheme {
        RootContent(component = root, modifier = Modifier.fillMaxSize())
    }
}

fun main() {
    val lifecycle = LifecycleRegistry()

    val root =
        DefaultRootComponent(
            componentContext = DefaultComponentContext(lifecycle = lifecycle),
        )
    application {
        Window(onCloseRequest = ::exitApplication) {
            App(root)
        }
    }
}
