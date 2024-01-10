package ui.products

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.product.Product
import util.Mode

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel
) {
    LaunchedEffect(null){
        viewModel.loadProducts()
    }
    val products = viewModel.products.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        if (viewModel.isAddProductDialogVisible.value){
            AddProductDialog(
                onDismiss = {
                    viewModel.isAddProductDialogVisible.value = false
                },
                onConfirm = {
                    viewModel.addProduct(it)
                    viewModel.isAddProductDialogVisible.value = false
                }
            )
        }

        if (viewModel.isEditProductDialogVisible.value){
            EditProductDialog(
                onDismiss = {
                    viewModel.isEditProductDialogVisible.value = false
                },
                onConfirm = {
                    viewModel.updateProduct(it.id,it)
                    viewModel.isEditProductDialogVisible.value =false
                },
                onDelete = {
                    viewModel.deleteProduct()
                    viewModel.isEditProductDialogVisible.value =false
                },
                product = viewModel.selectedProduct.value!!
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
                            "Id",
                            modifier = Modifier.width(120.dp).padding(16.dp),
                            fontSize = 12.sp
                        )
                        Text(
                            "Product name",
                            modifier = Modifier.width(120.dp),
                            fontSize = 12.sp
                        )
                        Text(
                            "Model",
                            modifier = Modifier.width(120.dp),
                            fontSize = 12.sp
                        )
                        Text(
                            "Warranty date",
                            modifier = Modifier.width(120.dp),
                            fontSize = 12.sp
                        )

                        Text(
                            "Price",
                            modifier = Modifier.width(120.dp),
                            fontSize = 12.sp
                        )
                        Text(
                            "Manufacturer",
                            modifier = Modifier.width(120.dp),
                            fontSize = 12.sp
                        )
                        Text(
                            "Image",
                            modifier = Modifier.width(120.dp),
                            fontSize = 12.sp
                        )
                        Text(
                            "Length",
                            modifier = Modifier.width(120.dp),
                            fontSize = 12.sp
                        )
                        Text(
                            "Width",
                            modifier = Modifier.width(120.dp),
                            fontSize = 12.sp
                        )
                        Text(
                            "Height",
                            modifier = Modifier.width(120.dp).padding(16.dp),
                            fontSize = 12.sp
                        )
                    }
                }
            itemsIndexed(products.value){index,product->
                val backgrColor = if (index%2==0){
                    Color.LightGray
                }
                else{
                    Color.Transparent
                }
                ProductItem(
                    product,
                    backgrColor,
                    onSelect = {
                        if (viewModel.workMode==Mode.Admin){
                            viewModel.selectedProduct.value = product
                            viewModel.isEditProductDialogVisible.value = true
                        }
                    }
                )
            }
            }
        }
        if (viewModel.workMode==Mode.Admin){
            Button(
                onClick = {
                    viewModel.isAddProductDialogVisible.value = true
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
fun ProductItem(
    product: Product,
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
           product.id,
           modifier = Modifier
               .width(120.dp)
               .padding(horizontal = 16.dp),
           fontSize = 12.sp
       )
       Text(
           product.name,
           modifier = Modifier
               .width(120.dp),
           fontSize = 12.sp
       )
       Text(
           product.model,
           modifier = Modifier.width(120.dp),
           fontSize = 12.sp
       )
       Text(
           product.warrantyDate.toString(),
           modifier = Modifier.width(120.dp),
           fontSize = 12.sp
       )
       Text(
           "${product.price}円",
           modifier = Modifier.width(120.dp),
           fontSize = 12.sp
       )
       Text(
           product.manufacturer,
           modifier = Modifier.width(120.dp),
           fontSize = 12.sp
       )
       KamelImage(
           resource = asyncPainterResource(data = product.image),
           contentDescription = "description",
           modifier = Modifier
               .width(120.dp)
               .height(120.dp)
               .padding(vertical = 8.dp),
           contentScale = ContentScale.FillBounds,
           onLoading = {
               CircularProgressIndicator(modifier = Modifier.size(30.dp))
           }
       )
       Text(
           product.characteristic.length.toString(),
           Modifier.width(120.dp),
           fontSize = 12.sp
       )
       Text(
           product.characteristic.width.toString(),
           Modifier.width(120.dp),
           fontSize = 12.sp
       )
       Text(
           product.characteristic.height.toString(),
           Modifier.width(120.dp).padding(horizontal = 16.dp),
           fontSize = 12.sp
       )
    }
}