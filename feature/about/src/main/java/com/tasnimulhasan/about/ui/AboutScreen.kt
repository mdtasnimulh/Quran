package com.tasnimulhasan.about.ui

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily

@Composable
fun AboutScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = "About This App",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        HorizontalDivider()

        Row(verticalAlignment = Alignment.Top) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "This Quran app is a sadaqa from me to everyone. " +
                        "The use of this app is completely free for all users. " +
                        "reading and understanding the Holy Quran easier for all users.\n" +
                        "My intention is to make the Quran easily accessible and beneficial for everyone.\n\n" +
                        "Any suggestions, feedback, or recommendations are highly appreciated.",
                fontSize = 16.sp,
                fontFamily = RobotoFontFamily,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        HorizontalDivider()

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Column {
                Text(
                    text = "Developer & Maintainer",
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = RobotoFontFamily,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = "Mohammad Tasnimul Hasan",
                    fontSize = 16.sp,
                    fontFamily = RobotoFontFamily,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }

        HorizontalDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = "mailto:mdtasnimulh@gmail.com".toUri()
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("mdtasnimulh@gmail.com"))
                        putExtra(Intent.EXTRA_SUBJECT, "Quran App Feedback")
                    }
                    context.startActivity(intent)
                }
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Column {
                Text(
                    text = "Contact Developer",
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "mdtasnimulh@gmail.com",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "May Allah accept this effort as Sadaqa Jariyah.\n© 2026",
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}