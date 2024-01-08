package ui.products

import androidx.compose.foundation.background
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
import kotlinx.datetime.LocalDate
import model.OnlineShop
import model.product.Characteristics
import model.product.Product
import java.util.*

@Composable
fun EditProductDialog(
    onDismiss:()->Unit,
    onConfirm:(Product)->Unit,
    onDelete:()->Unit,
    product: Product
) {
    val productNameField = remember {
        mutableStateOf(product.name)
    }
    val productModelField = remember {
        mutableStateOf(product.model)
    }
    val warrantyDateField = remember {
        mutableStateOf(product.warrantyDate)
    }
    val price = remember {
        mutableStateOf(product.price.toString())
    }
    val manufacturer = remember {
        mutableStateOf(product.manufacturer)
    }
    val image = remember {
        mutableStateOf(product.image)
    }
    val width = remember {
        mutableStateOf(product.characteristics.width.toString())
    }
    val height = remember {
        mutableStateOf(product.characteristics.height.toString())
    }
    val length = remember {
        mutableStateOf(product.characteristics.length.toString())
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
                    text =warrantyDateField.value.toString(),
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
            Button(
                onClick = {
                    onConfirm(
                        Product(
                            id = product.id,
                            name = productNameField.value,
                            model = productModelField.value,
                            manufacturer = manufacturer.value,
                            warrantyDate = warrantyDateField.value!!,
                            price = price.value.toInt(),
                            image = image.value,
                            characteristics = Characteristics(
                                id = product.characteristics.id,
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