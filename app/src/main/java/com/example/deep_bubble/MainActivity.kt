package com.example.deep_bubble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.deep_bubble.ui.theme.Deep_bubbleTheme
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.core.content.ContextCompat
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.deep_bubble.ui.theme.Deep_bubbleTheme

class MainActivity : ComponentActivity() {
    companion object{
        var isVisible = false
        var isBubbleRunning = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()




        setContent {
            Deep_bubbleTheme {
                val context = LocalContext.current
                var bubbleRunning by rememberSaveable { mutableStateOf(isBubbleRunning) }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            if (!Settings.canDrawOverlays(context)) {
                                val intent = Intent(
                                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                    Uri.parse("package:$packageName")
                                )
                                startActivity(intent)
                                return@Button
                            }
                            if (bubbleRunning) {
                                stopMyService()
                            } else {
                                val startIntent = Intent(context, MyFloatingBubbleService::class.java).apply {
                                    putExtra("size", 60)
                                    putExtra("noti_message", "HELLO FROM MAIN ACT")
                                }
                                ContextCompat.startForegroundService(context, startIntent)
                            }
                            bubbleRunning = !bubbleRunning
                            isBubbleRunning = bubbleRunning
                        }
                    ) {
                        Text(if (bubbleRunning) "Stop Bubble" else "Start Bubble")
                    }
                }
            }

        }

    }
    private fun stopMyService() {
        val intent = Intent(this, MyFloatingBubbleService::class.java)
        stopService(intent)
        Toast.makeText(this, "stop service", Toast.LENGTH_SHORT).show()
    }
}


