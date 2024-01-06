package ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import util.Screens

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        items(viewModel.items) { screen->
            ScreenItem(
                screen,
                onSelect = viewModel.onNavigate
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun ScreenItem(
    screen: Screens,
    onSelect:(Screens)->Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(56.dp))
            .background(Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onSelect(screen)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                screen.name
            )
        }
    }
}