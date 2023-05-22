package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileScreen()
/*            AppTheme {
                var selected by remember { mutableStateOf(false) }
                var selected2 by remember { mutableStateOf(false) }


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(80.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SelectableItem(selected = selected, title = "hello", onClick = {
                        selected = !selected
                    }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    SelectableItem(selected = selected2,
                        title = "hello2",
                        subtitle = "etc",
                        onClick = { selected2 = !selected2 })


                }
            }*/
        }
    }
}

@Preview
@Composable
fun PreviewMainActivity() {
    ProfileScreen()
}
