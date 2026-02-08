package com.tasnimulhasan.tasbih.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.QuranTheme

@Composable
fun SelectDhikrDialog(
    dhikrList: List<String>,
    selectedDhikr: String,
    goal: String,
    onDhikrSelected: (String) -> Unit,
    onGoalChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFE6F1EA),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(20.dp)
        ) {

            Column {

                /* Close Button */
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { onDismiss() }
                )

                Spacer(modifier = Modifier.height(12.dp))

                /* Select Dhikr */
                DialogLabel("SELECT YOUR DHIKR")
                DhikrDropdown(
                    items = dhikrList,
                    selected = selectedDhikr,
                    onItemSelected = onDhikrSelected
                )

                Spacer(modifier = Modifier.height(20.dp))

                /* Select Goal */
                DialogLabel("SELECT YOUR GOAL")
                GoalInput(
                    value = goal,
                    onValueChange = onGoalChange
                )

                Spacer(modifier = Modifier.height(28.dp))

                /* OK Button */
                Box(
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(50.dp))
                        .clickable { onConfirm() }
                        .padding(horizontal = 36.dp, vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "OK",
                        fontWeight = FontWeight.Bold,
                        color = BottleGreen
                    )
                }
            }
        }
    }
}

@Composable
private fun DialogLabel(text: String) {
    Text(
        text = text,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.Gray,
        modifier = Modifier.padding(start = 6.dp, bottom = 6.dp)
    )
}

@Composable
private fun DhikrDropdown(
    items: List<String>,
    selected: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(50.dp))
                .clickable { expanded = true }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selected,
                modifier = Modifier.weight(1f),
                fontSize = 12.sp
            )
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        onItemSelected(it)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun GoalInput(
    value: String,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(50.dp))
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        TextField(
            value = value,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    onValueChange(it)
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSelectDhikrDialog() {
    QuranTheme {
        SelectDhikrDialog(
            dhikrList = listOf("Alhamdulillah", "Subhanallah", "Allahu Akbar"),
            selectedDhikr = "Alhamdulillah",
            goal = "99",
            onDhikrSelected = {},
            onGoalChange = {},
            onDismiss = {},
            onConfirm = {}
        )
    }
}