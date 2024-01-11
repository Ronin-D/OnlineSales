package ui.delivery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
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
import kotlinx.datetime.*
import model.Delivery
import model.Order
import model.User
import ui.ErrorDialog

@Composable
fun EditDeliveryDialog(
    onDismiss:()->Unit,
    onConfirm:(Delivery)->Unit,
    onDelete:()->Unit,
    delivery: Delivery
) {
    val deliveryDateField = remember {
        mutableStateOf(delivery.date)
    }
    val deliveryTimeField = remember {
        mutableStateOf(delivery.time)
    }
    val courierNameField = remember {
        mutableStateOf(delivery.courierName)
    }
    val orderIdField = remember {
        mutableStateOf(delivery.id)
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
                value = courierNameField.value,
                onValueChange = {courierNameField.value = it},
                placeholder = {
                    Text("Courier name")
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
                    deliveryDateField.value=date
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
                    deliveryTimeField.value=time
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
                    text =deliveryDateField.value?.toString()?:"Delivery date",
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
                    text =deliveryTimeField.value?.toString()?:"Delivery time",
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
            Text("Order info")
            OutlinedTextField(
                value = orderIdField.value,
                onValueChange = {},
                enabled = false,
                placeholder = {
                    Text("Order id")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            if (isInputCorrect.value == true){
                onConfirm(
                    Delivery(
                        id = orderIdField.value,
                        date = deliveryDateField.value.toJavaLocalDate().toKotlinLocalDate(),
                        time = deliveryTimeField.value.toJavaLocalTime().toKotlinLocalTime(),
                        courierName = courierNameField.value,
                        customer = delivery.customer
                    )
                )
            }
            else if (isInputCorrect.value==false){
                ErrorDialog(
                    message =errorMsg.value,
                    onDismiss = {
                        isInputCorrect.value=null
                    }
                )
            }
            Button(
                onClick = {
                    if(deliveryDateField.value==null){
                        errorMsg.value = "delivery date must be filled"
                        isInputCorrect.value=false
                    }
                    else if (deliveryTimeField.value==null){
                        errorMsg.value = "delivery time must be filled"
                        isInputCorrect.value=false
                    }
                    else if (!courierNameField.value.matches(Regex("[a-zA-ZА-Яа-я]*"))
                        ||courierNameField.value.length<3||courierNameField.value.length>100){
                        errorMsg.value = "courier name is incorrect"
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