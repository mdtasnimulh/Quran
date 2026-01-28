package com.tasnimulhasan.settings.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tasnimulhasan.designsystem.theme.BlueMedium
import com.tasnimulhasan.designsystem.theme.CardNightBackground
import com.tasnimulhasan.designsystem.theme.CreamRed
import com.tasnimulhasan.designsystem.theme.MythicGreen
import com.tasnimulhasan.designsystem.theme.violet80
import com.tasnimulhasan.entity.enum.ThemeColor

@Composable
fun ColorThemeSection(
    modifier: Modifier = Modifier,
    context: Context,
    themeColor: ThemeColor,
    useDynamicColor: Boolean,
    changeThemeColor: (ThemeColor) -> Unit
) = Column(modifier = modifier.fillMaxWidth()) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(color = Color.White)
                .border(
                    width = 0.2.dp,
                    color = CardNightBackground,
                    shape = RoundedCornerShape(5.dp)
                )
                .clickable {
                    if (useDynamicColor) {
                        Toast.makeText(context, "Please disable dynamic color first!", Toast.LENGTH_SHORT).show()
                    } else changeThemeColor(ThemeColor.Default)
                },
            contentAlignment = Alignment.Center
        ) {
            if (themeColor == ThemeColor.Default && !useDynamicColor) {
                Icon(
                    modifier = Modifier.size(size = 24.dp),
                    imageVector = Icons.Default.Check,
                    tint = CardNightBackground,
                    contentDescription = null
                )
            }
        }

        Box(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(color = BlueMedium)
                .clickable {
                    if (useDynamicColor) {
                        Toast.makeText(context, "Please disable dynamic color first!", Toast.LENGTH_SHORT).show()
                    } else changeThemeColor(ThemeColor.BlueMedium)
                },
            contentAlignment = Alignment.Center
        ) {
            if (themeColor == ThemeColor.BlueMedium && !useDynamicColor) {
                Icon(
                    modifier = Modifier.size(size = 24.dp),
                    imageVector = Icons.Default.Check,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        }

        Box(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(color = CreamRed)
                .clickable {
                    if (useDynamicColor) {
                        Toast.makeText(context, "Please disable dynamic color first!", Toast.LENGTH_SHORT).show()
                    } else changeThemeColor(ThemeColor.CreamRed)
                },
            contentAlignment = Alignment.Center
        ) {
            if (themeColor == ThemeColor.CreamRed && !useDynamicColor) {
                Icon(
                    modifier = Modifier.size(size = 24.dp),
                    imageVector = Icons.Default.Check,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        }

        Box(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(color = MythicGreen)
                .clickable {
                    if (useDynamicColor) {
                        Toast.makeText(context, "Please disable dynamic color first!", Toast.LENGTH_SHORT).show()
                    } else changeThemeColor(ThemeColor.MythicGreen)
                },
            contentAlignment = Alignment.Center
        ) {
            if (themeColor == ThemeColor.MythicGreen && !useDynamicColor) {
                Icon(
                    modifier = Modifier.size(size = 24.dp),
                    imageVector = Icons.Default.Check,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        }

        Box(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(color = violet80)
                .clickable {
                    if (useDynamicColor) {
                        Toast.makeText(context, "Please disable dynamic color first!", Toast.LENGTH_SHORT).show()
                    } else changeThemeColor(ThemeColor.Violet)
                },
            contentAlignment = Alignment.Center
        ) {
            if (themeColor == ThemeColor.Violet && !useDynamicColor) {
                Icon(
                    modifier = Modifier.size(size = 24.dp),
                    imageVector = Icons.Default.Check,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        }
    }
}