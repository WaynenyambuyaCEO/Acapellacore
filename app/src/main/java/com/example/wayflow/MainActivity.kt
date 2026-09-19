package com.example.wayflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WayflowApp()
        }
    }
}

@Composable
fun WayflowApp() {
    var tab by remember { mutableStateOf(0) }
    var listening by remember { mutableStateOf(false) }
    
    MaterialTheme(colorScheme = darkColorScheme()) {
        Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0A0A0A)).padding(16.dp)) {
            Column {
                Text("Wayflow • AcapellaCore", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Vocal Training 100% Offline", color = Color(0xFF8A5CF6), fontSize = 12.sp)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row {
                    Button(onClick = {tab=0}, colors = ButtonDefaults.buttonColors(containerColor = if(tab==0) Color(0xFF8A5CF6) else Color.DarkGray)) { Text("STUDIO") }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {tab=1}, colors = ButtonDefaults.buttonColors(containerColor = if(tab==1) Color(0xFF8A5CF6) else Color.DarkGray)) { Text("CHOIR") }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {tab=2}, colors = ButtonDefaults.buttonColors(containerColor = if(tab==2) Color(0xFF8A5CF6) else Color.DarkGray)) { Text("RIFFS") }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                if(tab==0) {
                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)), shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("PITCH: A4 440Hz", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(if(listening) "● LISTENING... IN TUNE" else "Tap to start", color = if(listening) Color(0xFF22C55E) else Color.Gray, fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(20.dp))
                            Button(onClick = {listening = !listening}, modifier = Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = if(listening) Color.Red else Color(0xFF8A5CF6)), shape = RoundedCornerShape(28.dp)) {
                                Text(if(listening) "STOP" else "🎤 START LISTENING", fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("<20ms latency • Offline • No cloud", color = Color.Gray, fontSize = 10.sp)
                        }
                    }
                }
                
                if(tab==1) {
                    Text("Choir Blend Mode - 4 Tracks", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    TrackCard("SOPRANO - ON", true)
                    TrackCard("ALTO - MUTE", false)
                    TrackCard("TENOR - MUTE", false)
                    TrackCard("BASS - ON", true)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick={}, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A5CF6))) { Text("Unlock 5 AI Packs - $5") }
                }
                
                if(tab==2) {
                    Text("Riffs & Runs Engine", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)), modifier = Modifier.fillMaxWidth().height(150.dp)) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("🎵 Pitch Curve Challenge\nFollow the line!", color = Color(0xFF8A5CF6))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TrackCard(name: String, active: Boolean) {
    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)), modifier = Modifier.fillMaxWidth().padding(bottom=8.dp)) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(name, color = if(active) Color.White else Color.Gray, modifier = Modifier.weight(1f))
            Text(if(active) "●" else "○", color = if(active) Color(0xFF22C55E) else Color.Gray)
        }
    }
}
