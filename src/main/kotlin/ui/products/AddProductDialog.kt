package ui.products

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import model.OnlineShop
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import model.product.Characteristics
import model.product.Product
import java.util.UUID
import kotlin.random.Random

@Composable
fun AddProductDialog(
    onDismiss:()->Unit,
    onConfirm:(Product)->Unit
) {
    val productNameField = remember {
        mutableStateOf("")
    }
    val productModelField = remember {
        mutableStateOf("")
    }
    val warrantyDateField = remember {
        mutableStateOf<LocalDate?>(null)
    }
    val price = remember {
        mutableStateOf("")
    }
    val manufacturer = remember {
        mutableStateOf("")
    }
    val image = remember {
        mutableStateOf("")
    }
    val width = remember {
        mutableStateOf("")
    }
    val height = remember {
        mutableStateOf("")
    }
    val length = remember {
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
                "Product",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            OutlinedTextField(
                value = productNameField.value,
                onValueChange = {productNameField.value = it},
                placeholder = {
                    Text("Name")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            OutlinedTextField(
                value = productModelField.value,
                onValueChange = {productModelField.value = it},
                placeholder = {
                    Text("Model")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            val dialogState = rememberMaterialDialogState()
            MaterialDialog(
                dialogState = dialogState,
                buttons = {
                    positiveButton("Ok")
                    negativeButton("Cancel")
                },
            ) {
                datepicker { date ->
                    warrantyDateField.value = date
                }
            }
            OutlinedTextField(
                value = price.value,
                onValueChange = {price.value = it},
                placeholder = {
                    Text("Price")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            OutlinedTextField(
                value = manufacturer.value,
                onValueChange = {manufacturer.value = it},
                placeholder = {
                    Text("Manufacturer")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            OutlinedTextField(
                value = image.value,
                onValueChange = {image.value = it},
                placeholder = {
                    Text("Image url")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Text("Characteristics")
            OutlinedTextField(
                value = length.value,
                onValueChange = {length.value = it},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = {
                    Text("Length")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            OutlinedTextField(
                value = width.value,
                onValueChange = {width.value = it},
                placeholder = {
                    Text("Width")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            OutlinedTextField(
                value = height.value,
                onValueChange = {height.value = it},
                placeholder = {
                    Text("Height")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Button(
                onClick = {
                    dialogState.show()
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(86.dp))
                    .fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(Color.LightGray)
            ){
                Text(
                    text =warrantyDateField.value?.toString()?:"Warranty period",
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
            Button(
                onClick = {
                    onConfirm(
                        Product(
                            id = UUID.randomUUID().toString(),
                            name = productNameField.value,
                            model = productModelField.value,
                            manufacturer = manufacturer.value,
                            warrantyDate = warrantyDateField.value!!,
                            price = price.value.toInt(),
                            image = image.value,
                            characteristics = Characteristics(
                                id = UUID.randomUUID().toString(),
                                width = width.value.toInt(),
                                height = height.value.toInt(),
                                length = length.value.toInt()
                            )
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