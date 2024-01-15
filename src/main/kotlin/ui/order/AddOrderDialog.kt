package ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import model.OnlineShop
import model.Order
import model.User
import model.product.Product
import ui.ErrorDialog
import java.util.*

@Composable
fun AddOrderDialog(
    onDismiss:()->Unit,
    onConfirm:(Order)->Unit,
    products:List<Product>,
    shops:List<OnlineShop>
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
    val isShopDropMenuExpanded = remember {
        mutableStateOf(false)
    }
    val isProductDropMenuExpanded = remember {
        mutableStateOf(false)
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
                timepicker (is24HourClock = true){ time ->
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isShopDropMenuExpanded.value=true
                    }
            ){
                Row (
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .background(Color.LightGray)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = if (shopEmail.value.isEmpty()){
                            "Email"
                        } else{
                            shopEmail.value
                        }
                        ,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                    )
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.ArrowDropDown),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                DropdownMenu(
                    onDismissRequest = {
                       isShopDropMenuExpanded.value = false
                    },
                    expanded =isShopDropMenuExpanded.value,
                    modifier = Modifier.fillMaxWidth()
                ){
                    shops.forEach { shop->
                        DropdownMenuItem(
                            onClick = {
                                shopEmail.value = shop.email
                                isShopDropMenuExpanded.value = false
                            }
                        ){
                            Text(shop.email)
                        }
                    }
                }
            }
            Text("Product id")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isProductDropMenuExpanded.value=true
                    }
            ){
                Row (
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .background(Color.LightGray)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = if (productId.value.isEmpty()){
                            "Product id"
                        } else{
                            productId.value
                        }
                        ,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                    )
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.ArrowDropDown),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                DropdownMenu(
                    onDismissRequest = {
                        isProductDropMenuExpanded.value = false
                    },
                    expanded =isProductDropMenuExpanded.value,
                    modifier = Modifier.fillMaxWidth()
                ){
                    products.forEach { product->
                        DropdownMenuItem(
                            onClick = {
                                productId.value = product.id
                                isProductDropMenuExpanded.value = false
                            }
                        ){
                            Text(product.id)
                        }
                    }
                }
            }
            if (isInputCorrect.value == true){
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
                isInputCorrect.value=null
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
        }
    }
}