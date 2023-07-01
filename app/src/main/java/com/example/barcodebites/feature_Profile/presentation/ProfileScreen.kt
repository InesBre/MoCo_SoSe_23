package com.example.barcodebites.feature_Profile.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.barcodebites.core.inheriting.ViewModelFactory
import com.example.barcodebites.ui.theme.purple

@Composable
fun ProfileScreen(
    factory: ViewModelFactory,
    viewModel: ProfileViewModel = viewModel(factory = factory)
) {
    viewModel.getPreferencesList()
    LazyColumn {
        items(viewModel.state.value.PREFERENCES, key = {pref -> pref.first}) {
            Card(
                backgroundColor = purple,
                elevation = 10.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dp(50f))
            ) {
                val name = it.first
                val triple = it.second
                Row {
                    Column {
                        Text(text = triple.first)
                        triple.second?.let { desc -> Text(text = desc) }
                    }
                    Switch(
                        checked = triple.third.value,
                        onCheckedChange = { checked ->
                            triple.third.value = checked
                            viewModel.onEvent(ProfileEvents.Change(isDelete = !checked, prefName = name))
                        }
                    )
                }
            }

            Divider(Modifier.fillMaxWidth(), 1.dp, Color.DarkGray)
        }
    }
}