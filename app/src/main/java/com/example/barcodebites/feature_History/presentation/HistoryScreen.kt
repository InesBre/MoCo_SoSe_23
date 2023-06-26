package com.example.barcodebites.feature_History.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.barcodebites.core.data.entities.Product
import com.example.barcodebites.core.inheriting.ViewModelFactory
import com.example.barcodebites.ui.theme.purple
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryScreen(
    factory: ViewModelFactory,
    viewModel: HistoryViewModel = viewModel(factory = factory)
) {
    viewModel.onEvent(HistoryEvents.Init())
    if (viewModel.state.value.LIST.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Noch keine Produkte")
        }
    } else {
        LazyColumn {
            items(viewModel.state.value.LIST, key = {product -> product.productId})
            {product ->
                val dismissState = rememberDismissState()
                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    viewModel.onEvent(HistoryEvents.Remove(product.productId))
                }
                SwipeToDismiss(
                    state = dismissState,
                    modifier = Modifier.padding(vertical = Dp(1f)),
                    directions = setOf(
                        DismissDirection.EndToStart
                    ),
                    dismissThresholds = { direction ->
                        FractionalThreshold(if (direction == DismissDirection.EndToStart) 0.1f else 0.05f)
                    },
                    background = {
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> Color.White
                                else -> Color.Red
                            }
                        )
                        val alignment = Alignment.CenterEnd
                        val icon = Icons.Default.Delete

                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                        )
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = Dp(20f)),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                icon,
                                contentDescription = "Delete Icon",
                                modifier = Modifier.scale(scale)
                            )
                        }
                    },
                    dismissContent = {
                        Card(
                            backgroundColor = purple,
                            elevation = 10.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Dp(50f))
                                .align(alignment = Alignment.CenterVertically)
                        ) {
                            SetUpRow(product, viewModel)
                        }
                    }
                )

                Divider(Modifier.fillMaxWidth(), 1.dp, Color.DarkGray)
            }
        }
    }
}

@Composable
fun SetUpRow(product: Product, viewModel: HistoryViewModel) {
    val open = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(true, "CLICK!", Role.Button) {
                open.value = true
            }
            .fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            color = Color.Black,
            text = product.productName, modifier = Modifier.wrapContentSize(),
            fontSize = TextUnit(value = 16f, type = TextUnitType.Sp)
        )
    }
    if (open.value) {
        Dialog(product, viewModel) { open.value = false }
    }
}

@Composable
fun Dialog(product: Product, viewModel: HistoryViewModel, onClose: () -> Unit) {
    val likedIcon = remember { mutableStateOf(Icons.Default.FavoriteBorder) }
    AlertDialog(
        onDismissRequest = onClose,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = product.productName)
            }
        },
        text = { FormatDetails(product) },
        confirmButton = {},
        dismissButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    product.isLiked = !product.isLiked
                    if (product.isLiked) likedIcon.value = Icons.Default.FavoriteBorder
                    else likedIcon.value = Icons.Default.Favorite
                    viewModel.onEvent(HistoryEvents.Update(product))
                }) {
                    Icon(
                        imageVector = likedIcon.value,
                        contentDescription = "Your stance on the product"
                    )
                }
                Button(onClick = onClose) {
                    Text("Schliessen")
                }
            }
        }
    )
}

@Composable
fun FormatDetails(product: Product) {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    val date = LocalDateTime.parse(product.lastScanned).format(formatter)
    Column {
        Row {
            Text(text = "Firma", modifier = Modifier.weight(.5f))
            Text(text = product.brand, modifier = Modifier.weight(.5f))
        }
        Row {
            Text(text = "Kannste Essen", modifier = Modifier.weight(.5f))
            Text(
                text = when (product.isEdible) {
                    true -> "ISS JUNG!"
                    else -> "FINGER WEG!"
                }, modifier = Modifier.weight(.5f)
            )
        }
        Row {
            Text(text = "Zuletzt gescannt am", modifier = Modifier.weight(.5f))
            Text(text = date, modifier = Modifier.weight(.5f))
        }
    }
}