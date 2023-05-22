package com.example.app

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foodCategories


val arrowIcon: ImageVector = Icons.Default.ArrowForward


@Composable
fun ProfileScreen() {

    // State to hold the URI of the profile picture
    val profilePictureUri = remember { mutableStateOf<Uri?>(null) }


    Column(
        // Set up the main column layout with padding and alignment
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            // Display the user's profile picture in a box with rounded corners and border
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, MaterialTheme.colorScheme.onSurface)
        ) {


            Image(
                // Load the profile picture image
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "profilpicture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            // Display the "Profile" text as a headline
            text = "Profile",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            // Display the "Your Food Preferences" text as a bold label
            text = "Deine Ernährungspräferenzen mit ausgelassenen Zuataten:",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val selectedPreferences = remember { mutableStateListOf<String>() }

        foodCategories.forEach { (category, preferences) ->
            // TODO: Add logic for each category here
        }

        LazyColumn(
            // Create a scrollable list of food categories
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 8.dp)
        ) {
            items(foodCategories) { (category, preferences) ->
                var expanded by remember { mutableStateOf(false) }

                Column {
                    Row(
                        // Display each category with an arrow icon indicating expansion/collapse
                        modifier = Modifier.clickable { expanded = !expanded },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val arrowIcon: ImageVector = Icons.Default.ArrowForward

                        Icon(
                            // Show the arrow icon, rotating it if expanded
                            imageVector = arrowIcon,
                            contentDescription = null,
                            modifier = Modifier.rotate(if (expanded) 90f else 0f)
                        )

                        Text(
                            // Display the category name as bold text
                            text = category,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp // Increase the font size as desired
                        )
                    }

                    if (expanded) {
                        Column(
                            modifier = Modifier.padding(start = 16.dp)
                        ) {
                            preferences.forEach { (preference, description) ->
                                SelectableItem(
                                    // Display each preference as a selectable item
                                    selected = selectedPreferences.contains(preference),
                                    title = preference,
                                    subtitle = description,
                                    onClick = {
                                        if (selectedPreferences.contains(preference)) {
                                            selectedPreferences.remove(preference)
                                        } else {
                                            selectedPreferences.add(preference)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            // Display the "Account Settings" text as a bold label
            text = "Account Settings",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // TODO: Add account settings options here

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            // Add a "Save" button with a click action
            onClick = { /* TODO: Add action for save button */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save")
        }
    }
}
