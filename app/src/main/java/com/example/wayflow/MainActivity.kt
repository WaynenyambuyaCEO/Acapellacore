package com.example.wayflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                var screen by remember { mutableStateOf("home") }
                when(screen) {
                    "home" -> HomeScreen { screen = it }
                    "work" -> WorkScreen { screen = "home" }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onNav: (String)->Unit) {
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Wayflow", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Text("CEO Mode Activated")
        Spacer(Modifier.height(24.dp))
        Button(onClick = { onNav("work") }, modifier = Modifier.fillMaxWidth()) { Text("Enter Work Zone") }
    }
}

@Composable
fun WorkScreen(onBack: ()->Unit) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text("Work Zone", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Back to Home") }
    }
}
