package ui.delivery

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
import kotlinx.datetime.*
import model.Delivery
import model.User

@Composable
fun AddDeliveryDialog(
    onDismiss:()->Unit,
    onConfirm:(Delivery)->Unit
) {
    val deliveryDateField = remember {
        mutableStateOf<LocalDate?>(null)
    }
    val deliveryTimeField = remember {
        mutableStateOf<LocalTime?>(null)
    }
    val courierNameField = remember {
        mutableStateOf("")
    }
    val orderIdField = remember {
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
                timepicker { time ->
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
                onValueChange = {orderIdField.value = it},
                placeholder = {
                    Text("Order id")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Button(
                onClick = {
                    onConfirm(
                        Delivery(
                            id = orderIdField.value,
                            date = deliveryDateField.value!!.toJavaLocalDate().toKotlinLocalDate(),
                            time = deliveryTimeField.value!!.toJavaLocalTime().toKotlinLocalTime(),
                            courierName = courierNameField.value,
                            customer = User()
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