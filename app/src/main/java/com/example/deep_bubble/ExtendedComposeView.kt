package com.example.deep_bubble

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtendedComposeView(
    modifier: Modifier = Modifier,
    minimize: () -> Unit = {}
) {

    val items = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        val temp = mutableListOf<String>()
        for (i in 0..10) {
            temp.add(i.toString())
        }
        items.addAll(temp)
    }


        Column(
            modifier
                .fillMaxWidth()
                .height(500.dp)
                .background(Color.LightGray),
        ) {
            Button(onClick = {
                Log.d("TestComposeView", "Minimized")
                runBlocking {
                    minimize()
                }
            })
            {
                Text(text = "Minimize")
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                itemsIndexed(items) { index, item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = item.toString(), fontSize = 18.sp)
                    }
                }
            }
        }


}