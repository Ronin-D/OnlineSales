package ui.online_shops

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import model.OnlineShop

@Composable
fun AddOnlineShopDialog(
    onDismiss:()->Unit,
    onConfirm:(OnlineShop)->Unit
) {
    val emailField = remember {
        mutableStateOf("")
    }
    val isDeliveryFreeField = remember {
        mutableStateOf(false)
    }

    Dialog(
        onDismissRequest = onDismiss,
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Online shop",
                modifier = Modifier.padding(16.dp)
                )
            OutlinedTextField(
                value = emailField.value,
                onValueChange = {emailField.value = it},
                placeholder = {
                    Text("Email")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Checkbox(
                    checked = isDeliveryFreeField.value,
                    onCheckedChange = {
                        isDeliveryFreeField.value = it
                    }
                )
                Text("Free delivery")
            }
            Button(
                onClick = {
                    if(emailField.value.isNotBlank()){//todo
                        onConfirm(
                            OnlineShop(
                                email = emailField.value,
                                isDeliveryFree = isDeliveryFreeField.value
                            )
                        )
                    }
                },
                modifier = Modifier.padding(16.dp)
            ){
                Text("Confirm")
            }
        }
    }
}