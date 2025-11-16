package com.tasnimulhasan.home.component

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri

@Composable
fun FindMosqueRow(context: Context = LocalContext.current) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(color = MaterialTheme.colorScheme.background, shape = RoundedCornerShape(15.dp))
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(15.dp))
            .clickable {
                val gmmIntentUri = "geo:0,0?q=Mosque".toUri()
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                    setPackage("com.google.android.apps.maps")
                }
                if (mapIntent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(mapIntent)
                } else {
                    // Fallback to browser
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/Mosque"))
                    if (browserIntent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(browserIntent)
                    } else {
                        Toast.makeText(context, "No app found to handle the request", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Default.Place,
            contentDescription = "Mosque Icon",
            tint = Color(0xFF4CAF50)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Find Nearest Mosque",
            modifier = Modifier.weight(1f),
            fontSize = 16.sp
        )
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Go to Maps"
        )
    }
}

@Preview
@Composable
fun PreviewFindMosqueRow() {
    FindMosqueRow()
}
