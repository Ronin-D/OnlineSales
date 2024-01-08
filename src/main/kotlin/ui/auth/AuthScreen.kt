package ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ui.ErrorDialog
import util.Constants.workModes

@Composable
fun  AuthScreen(
    viewModel:AuthViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ){
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Choose your work mode",
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.isDropMenuExpanded.value=true
                    }
            ) {
                Row (
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .background(Color.LightGray)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        viewModel.workMode.value,
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
                        viewModel.isDropMenuExpanded.value = false
                    },
                    expanded = viewModel.isDropMenuExpanded.value,
                    modifier = Modifier.fillMaxWidth()
                ){
                    workModes.forEach {mode->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.workMode.value = mode.name
                                viewModel.isDropMenuExpanded.value = false
                            }
                        ){
                            Text(mode.name)
                        }
                    }
                }
            }
            OutlinedTextField(
                value = viewModel.login.value,
                onValueChange = {viewModel.login.value = it},
                placeholder = {
                              Text("Login")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            OutlinedTextField(
                value = viewModel.password.value,
                onValueChange = {viewModel.password.value = it},
                placeholder = {
                    Text("Password")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            if (viewModel.isLoading.value){
                CircularProgressIndicator(modifier = Modifier.size(30.dp))
            }
            else{
                viewModel.isUserFound.value?.let {
                    if (it){
                        viewModel.isUserFound.value = null
                        viewModel.onNavigateToHome(viewModel.workMode.value)
                    }
                    else{
                        ErrorDialog(
                            message = "User not found",
                            onDismiss = {
                                viewModel.isUserFound.value = null
                            }
                        )
                    }
                }
                Button(
                    onClick = {
                        viewModel.login()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(56.dp))
                ){
                    Text(
                        "Submit",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}