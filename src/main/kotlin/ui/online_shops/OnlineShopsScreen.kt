package ui.online_shops

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import model.OnlineShop
import util.Mode

@Composable
fun OnlineShopsScreen(
    viewModel: OnlineShopsViewModel
) {
    LaunchedEffect(null){
        viewModel.loadShops()
    }
    val onlineShops = viewModel.onlineShops.collectAsState()
    Box(modifier = Modifier.fillMaxSize()){
        if (viewModel.isAddOnlineShopDialogShown.value){
            AddOnlineShopDialog(
                onConfirm = {
                    viewModel.addShop(it)
                    viewModel.isAddOnlineShopDialogShown.value = false
                },
                onDismiss = {
                    viewModel.isAddOnlineShopDialogShown.value = false
                }
            )
        }
        if (viewModel.isEditOnlineShopDialogShown.value){
            EditOnlineShopDialog(
                onConfirm = {
                    viewModel.updateShop(it)
                    viewModel.isEditOnlineShopDialogShown.value = false
                },
                onDelete = {
                    viewModel.deleteShop()
                    viewModel.isEditOnlineShopDialogShown.value = false
                },
                shop = viewModel.selectedOnlineShop.value!!,
                onDismiss = {
                   viewModel.isEditOnlineShopDialogShown.value = false
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
                            .fillMaxWidth()
                            .background(color = Color.Gray)
                    ){
                        Text(
                            "Email",
                            modifier = Modifier.padding(16.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            "Is delivery free",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                itemsIndexed(onlineShops.value){index,onlineShop->
                    val backgrColor = if (index%2==0){
                        Color.LightGray
                    }
                    else{
                        Color.Transparent
                    }
                    OnlineShopItem(
                        onlineShop,
                        backgrColor,
                        viewModel
                    )
                }
            }
        }

        Button(
            onClick = {
                viewModel.isAddOnlineShopDialogShown.value = true
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

@Composable
fun OnlineShopItem(
    onlineShop: OnlineShop,
    backGroundColor:Color,
    viewModel: OnlineShopsViewModel
){
    Row(
        modifier = Modifier
            .background(backGroundColor)
            .fillMaxWidth()
            .clickable {
                viewModel.selectedOnlineShop.value = onlineShop
                viewModel.isEditOnlineShopDialogShown.value = true
            }
    ) {
        Text(
            onlineShop.email,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            onlineShop.isDeliveryFree.toString(),
            modifier = Modifier.padding(16.dp)
        )
    }
}