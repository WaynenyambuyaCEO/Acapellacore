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
import kotlin.math.sin
import kotlinx.coroutines.delay

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
    var pitch by remember { mutableStateOf(0f) } // -50 to 50 flat/sharp
    var sopranoOn by remember { mutableStateOf(true) }
    var altoOn by remember { mutableStateOf(false) }
    var tenorOn by remember { mutableStateOf(false) }
    var bassOn by remember { mutableStateOf(true) }

    // Simulate pitch needle movement when listening
    LaunchedEffect(isListening) {
        while(isListening) {
            pitch = (-40..40).random().toFloat()
            delay(150)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0A0A0A))) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(10.dp)).background(Color.White), contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(Color.Black))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text("Wayflow", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(" ACAPELLACORE", color = Color(0xFF8A5CF6), fontSize = 10.sp, modifier = Modifier.padding(start = 8.dp))
                Spacer(modifier = Modifier.weight(1f))
                Badge { Text("OFFLINE") }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Tabs
            TabRow(selectedTabIndex = selectedTab, containerColor = Color(0xFF1A1A1A), contentColor = Color.White) {
                Tab(selected = selectedTab==0, onClick = {selectedTab=0}, text = {Text("STUDIO")})
                Tab(selected = selectedTab==1, onClick = {selectedTab=1}, text = {Text("CHOIR")})
                Tab(selected = selectedTab==2, onClick = {selectedTab=2}, text = {Text("RIFFS")})
            }

            Spacer(modifier = Modifier.height(24.dp))

            if(selectedTab==0) {
                // STUDIO TAB
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF121212)), shape = RoundedCornerShape(24.dp)) {
                    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("PITCH DETECTION", color = Color(0xFF8A5CF6), fontSize = 11.sp, letterSpacing = 2.sp)
                        Spacer(modifier = Modifier.height(16.dp))

                        // Needle
                        Box(modifier = Modifier.fillMaxWidth().height(120.dp).clip(RoundedCornerShape(16.dp)).background(Color(0xFF1E1E1E)), contentAlignment = Alignment.Center) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val center = Offset(size.width/2, size.height*0.8f)
                                // scale line
                                drawLine(Color.White.copy(alpha=0.2f), Offset(40f, center.y), Offset(size.width-40, center.y), strokeWidth = 2f)
                                // needle
                                val needleX = center.x + (pitch * 3f)
                                drawLine(Color(0xFF8A5CF6), center, Offset(needleX, 20f), strokeWidth = 6f)
                                drawCircle(Color(0xFF8A5CF6), radius = 10f, center = center)
                            }
                            Text(if(pitch<-15) "FLAT" else if(pitch>15) "SHARP" else "IN TUNE", color = if(pitch in -15..15) Color(0xFF22C55E) else Color(0xFFEF4444), fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.BottomCenter).padding(bottom=12.dp))
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("NOTE", color = Color.White.copy(0.4f), fontSize = 10.sp)
                                Text("A4", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("FREQ", color = Color.White.copy(0.4f), fontSize = 10.sp)
                                Text("440Hz", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("LATENCY", color = Color.White.copy(0.4f), fontSize = 10.sp)
                                Text("12ms", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        Button(onClick = { isListening =!isListening }, modifier = Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = if(isListening) Color(0xFFEF4444) else Color(0xFF8A5CF6)), shape = RoundedCornerShape(28.dp)) {
                            Text(if(isListening) "● STOP LISTENING" else "🎤 START LISTENING", fontWeight = FontWeight.Bold)
                        }
                        Text("100% Offline • Web Audio API • No cloud", color = Color.White.copy(0.3f), fontSize = 10.sp, modifier = Modifier.padding(top=8.dp))
                    }
                }
            }

            if(selectedTab==1) {
                // CHOIR TAB
                Text("Choir Blend Mode", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("4 tracks • Mute/Solo • 5 AI Packs", color = Color.White.copy(0.4f), fontSize = 12.sp)
                Spacer(modifier = Modifier.height(16.dp))
                ChoirTrack("SOPRANO", sopranoOn, {sopranoOn=!sopranoOn}, Color(0xFF8A5CF6))
                ChoirTrack("ALTO", altoOn, {altoOn=!altoOn}, Color(0xFFEC4899))
                ChoirTrack("TENOR", tenorOn, {tenorOn=!tenorOn}, Color(0xFF22C55E))
                ChoirTrack("BASS", bassOn, {bassOn=!bassOn}, Color(0xFF3B82F6))
                Spacer(modifier = Modifier.height(16.dp))
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF8A5CF6).copy(0.15f)), shape = RoundedCornerShape(16.dp)) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("🔒 Premium: 5 AI Voice Packs • 10 Songs", color = Color.White, fontSize = 13.sp, modifier = Modifier.weight(1f))
                        Button(onClick={}, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A5CF6)), shape = RoundedCornerShape(20.dp)) { Text("Unlock $5") }
                    }
                }
            }

            if(selectedTab==2) {
                // RIFFS TAB
                Text("Riffs & Runs Engine", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Follow the pitch curve like Guitar Hero", color = Color.White.copy(0.4f), fontSize = 12.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Card(modifier = Modifier.fillMaxWidth().height(200.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF121212)), shape = RoundedCornerShape(24.dp)) {
                    Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        val path = mutableListOf<Offset>()
                        for(i in 0..size.width.toInt() step 10) {
                            val y = size.height/2 + sin(i/50f + System.currentTimeMillis()/500f)*60f
                            path.add(Offset(i.toFloat(), y))
                        }
                        for(i in 0 until path.size-1) {
                            drawLine(Color(0xFF8A5CF6), path[i], path[i+1], strokeWidth = 4f)
                        }
                        drawCircle(Color.White, radius = 8f, center = path[path.size/2])
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick={}, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)) { Text("▶ START RIFF CHALLENGE") }
            }
        }
    }
}

@Composable
fun ChoirTrack(name:String, on:Boolean, toggle:()->Unit, color:Color) {
    Card(modifier = Modifier.fillMaxWidth().padding(bottom=10.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)), shape = RoundedCornerShape(16.dp)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(if(on) color else Color.Gray))
            Spacer(modifier = Modifier.width(12.dp))
            Text(name, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text(if(on) "ON" else "MUTE", color = Color.White.copy(0.5f), fontSize = 11.sp, modifier = Modifier.padding(end=12.dp))
            Switch(checked = on, onCheckedChange = {toggle()})
        }
    }
}
