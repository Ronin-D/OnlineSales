package ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import model.Order
import ui.ErrorDialog
import util.Mode

@Composable
fun OrdersScreen(
    viewModel: OrdersViewModel
) {
    LaunchedEffect(null){
        viewModel.loadOrders()
        viewModel.getProducts()
        viewModel.getShops()
    }
    val orders = viewModel.orders.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {

        if (viewModel.isDeliveryAccepted.value){
            if (viewModel.shops.value!=null||viewModel.products.value!=null){
                AddDeliveryDialog(
                    onConfirm = {
                        if (viewModel.isAddOrderDialogVisible.value){
                            viewModel.addOrder(
                                order = viewModel.orderForDelivery.value!!,
                                delivery = it
                            )
                            viewModel.isAddOrderDialogVisible.value = false
                        }
                        else if (viewModel.isEditOrderDialogVisible.value){
                            viewModel.editOrder(
                                updatedOrder = viewModel.orderForDelivery.value!!,
                                delivery = it
                            )
                            viewModel.isEditOrderDialogVisible.value=false
                        }
                        viewModel.isDeliveryAccepted.value = false
                    },
                    order = viewModel.orderForDelivery.value!!,
                    onDismiss = {
                        viewModel.isDeliveryAccepted.value = false
                    }
                )
            }
        }
        if (viewModel.isAddOrderDialogVisible.value){
            AddOrderDialog(
                onConfirm = {
                    if(it.isAccepted){
                        viewModel.isDeliveryAccepted.value=true
                        viewModel.orderForDelivery.value=it
                    }
                    else{
                        viewModel.addOrder(it)
                        viewModel.isAddOrderDialogVisible.value=false
                    }

                },
                shops = viewModel.shops.value!!,
                products = viewModel.products.value!!,
                onDismiss = {
                    viewModel.isAddOrderDialogVisible.value=false
                }
            )
        }
        if (viewModel.isEditOrderDialogVisible.value){
            EditOrderDialog(
                onConfirm = {
                    if (!viewModel.selectedOrder.value!!.isAccepted&&it.isAccepted){
                        viewModel.isDeliveryAccepted.value=true
                        viewModel.orderForDelivery.value=it
                    }else{
                        viewModel.editOrder(it)
                        viewModel.isEditOrderDialogVisible.value = false
                    }
                },
                onDismiss = {
                    viewModel.isEditOrderDialogVisible.value = false
                },
                order = viewModel.selectedOrder.value!!,
                onDelete = {
                    viewModel.deleteOrder()
                    viewModel.isEditOrderDialogVisible.value = false
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
                            "Order date",
                            modifier = Modifier.width(120.dp).padding(16.dp),
                            fontSize = 12.sp
                        )
                        Text(
                            "Order time",
                            modifier = Modifier.width(120.dp),
                            fontSize = 12.sp
                        )
                        Text(
                            "Count",
                            modifier = Modifier.width(120.dp),
                            fontSize = 12.sp
                        )

                        Text(
                            "Phone number",
                            modifier = Modifier.width(120.dp),
                            fontSize = 12.sp
                        )
                        Text(
                            "Is accepted",
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
                        Text(
                            "Shop email",
                            modifier = Modifier.width(120.dp),
                            fontSize = 12.sp
                        )
                        Text(
                            "Product id",
                            modifier = Modifier.width(120.dp),
                            fontSize = 12.sp
                        )
                    }
                }
                itemsIndexed(orders.value){index,order->
                    val backgrColor = if (index%2==0){
                        Color.LightGray
                    }
                    else{
                        Color.Transparent
                    }
                    OrderItem(
                        order,
                        backgrColor,
                        onSelect = {
                            if (viewModel.workMode==Mode.Admin){
                                viewModel.selectedOrder.value=order
                                viewModel.isEditOrderDialogVisible.value=true
                            }

                        }
                    )
                }
            }
        }
        if (viewModel.workMode==Mode.Admin){
            Button(
                onClick = {
                    viewModel.isAddOrderDialogVisible.value=true
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(56.dp))
            ){
                Text(
                    "Add",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }

}

@Composable
fun OrderItem(
    order: Order,
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
            order.orderDate.toString(),
            modifier = Modifier
                .width(120.dp)
                .padding(16.dp),
            fontSize = 12.sp
        )
        Text(
            order.orderTime.toString(),
            modifier = Modifier
                .width(120.dp),
            fontSize = 12.sp
        )
        Text(
            order.count.toString(),
            modifier = Modifier
                .width(120.dp),
            fontSize = 12.sp
        )
        Text(
            order.phoneNumber,
            modifier = Modifier
                .width(120.dp),
            fontSize = 12.sp
        )
        Text(
            order.isAccepted.toString(),
            modifier = Modifier
                .width(120.dp),
            fontSize = 12.sp
        )
        Text(
            order.customer.name,
            modifier = Modifier.width(120.dp),
            fontSize = 12.sp
        )
        Text(
            order.customer.surname,
            modifier = Modifier.width(120.dp),
            fontSize = 12.sp
        )
        Text(
            order.customer.patronymic,
            modifier = Modifier.width(120.dp),
            fontSize = 12.sp
        )
        Text(
            order.shopId,
            modifier = Modifier.width(120.dp),
            fontSize = 12.sp
        )
        Text(
            order.productId,
            modifier = Modifier.width(120.dp).padding(16.dp),
            fontSize = 12.sp
        )
    }
}