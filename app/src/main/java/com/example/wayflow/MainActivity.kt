package com.example.wayflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                WayflowApp()
            }
        }
    }
}

@Composable
fun WayflowApp() {
    var selectedTab by remember { mutableStateOf(0) }
    var isListening by remember { mutableStateOf(false) }
    var pitch by remember { mutableStateOf(0f) }
    var sopranoOn by remember { mutableStateOf(true) }
    var altoOn by remember { mutableStateOf(false) }
    var tenorOn by remember { mutableStateOf(false) }
    var bassOn by remember { mutableStateOf(true) }

    LaunchedEffect(isListening) {
        while(true) {
            if(isListening) {
                pitch = Random.nextInt(-40, 40).toFloat()
            }
            delay(150)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0A0A0A))) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(10.dp)).background(Color.White), contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(Color.Black))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Wayflow", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("ACAPELLACORE • OFFLINE", color = Color(0xFF8A5CF6), fontSize = 9.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Tabs - Simple buttons to avoid compile error
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {selectedTab=0}, colors = ButtonDefaults.buttonColors(containerColor = if(selectedTab==0) Color(0xFF8A5CF6) else Color(0xFF1A1A1A)), modifier = Modifier.weight(1f)) { Text("STUDIO") }
                Button(onClick = {selectedTab=1}, colors = ButtonDefaults.buttonColors(containerColor = if(selectedTab==1) Color(0xFF8A5CF6) else Color(0xFF1A1A1A)), modifier = Modifier.weight(1f)) { Text("CHOIR") }
                Button(onClick = {selectedTab=2}, colors = ButtonDefaults.buttonColors(containerColor = if(selectedTab==2) Color(0xFF8A5CF6) else Color(0xFF1A1A1A)), modifier = Modifier.weight(1f)) { Text("RIFFS") }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if(selectedTab==0) {
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF121212)), shape = RoundedCornerShape(24.dp)) {
                    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("PITCH DETECTION", color = Color(0xFF8A5CF6), fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(120.dp).clip(RoundedCornerShape(16.dp)).background(Color(0xFF1E1E1E)), contentAlignment = Alignment.Center) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val center = Offset(size.width/2, size.height*0.8f)
                                drawLine(Color.White.copy(alpha=0.2f), Offset(40f, center.y), Offset(size.width-40, center.y), strokeWidth = 2f)
                                val needleX = center.x + (pitch * 3f)
                                drawLine(Color(0xFF8A5CF6), center, Offset(needleX, 20f), strokeWidth = 6f)
                                drawCircle(Color(0xFF8A5CF6), radius = 10f, center = center)
                            }
                            Text(if(pitch<-15) "FLAT" else if(pitch>15) "SHARP" else "IN TUNE", color = if(pitch in -15f..15f) Color(0xFF22C55E) else Color(0xFFEF4444), fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.BottomCenter).padding(bottom=12.dp))
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            Text("A4\n440Hz", color = Color.White, fontWeight = FontWeight.Bold)
                            Text("LATENCY\n12ms", color = Color.White, fontWeight = FontWeight.Bold)
                            Text("MODE\nOFFLINE", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(onClick = { isListening = !isListening }, modifier = Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = if(isListening) Color(0xFFEF4444) else Color(0xFF8A5CF6)), shape = RoundedCornerShape(28.dp)) {
                            Text(if(isListening) "● STOP" else "🎤 START LISTENING", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            if(selectedTab==1) {
                Text("Choir Blend Mode", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                TrackRow("SOPRANO", sopranoOn, {sopranoOn=!sopranoOn}, Color(0xFF8A5CF6))
                TrackRow("ALTO", altoOn, {altoOn=!altoOn}, Color(0xFFEC4899))
                TrackRow("TENOR", tenorOn, {tenorOn=!tenorOn}, Color(0xFF22C55E))
                TrackRow("BASS", bassOn, {bassOn=!bassOn}, Color(0xFF3B82F6))
                Spacer(modifier = Modifier.height(12.dp))
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF8A5CF6).copy(alpha=0.15f)), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("🔒 Premium: 5 AI Voice Packs • 10 Songs", color = Color.White, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick={}, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A5CF6))) { Text("Unlock $5") }
                    }
                }
            }

            if(selectedTab==2) {
                Text("Riffs & Runs", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Card(modifier = Modifier.fillMaxWidth().height(180.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF121212))) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("🎵 Visual Pitch Curve - Follow The Line!", color = Color(0xFF8A5CF6))
                    }
                }
            }
        }
    }
}

@Composable
fun TrackRow(name:String, on:Boolean, toggle:()->Unit, color:Color) {
    Card(modifier = Modifier.fillMaxWidth().padding(bottom=8.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)), shape = RoundedCornerShape(16.dp)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(if(on) color else Color.Gray))
            Spacer(modifier = Modifier.width(12.dp))
            Text(name, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Switch(checked = on, onCheckedChange = { toggle() })
        }
    }
}
