package ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import model.Order
import model.User
import java.util.*

@Composable
fun AddOrderDialog(
    onDismiss:()->Unit,
    onConfirm:(Order)->Unit
) {
    val orderDateField = remember {
        mutableStateOf<LocalDate?>(null)
    }
    val orderTimeField = remember {
        mutableStateOf<LocalTime?>(null)
    }
    val count = remember {
        mutableStateOf("")
    }
    val phoneNumber = remember {
        mutableStateOf("")
    }
    val isAccepted = remember {
        mutableStateOf(false)
    }
    val name = remember {
        mutableStateOf("")
    }
    val surname = remember {
        mutableStateOf("")
    }
    val patronymic = remember {
        mutableStateOf("")
    }
    val shopEmail = remember {
        mutableStateOf("")
    }
    val productId = remember {
        mutableStateOf("")
    }

    Dialog(
        onDismissRequest = onDismiss,
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Order",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            OutlinedTextField(
                value = count.value,
                onValueChange = {count.value = it},
                placeholder = {
                    Text("Count")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            OutlinedTextField(
                value = phoneNumber.value,
                onValueChange = {phoneNumber.value = it},
                placeholder = {
                    Text("Phone number")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            val dateDialogState = rememberMaterialDialogState()
            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton("Ok")
                    negativeButton("Cancel")
                },
            ) {
                datepicker { date ->
                    orderDateField.value=date
                }
            }
            val timeDialogState = rememberMaterialDialogState()
            MaterialDialog(
                dialogState = timeDialogState,
                buttons = {
                    positiveButton("Ok")
                    negativeButton("Cancel")
                },
            ) {
                timepicker { time ->
                    orderTimeField.value=time
                }
            }

            Button(
                onClick = {
                    dateDialogState.show()
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(86.dp))
                    .fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(Color.LightGray)
            ){
                Text(
                    text =orderDateField.value?.toString()?:"order date",
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
            Button(
                onClick = {
                    timeDialogState.show()
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(86.dp))
                    .fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(Color.LightGray)
            ){
                Text(
                    text =orderTimeField.value?.toString()?:"order time",
                    modifier = Modifier
                        .padding(8.dp)
                )
            }

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Checkbox(
                    checked = isAccepted.value,
                    onCheckedChange = {
                        isAccepted.value = it
                    }
                )
                Text("Accepted")
            }

            Text("Customer info")
            OutlinedTextField(
                value = name.value,
                onValueChange = {name.value = it},
                placeholder = {
                    Text("Name")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            OutlinedTextField(
                value = surname.value,
                onValueChange = {surname.value = it},
                placeholder = {
                    Text("Surname")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            OutlinedTextField(
                value = patronymic.value,
                onValueChange = {patronymic.value = it},
                placeholder = {
                    Text("Patronymic")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Text("Shop email")
            OutlinedTextField(
                value = shopEmail.value,
                onValueChange = {shopEmail.value = it},
                placeholder = {
                    Text("Email")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Text("Product id")
            OutlinedTextField(
                value = productId.value,
                onValueChange = {productId.value = it},
                placeholder = {
                    Text("Product id")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Button(
                onClick = {
                    onConfirm(
                        Order(
                            orderTime = orderTimeField.value!!,
                            orderDate = orderDateField.value!!,
                            count = count.value.toInt(),
                            id = UUID.randomUUID().toString(),
                            isAccepted = isAccepted.value,
                            phoneNumber = phoneNumber.value,
                            customer = User(
                                id = UUID.randomUUID().toString(),
                                name = name.value,
                                surname = surname.value,
                                patronymic = patronymic.value
                            ),
                            productId = productId.value,
                            shopId = shopEmail.value
                        )
                    )
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            ){
                Text("Confirm")
            }
        }
    }
}