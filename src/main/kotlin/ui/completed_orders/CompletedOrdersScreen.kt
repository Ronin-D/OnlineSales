package ui.completed_orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.datetime.toJavaLocalDate
import model.CompletedOrder
import ui.order.OrderItem

@Composable
fun CompletedOrdersScreen(
    viewModel: CompletedOrdersViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
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
            val dateDialogState = rememberMaterialDialogState()
            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton("Ok")
                    negativeButton("Cancel")
                },
            ) {
                datepicker { date ->
                    viewModel.date.value=date
                    viewModel.loadCompletedOrders(date)
                }
            }
            Button(
                onClick = {
                    dateDialogState.show()
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.LightGray)
            ){
                Text(
                    text =viewModel.date.value?.toString()?:"Date",
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
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
                        "Online shop",
                        modifier = Modifier.width(120.dp).padding(16.dp),
                        fontSize = 12.sp
                    )
                    Text(
                        "Order date",
                        modifier = Modifier.width(120.dp),
                        fontSize = 12.sp
                    )
                    Text(
                        "Order time",
                        modifier = Modifier.width(120.dp),
                        fontSize = 12.sp
                    )

                    Text(
                        "Price",
                        modifier = Modifier.width(120.dp),
                        fontSize = 12.sp
                    )
                    Text(
                        "Count",
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
            itemsIndexed(viewModel.completedOrders){index,completedOrder->
                val items = viewModel.getAllCompletedOrdersByProduct(completedOrder.product)
                if (items.isNotEmpty()){
                    CompletedOrderItem(
                        completedOrder,
                        items,
                        viewModel.getSummary(items)
                    )
                }
            }
        }
    }
}

@Composable
fun CompletedOrderItem(
    completedOrder: CompletedOrder,
    completedOrders: List<CompletedOrder>,
    summary:Int
){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Product name: ${completedOrder.product.name}",
            modifier = Modifier.padding(vertical = 16.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
        Text(
            "Manufacturer: ${completedOrder.product.manufacturer}",
            modifier = Modifier,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
        Text(
            "Model: ${completedOrder.product.model}",
            modifier = Modifier.width(120.dp).padding(vertical = 16.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
        completedOrders.forEach {
            Row (
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Text(
                    it.onlineShop.email,
                    modifier = Modifier.width(120.dp).padding(16.dp),
                    fontSize = 12.sp
                )
                Text(
                    it.order.orderDate.toString(),
                    modifier = Modifier.width(120.dp),
                    fontSize = 12.sp
                )
                Text(
                    it.order.orderTime.toString(),
                    modifier = Modifier.width(120.dp),
                    fontSize = 12.sp
                )

                Text(
                    it.product.price.toString(),
                    modifier = Modifier.width(120.dp),
                    fontSize = 12.sp
                )
                Text(
                    it.order.count.toString(),
                    modifier = Modifier.width(120.dp),
                    fontSize = 12.sp
                )
                Text(
                    it.order.customer.name,
                    modifier = Modifier.width(120.dp),
                    fontSize = 12.sp
                )
                Text(
                    it.order.customer.surname,
                    modifier = Modifier.width(120.dp),
                    fontSize = 12.sp
                )
                Text(
                    it.order.customer.patronymic,
                    modifier = Modifier.width(120.dp),
                    fontSize = 12.sp
                )
            }
        }
        Text(
            "Summary: $summary",
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .background(Color.Gray),
            fontSize = 14.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
    }
}