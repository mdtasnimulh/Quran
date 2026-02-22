package com.example.calendar.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.theme.*
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.tasnimulhasan.designsystem.R as Res

// ─── Colors ────────────────────────────────────────────────────────────────
private val DarkGreenBg     = Color(0xFF1B4332)
private val MediumGreenBg   = Color(0xFF2D6A4F)
private val ArcGreen        = Color(0xFF74C69D)
private val CardWhite       = Color(0xFFF8F9FA)
private val DotActive       = Color(0xFFFFD166)
private val DotInactive     = Color(0xFFDEE2E6)
private val TimelineLineClr = Color(0xFFE9ECEF)
private val SubtextGray     = Color(0xFF888888)

// ─── Data ──────────────────────────────────────────────────────────────────
private data class PrayerRow(
    val name: String,
    val time: String,
    val iconRes: Int,
    val isActive: Boolean = false,
)

// ─── Main Composable ───────────────────────────────────────────────────────
@Composable
fun PrayerTimesCard(
    fajrTime: String,
    dhuhrTime: String,
    asrTime: String,
    maghribTime: String,
    ishaTime: String,
    sunriseTime: String,   // kept for future use
    sunsetTime: String,    // kept for future use
    locationName: String = "",
    currentEnDate: String = "",
) {
    val isDark = isSystemInDarkTheme()
    val fmt = DateTimeFormatter.ofPattern("hh:mm a", Locale.US)

    fun parse(t: String): LocalTime? = runCatching { LocalTime.parse(t, fmt) }.getOrNull()

    val fajrLT    = parse(fajrTime)
    val dhuhrLT   = parse(dhuhrTime)
    val asrLT     = parse(asrTime)
    val maghribLT = parse(maghribTime)
    val ishaLT    = parse(ishaTime)

    // Live clock — updates every second
    var now by remember { mutableStateOf(LocalTime.now()) }
    LaunchedEffect(Unit) {
        while (true) { delay(1000L); now = LocalTime.now() }
    }

    // Arc progress: 0f at Fajr → 1f at Isha
    val arcProgress = remember(now, fajrLT, ishaLT) {
        if (fajrLT != null && ishaLT != null) {
            val total   = (ishaLT.toSecondOfDay() - fajrLT.toSecondOfDay()).toFloat()
            val elapsed = (now.toSecondOfDay() - fajrLT.toSecondOfDay()).toFloat()
            (elapsed / total).coerceIn(0f, 1f)
        } else 0.4f
    }

    // Which prayer is currently active
    val activePrayerIndex = remember(now) {
        val times = listOf(fajrLT, dhuhrLT, asrLT, maghribLT, ishaLT)
        var idx = -1
        times.forEachIndexed { i, t -> if (t != null && !now.isBefore(t)) idx = i }
        idx
    }

    val prayers = listOf(
        PrayerRow(
            name     = "Fajr",
            time     = fajrTime,
            iconRes  = Res.drawable.img_calendar, // TODO: replace with ic_fajr_time
            isActive = activePrayerIndex == 0
        ),
        PrayerRow(
            name     = "Dhuhr",
            time     = dhuhrTime,
            iconRes  = Res.drawable.img_calendar, // TODO: replace with ic_dhuhr_time
            isActive = activePrayerIndex == 1
        ),
        PrayerRow(
            name     = "Asr",
            time     = asrTime,
            iconRes  = Res.drawable.img_calendar, // TODO: replace with ic_asr_time
            isActive = activePrayerIndex == 2
        ),
        PrayerRow(
            name     = "Maghrib",
            time     = maghribTime,
            iconRes  = Res.drawable.img_calendar, // TODO: replace with ic_maghrib_time
            isActive = activePrayerIndex == 3
        ),
        PrayerRow(
            name     = "Isha",
            time     = ishaTime,
            iconRes  = Res.drawable.img_calendar, // TODO: replace with ic_isha_time
            isActive = activePrayerIndex == 4
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(24.dp))
            .background(DarkGreenBg)
    ) {

        // ── HEADER: Arc + Clock ───────────────────────────────────────────
        Box(
            modifier         = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .background(DarkGreenBg),
            contentAlignment = Alignment.Center
        ) {
            ArcCanvas(arcProgress = arcProgress)

            // Text sits inside the dome area
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier            = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 28.dp)
            ) {
                if (locationName.isNotBlank()) {
                    Text(
                        text  = locationName,
                        style = TextStyle(
                            fontSize   = 12.sp,
                            color      = Color.White.copy(alpha = 0.85f),
                            fontFamily = RobotoFontFamily,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Spacer(Modifier.height(2.dp))
                }

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text  = now.format(DateTimeFormatter.ofPattern("hh:mm", Locale.US)),
                        style = TextStyle(
                            fontSize   = 52.sp,
                            color      = Color.White,
                            fontFamily = RobotoFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    // Superscript AM/PM — only the letter "M" styled small
                    Text(
                        text  = now.format(DateTimeFormatter.ofPattern("a", Locale.US)),
                        style = TextStyle(
                            fontSize   = 14.sp,
                            color      = Color.White.copy(alpha = 0.9f),
                            fontFamily = RobotoFontFamily,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.padding(start = 3.dp, bottom = 12.dp)
                    )
                }
            }
        }

        // ── FAJR / IFTAR ROW ─────────────────────────────────────────────
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .background(DarkGreenBg)
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            // Fajr — left: icon → label + time
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter            = painterResource(Res.drawable.img_calendar), // TODO: replace with ic_fajr or ic_sunrise
                    contentDescription = "Fajr",
                    tint               = Color(0xFFFFD166),
                    modifier           = Modifier.size(28.dp)
                )
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(
                        text  = "Fajr",
                        style = TextStyle(
                            fontSize   = 13.sp,
                            color      = Color.White,
                            fontFamily = RobotoFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text  = fajrTime,
                        style = TextStyle(
                            fontSize   = 12.sp,
                            color      = Color.White.copy(alpha = 0.85f),
                            fontFamily = RobotoFontFamily
                        )
                    )
                }
            }

            // Iftar — right: label + time → icon
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text  = "Iftar",
                        style = TextStyle(
                            fontSize   = 13.sp,
                            color      = Color.White,
                            fontFamily = RobotoFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text  = maghribTime,
                        style = TextStyle(
                            fontSize   = 12.sp,
                            color      = Color.White.copy(alpha = 0.85f),
                            fontFamily = RobotoFontFamily
                        )
                    )
                }
                Spacer(Modifier.width(8.dp))
                Icon(
                    painter            = painterResource(Res.drawable.img_calendar), // TODO: replace with ic_iftar or ic_sunset
                    contentDescription = "Iftar",
                    tint               = Color(0xFFFFD166),
                    modifier           = Modifier.size(28.dp)
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        // ── PRAYER LIST ───────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(if (isDark) Color(0xFF121212) else CardWhite)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // ── Timeline column (dots + connecting lines) ─────────────
                Column(
                    modifier            = Modifier
                        .width(52.dp)
                        .padding(top = 12.dp, bottom = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    prayers.forEachIndexed { index, prayer ->
                        // Dot — always rendered unconditionally
                        Box(
                            modifier = Modifier
                                .size(if (prayer.isActive) 14.dp else 10.dp)
                                .background(
                                    color = if (prayer.isActive) DotActive else DotInactive,
                                    shape = CircleShape
                                )
                        )

                        // Connecting line — only between items, never after last
                        if (index < prayers.lastIndex) {
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(58.dp) // fixed height so last dot is never clipped
                                    .background(TimelineLineClr)
                            )
                        } else {
                            // Small breathing room below last dot
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                }

                // ── Prayer rows ───────────────────────────────────────────
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp, top = 12.dp, bottom = 12.dp)
                ) {
                    prayers.forEachIndexed { index, prayer ->
                        PrayerListRow(prayer = prayer, isDark = isDark)
                        if (index < prayers.lastIndex) {
                            HorizontalDivider(
                                color     = TimelineLineClr,
                                thickness = 0.8.dp,
                                modifier  = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// ─── Arc Canvas (stroke only — no filled dome) ─────────────────────────────
@Composable
private fun ArcCanvas(arcProgress: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val strokeWidth  = 18.dp.toPx()
        val pad          = 24.dp.toPx()
        val diameter     = minOf(size.width, size.height * 2f) - pad * 2
        val arcSize      = Size(diameter, diameter)
        val topLeft      = Offset((size.width - diameter) / 2f, size.height - diameter / 2f)
        val centerX      = size.width / 2f
        val centerY      = topLeft.y + diameter / 2f
        val radius       = diameter / 2f
        val totalSweep   = 180f

        // ── 1. Dark background track ──────────────────────────────────────
        drawArc(
            color      = Color(0xFF2D6A4F),
            startAngle = 180f,
            sweepAngle = totalSweep,
            useCenter  = false,
            topLeft    = topLeft,
            size       = arcSize,
            style      = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        // ── 2. Green progress stroke ──────────────────────────────────────
        val greenEnd   = 0.88f
        val greenSweep = totalSweep * minOf(arcProgress, greenEnd)

        if (greenSweep > 0f) {
            drawArc(
                color      = Color(0xFF95D5B2),
                startAngle = 180f,
                sweepAngle = greenSweep,
                useCenter  = false,
                topLeft    = topLeft,
                size       = arcSize,
                style      = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        // ── 3. Yellow tip ─────────────────────────────────────────────────
        if (arcProgress > greenEnd) {
            // Yellow arc segment at the very end
            val yellowStart = 180f + greenSweep
            val yellowSweep = (totalSweep * arcProgress) - greenSweep
            drawArc(
                color      = Color(0xFFFFD166),
                startAngle = yellowStart - 2f,
                sweepAngle = yellowSweep + 4f,
                useCenter  = false,
                topLeft    = topLeft,
                size       = arcSize,
                style      = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        } else {
            // Yellow dot at the current head of the progress arc
            val tipAngle = Math.toRadians((180.0 + totalSweep * arcProgress))
            val tipX     = centerX + radius * Math.cos(tipAngle).toFloat()
            val tipY     = centerY + radius * Math.sin(tipAngle).toFloat()
            drawCircle(
                color  = Color(0xFFFFD166),
                radius = strokeWidth / 2f + 2.dp.toPx(),
                center = Offset(tipX, tipY)
            )
        }

        // ── 4. Small notch bump at 6 o'clock (bottom centre of arc) ──────
        drawArc(
            color      = Color(0xFF1B4332),
            startAngle = 155f,
            sweepAngle = 50f,
            useCenter  = false,
            topLeft    = Offset(centerX - 30.dp.toPx(), centerY - 14.dp.toPx()),
            size       = Size(60.dp.toPx(), 60.dp.toPx()),
            style      = Stroke(width = 16.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

// ─── Single Prayer Row ──────────────────────────────────────────────────────
@Composable
private fun PrayerListRow(prayer: PrayerRow, isDark: Boolean) {
    Row(
        modifier          = Modifier
            .fillMaxWidth()
            .height(68.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Prayer icon in rounded box
        Box(
            modifier         = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(if (isDark) Color(0xFF1E1E1E) else Color(0xFFF0F0F0)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter            = painterResource(prayer.iconRes),
                contentDescription = prayer.name,
                tint               = Color.Unspecified,
                modifier           = Modifier.size(28.dp)
            )
        }

        Spacer(Modifier.width(8.dp))

        // Prayer name + time
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text  = prayer.name,
                style = TextStyle(
                    fontSize   = 15.sp,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color      = if (isDark) MintWhite else Color(0xFF1B1B1B)
                )
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text  = prayer.time,
                style = TextStyle(
                    fontSize   = 12.sp,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    color      = if (isDark) MintWhite.copy(alpha = 0.5f) else SubtextGray
                )
            )
        }

        // Notification icon — TODO: will implement alarm logic on click
        Icon(
            imageVector        = Icons.AutoMirrored.Filled.VolumeUp,
            contentDescription = "Volume",
            tint               = if (prayer.isActive) BottleGreen else SubtextGray,
            modifier           = Modifier.size(22.dp)
        )
    }
}

// ─── Preview ────────────────────────────────────────────────────────────────
@Preview(showBackground = true)
@Composable
fun PreviewPrayerTimesCard() {
    PrayerTimesCard(
        fajrTime      = "04:38 AM",
        dhuhrTime     = "12:25 PM",
        asrTime       = "03:35 PM",
        maghribTime   = "06:20 PM",
        ishaTime      = "07:32 PM",
        sunriseTime   = "05:51 AM",
        sunsetTime    = "06:20 PM",
        locationName  = "Banani, Dhaka, BD",
        currentEnDate = "Wednesday, 18 Feb 2026"
    )
}