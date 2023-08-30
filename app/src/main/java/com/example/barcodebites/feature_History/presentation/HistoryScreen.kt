package com.example.barcodebites.feature_History.presentation

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
    val filter = listOf(
        Filter("search", "Suche Name / Firma", remember { mutableStateOf("") }),
        Filter("isLiked", "Nur gelikete Gerichte", remember { mutableStateOf(false) }),
        Filter("isEdible", "Nur den Präferenzen entsprechende Gerichte", remember { mutableStateOf(false) })
    )
    val expanded = remember { mutableStateOf(false) }
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                placeholder = { Text(text = filter[0].label) },
                value = filter[0].data.value as String,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White
                ),
                onValueChange = {
                    filter[0].data.value = it
                    viewModel.onEvent(HistoryEvents.Filter(filter))
                }
            )
            Box {
                Button(
                    onClick = { expanded.value = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Your stance on the product",
                        tint = Color.White
                    )
                }
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    filter.drop(1).forEach {
                        DropdownMenuItem(
                            text = {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Switch(
                                        checked = it.data.value as Boolean,
                                        onCheckedChange = { checked ->
                                            it.data.value = checked
                                            viewModel.onEvent(HistoryEvents.Filter(filter))
                                        }
                                    )
                                    Text(text = it.label)
                                }
                            },
                            onClick = {
                                expanded.value = false
                            }
                        )
                    }
                }
            }
        }
        if (viewModel.state.value.LIST.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Keine Produkte gefunden")
            }
        } else {
            LazyColumn {
                items(
                    viewModel.state.value.LIST,
                    key = { pair -> viewModel.getProductId(pair.first) })
                { pair ->
                    val dismissState = rememberDismissState()
                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        viewModel.onEvent(HistoryEvents.Remove(viewModel.getProductId(pair.first)))
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
                                SetUpRow(pair, viewModel)
                            }
                        }
                    )

                    Divider(Modifier.fillMaxWidth(), 1.dp, Color.DarkGray)
                }
            }
        }
    }
}

@Composable
fun SetUpRow(pair: Pair<Product, Boolean>, viewModel: HistoryViewModel) {
    val open = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(true, "CLICK!", Role.Button) {
                open.value = true
            }
            .fillMaxHeight()
            .padding(paddingValues = PaddingValues(start = 10.dp, end = 50.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            color = Color.Black,
            text = pair.first.productName, modifier = Modifier.wrapContentSize(),
            fontSize = TextUnit(value = 16f, type = TextUnitType.Sp)
        )
        Icon(
            imageVector = if (pair.second) Icons.Default.Warning else Icons.Default.ThumbUp,
            contentDescription = "State"
        )
    }
    if (open.value) {
        Dialog(pair, viewModel) { open.value = false }
    }
}

@Composable
fun Dialog(pair: Pair<Product, Boolean>, viewModel: HistoryViewModel, onClose: () -> Unit) {
    val product = pair.first
    val likedIcon = remember { mutableStateOf(getIcon(product.isLiked)) }
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
        text = { FormatDetails(pair) },
        confirmButton = {},
        dismissButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    product.isLiked = !product.isLiked
                    likedIcon.value = getIcon(product.isLiked)
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
fun FormatDetails(pair: Pair<Product, Boolean>) {
    val product = pair.first
    val isNotEdible = pair.second
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    val date = LocalDateTime.parse(product.lastScanned).format(formatter)
    Column {
        Row {
            Text(text = "Firma", modifier = Modifier.weight(.5f))
            Text(text = product.brand, modifier = Modifier.weight(.5f))
        }
        Row {
            Text(text = "Den Präferenzen entsprechend", modifier = Modifier.weight(.5f))
            Text(
                text = when (isNotEdible) {
                    true -> "Nein"
                    else -> "Ja"
                }, modifier = Modifier.weight(.5f)
            )
        }
        Row {
            Text(text = "Zuletzt gescannt am", modifier = Modifier.weight(.5f))
            Text(text = date, modifier = Modifier.weight(.5f))
        }
    }
}

fun getIcon(isLiked: Boolean): ImageVector {
    return when (isLiked) {
        true -> Icons.Default.Favorite
        else -> Icons.Default.FavoriteBorder
    }
}

data class Filter(
    val id: String,
    val label: String,
    var data: MutableState<Any>
)
