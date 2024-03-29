package ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import model.product.Product
import okhttp3.internal.notifyAll
import ui.ErrorDialog
import java.util.*

@Composable
fun EditOrderDialog(
    onDismiss:()->Unit,
    onConfirm:(Order)->Unit,
    onDelete:()->Unit,
    order: Order
) {
    val orderDateField = remember {
        mutableStateOf(order.orderDate)
    }
    val orderTimeField = remember {
        mutableStateOf(order.orderTime)
    }
    val count = remember {
        mutableStateOf(order.count.toString())
    }
    val phoneNumber = remember {
        mutableStateOf(order.phoneNumber)
    }
    val isAccepted = remember {
        mutableStateOf(order.isAccepted)
    }
    val name = remember {
        mutableStateOf(order.customer.name)
    }
    val surname = remember {
        mutableStateOf(order.customer.surname)
    }
    val patronymic = remember {
        mutableStateOf(order.customer.patronymic)
    }
    val shopEmail = remember {
        mutableStateOf(order.shopId)
    }
    val productId = remember {
        mutableStateOf(order.productId)
    }
    val isInputCorrect = remember {
        mutableStateOf<Boolean?>(null)
    }
    val errorMsg = remember {
        mutableStateOf("")
    }

    Dialog(
        onDismissRequest = onDismiss,
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                timepicker(is24HourClock = true) { time ->
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
                    enabled = !order.isAccepted,
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
                enabled = false,
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
                enabled = false,
                placeholder = {
                    Text("Product id")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            if (isInputCorrect.value == true){
                onConfirm(
                    Order(
                        orderTime = orderTimeField.value,
                        orderDate = orderDateField.value,
                        count = count.value.toInt(),
                        id = order.id,
                        isAccepted = isAccepted.value,
                        phoneNumber = phoneNumber.value,
                        customer = User(
                            id = order.customer.id,
                            name = name.value,
                            surname = surname.value,
                            patronymic = patronymic.value
                        ),
                        productId = productId.value,
                        shopId = shopEmail.value
                    )
                )
                isInputCorrect.value = null
            }
            else if (isInputCorrect.value==false){
                ErrorDialog(
                    message =errorMsg.value,
                    onDismiss = {
                        isInputCorrect.value=null
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        if (!phoneNumber.value.matches(Regex("^(\\+7)(\\d{10})$"))){
                            errorMsg.value = "phone number field is incorrect"
                            isInputCorrect.value=false
                        }
                        else if (orderDateField.value==null){
                            errorMsg.value = "date field is incorrect"
                            isInputCorrect.value=false
                        }
                        else if (orderTimeField.value==null){
                            errorMsg.value = "time field is incorrect"
                            isInputCorrect.value=false
                        }
                        else if (!count.value.matches(Regex("[1-9][0-9]*"))
                            ||count.value.isBlank()
                            ||count.value.length>6){
                            errorMsg.value = "count field is incorrect"
                            isInputCorrect.value=false
                        }
                        else if (!name.value.matches(Regex("[a-zA-ZА-Яа-я]*"))
                            ||name.value.length<3||name.value.length>45||name.value.isBlank()){
                            errorMsg.value = "name field is incorrect"
                            isInputCorrect.value=false
                        }
                        else if (!surname.value.matches(Regex("[a-zA-ZА-Яа-я]*"))
                            ||surname.value.length<3||surname.value.length>45||surname.value.isBlank()){
                            errorMsg.value = "surname field is incorrect"
                            isInputCorrect.value=false
                        }
                        else if (!patronymic.value.matches(Regex("[a-zA-ZА-Яа-я]*"))
                            ||patronymic.value.length<3||patronymic.value.length>45||patronymic.value.isBlank()) {
                            errorMsg.value = "patronymic field is incorrect"
                            isInputCorrect.value = false
                        }
                        else if (shopEmail.value.isBlank()){
                            errorMsg.value = "email must be filled"
                            isInputCorrect.value=false
                        }
                        else if (productId.value.isBlank()){
                            errorMsg.value = "product id must be filled"
                            isInputCorrect.value=false
                        }
                        else{
                            isInputCorrect.value=true
                        }
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                ){
                    Text("Confirm")
                }
                Button(
                    onClick = {
                        onDelete()
                    },
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ){
                    Text("Delete")
                }
            }

        }
    }
}