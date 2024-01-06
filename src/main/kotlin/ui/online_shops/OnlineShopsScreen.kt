package ui.online_shops

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text("Email")
                Text("IsDeliveryFree")
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
                backgrColor
            )
        }
    }
}

@Composable
fun OnlineShopItem(
    onlineShop: OnlineShop,
    backGroundColor:Color
){
    Row(
        modifier = Modifier
            .background(backGroundColor)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(onlineShop.email)
        Text(onlineShop.isDeliveryFree.toString())
    }
}