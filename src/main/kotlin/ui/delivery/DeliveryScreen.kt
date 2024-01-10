package ui.delivery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.Delivery
import model.Order
import ui.order.AddOrderDialog
import ui.order.EditOrderDialog
import util.Mode

@Composable
fun DeliveryScreen(
    viewModel: DeliveryViewModel
) {
    LaunchedEffect(null){
        viewModel.loadDeliveries()
    }
    val deliveries = viewModel.deliveries.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        if (viewModel.isEditDeliveryDialogVisible.value){
            EditDeliveryDialog(
                onConfirm = {
                    viewModel.editDelivery(it)
                    viewModel.isEditDeliveryDialogVisible.value=false
                },
                onDelete = {
                    viewModel.deleteDelivery()
                },
                delivery = viewModel.selectedDelivery.value!!,
                onDismiss = {
                    viewModel.isEditDeliveryDialogVisible.value=false
                }
            )
        }
        if (viewModel.isEditDeliveryDialogVisible.value){
            EditDeliveryDialog(
                onConfirm = {
                    viewModel.editDelivery(it)
                    viewModel.isEditDeliveryDialogVisible.value = false
                },
                onDismiss = {
                    viewModel.isEditDeliveryDialogVisible.value = false
                },
                delivery = viewModel.selectedDelivery.value!!,
                onDelete = {
                    viewModel.deleteDelivery()
                    viewModel.isEditDeliveryDialogVisible.value = false
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            IconButton(
                onClick = {
                    viewModel.onNavigateBack()
                },
                modifier = Modifier.clip(RoundedCornerShape(56.dp))
            ){
                Icon(
                    painter = rememberVectorPainter(Icons.Default.ArrowBack),
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
            }
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    Row (
                        modifier = Modifier
                            .background(Color.Gray)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ){
                        Text(
                            "Order id",
                            modifier = Modifier.width(120.dp).padding(16.dp),
                            fontSize = 12.sp
                        )
                        Text(
                            "Date",
                            modifier = Modifier.width(120.dp).padding(16.dp),
                            fontSize = 12.sp
                        )
                        Text(
                            "Time",
                            modifier = Modifier.width(120.dp),
                            fontSize = 12.sp
                        )
                        Text(
                            "Courier name",
                            modifier = Modifier.width(120.dp),
                            fontSize = 12.sp
                        )

                        Text(
                            "Customer name",
                            modifier = Modifier.width(120.dp),
                            fontSize = 12.sp
                        )
                        Text(
                            "Customer surname",
                            modifier = Modifier.width(120.dp),
                            fontSize = 12.sp
                        )
                        Text(
                            "Customer patronymic",
                            modifier = Modifier.width(120.dp),
                            fontSize = 12.sp
                        )
                    }
                }
                itemsIndexed(deliveries.value){index,delivery->
                    val backgrColor = if (index%2==0){
                        Color.LightGray
                    }
                    else{
                        Color.Transparent
                    }
                    DeliveryItem(
                        delivery,
                        backgrColor,
                        onSelect = {
                            if (viewModel.workMode== Mode.Admin){
                                viewModel.selectedDelivery.value=delivery
                                viewModel.isEditDeliveryDialogVisible.value=true
                            }
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun DeliveryItem(
    delivery: Delivery,
    backgroundColor: Color,
    onSelect:()->Unit
){
    Row (
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .clickable {
                onSelect()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            delivery.id,
            modifier = Modifier
                .width(120.dp)
                .padding(16.dp),
            fontSize = 12.sp
        )
        Text(
            delivery.date.toString(),
            modifier = Modifier
                .width(120.dp),
            fontSize = 12.sp
        )
        Text(
            delivery.time.toString(),
            modifier = Modifier
                .width(120.dp),
            fontSize = 12.sp
        )
        Text(
            delivery.courierName,
            modifier = Modifier
                .width(120.dp),
            fontSize = 12.sp
        )
        Text(
            delivery.customer.name,
            modifier = Modifier.width(120.dp),
            fontSize = 12.sp
        )
        Text(
            delivery.customer.surname,
            modifier = Modifier.width(120.dp),
            fontSize = 12.sp
        )
        Text(
            delivery.customer.patronymic,
            modifier = Modifier.width(120.dp).padding(16.dp),
            fontSize = 12.sp
        )
    }
}