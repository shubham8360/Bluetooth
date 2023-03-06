package com.project.bluetooth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.project.bluetooth.ui.theme.BluetoothTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BluetoothTheme {
                // A surface container using the 'background' color from the theme
                DefaultPreview()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello $name!",
        color = Color.Blue,
        fontFamily = FontFamily.SansSerif,
        fontSize = 20.sp,
        letterSpacing = 2.sp,
        fontWeight = FontWeight.SemiBold,

        )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    LazyColumn(Modifier.fillMaxSize()) {
        items((1..10).toList()) {
            Greeting(name = "Android $it")
        }
    }

}