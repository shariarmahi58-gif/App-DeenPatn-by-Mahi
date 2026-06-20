package com.example.ui.screens

import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.*
import com.example.ui.IslamicViewModel
import kotlin.math.abs

// Localized Dictionary for UI strings
object Loc {
    fun t(key: String, isBn: Boolean): String {
        return if (isBn) {
            when (key) {
                "dashboard" -> "ড্যাশবোর্ড"
                "quran" -> "আল-কুরআন"
                "hadith" -> "হাদীস"
                "dua" -> "দু’আ"
                "qibla" -> "কিবলা"
                "settings" -> "সেটিংস"
                "fajr" -> "ফজর"
                "sunrise" -> "সূর্যোদয়"
                "dhuhr" -> "যোহর"
                "asr" -> "আসর"
                "maghrib" -> "মাগরিব"
                "isha" -> "এশা"
                "district" -> "জেলা"
                "notifications" -> "নামাযের এলার্ট"
                "search_surah" -> "সূরা খুঁজুন..."
                "search_hadith" -> "হাদীস খুঁজুন..."
                "search_dua" -> "দু’আ খুঁজুন..."
                "select_district" -> "আপনার জেলা নির্বাচন করুন"
                "language" -> "ভাষা / Language"
                "theme" -> "থিম (Theme)"
                "qibla_compass" -> "কিবলা কম্পাস"
                "next_prayer" -> "পরবর্তী নামায"
                "aligned" -> "ক্বিবলা সোজা হয়েছে!"
                "not_aligned" -> "ক্বিবলা মেলাতে আপনার ফোনটি ঘোরান"
                "arabic_text_size" -> "আরবি টেক্সট সাইজ"
                "english" -> "ইংরেজি (English)"
                "bangla" -> "বাংলা (Bangla)"
                "light" -> "লাইট মোড"
                "dark" -> "ডার্ক মোড"
                "system" -> "সিস্টেম ডিফল্ট"
                "bookmarks" -> "বুকমার্কস"
                "narrator" -> "বর্ণনায়"
                "source" -> "সূত্র"
                "share" -> "শেয়ার করুন"
                "quick_dua" -> "গুরুত্বপূর্ণ দু'আ সমূহ"
                "adhan_alert_title" -> "সালাত রিমাইন্ডার"
                "adhan_alert_desc" -> "নামাযের সময় হলে নোটিফিকেশন পাঠাতে সাহায্য করে"
                "bd_districts" -> "বাংলাদেশ জেলা সমুহ"
                "meccan" -> "মাক্কী"
                "medinan" -> "মাদানী"
                "verses" -> "আয়াত"
                else -> key
            }
        } else {
            when (key) {
                "dashboard" -> "Dashboard"
                "quran" -> "Al-Quran"
                "hadith" -> "Hadith"
                "dua" -> "Dua"
                "qibla" -> "Qibla"
                "settings" -> "Settings"
                "fajr" -> "Fajr"
                "sunrise" -> "Sunrise"
                "dhuhr" -> "Dhuhr"
                "asr" -> "Asr"
                "maghrib" -> "Maghrib"
                "isha" -> "Isha"
                "district" -> "District"
                "notifications" -> "Prayer Alerts"
                "search_surah" -> "Search Surah..."
                "search_hadith" -> "Search Hadith..."
                "search_dua" -> "Search Dua..."
                "select_district" -> "Select Your District"
                "language" -> "Language / ভাষা"
                "theme" -> "Theme Mode"
                "qibla_compass" -> "Qibla Compass"
                "next_prayer" -> "Next Prayer"
                "aligned" -> "Qibla Aligned!"
                "not_aligned" -> "Rotate phone to align with Qibla"
                "arabic_text_size" -> "Arabic Text Size"
                "english" -> "English"
                "bangla" -> "Bangla (বাংলা)"
                "light" -> "Light"
                "dark" -> "Dark"
                "system" -> "System Default"
                "bookmarks" -> "Bookmarks"
                "narrator" -> "Narrator"
                "source" -> "Source"
                "share" -> "Share"
                "quick_dua" -> "Daily Duas"
                "adhan_alert_title" -> "Prayer Reminders"
                "adhan_alert_desc" -> "Receive beautiful notifications at prayer times"
                "bd_districts" -> "Bangladesh Districts"
                "meccan" -> "Meccan"
                "medinan" -> "Medinan"
                "verses" -> "Verses"
                else -> key
            }
        }
    }
}

// ---------------- DASHBOARD SCREEN ----------------
@Composable
fun DashboardScreen(
    viewModel: IslamicViewModel,
    onNavigateToDua: () -> Unit,
    onNavigateToQibla: () -> Unit
) {
    val languageState by viewModel.language.collectAsState()
    val isBn = languageState == "bn"

    val district by viewModel.selectedDistrict.collectAsState()
    val prayerTimes by viewModel.currentPrayerTimes.collectAsState()
    val nextName by viewModel.nextPrayerName.collectAsState()
    val countdown by viewModel.nextPrayerCountdown.collectAsState()
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()

    var showDistrictDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Hero Card (Primary Prayer Card)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.35f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Top row: Title, Location selection, Info
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = Loc.t("next_prayer", isBn).uppercase(),
                                color = MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            )
                            val bnNames = mapOf(
                                "Fajr" to "ফজর", "Sunrise" to "সূর্যোদয়", "Dhuhr" to "যোহর",
                                "Asr" to "আসর", "Maghrib" to "মাগরিব", "Isha" to "এশা"
                            )
                            val displayName = if (isBn) bnNames[nextName] ?: nextName else nextName
                            Text(
                                text = displayName,
                                color = MaterialTheme.colorScheme.tertiary,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                text = countdown,
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.9f),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        // Location Pill
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
                                .clickable { showDistrictDialog = true }
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = if (isBn) district.nameBn else district.nameEn,
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    // Mini horizontal schedule highlighting the next prayer
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val miniPrayers = listOf(
                            "Fajr" to prayerTimes.fajr,
                            "Dhuhr" to prayerTimes.dhuhr,
                            "Asr" to prayerTimes.asr,
                            "Maghrib" to prayerTimes.maghrib,
                            "Isha" to prayerTimes.isha
                        )
                        val bnBtnNames = mapOf(
                            "Fajr" to "ফজর", "Dhuhr" to "যোহর", "Asr" to "আসর", "Maghrib" to "মাগরিব", "Isha" to "এশা"
                        )

                        miniPrayers.forEach { (nameKey, timeString) ->
                            val isNext = nextName.lowercase() == nameKey.lowercase()
                            val label = if (isBn) bnBtnNames[nameKey] ?: nameKey else nameKey

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        if (isNext) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)
                                    )
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isNext) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondary
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = timeString,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isNext) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.tertiary
                                        )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Quick Tools Row (Compass & Duas)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("compact_qibla_button")
                        .clickable { onNavigateToQibla() },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Explore,
                                contentDescription = "Qibla",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        Column {
                            Text(
                                text = Loc.t("qibla", isBn),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "21.4° N, Makkah",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("quick_duas_button")
                        .clickable { onNavigateToDua() },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Dua",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        Column {
                            Text(
                                text = Loc.t("dua", isBn),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = Loc.t("quick_dua", isBn),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        }

        // Daily Inspiration Card (Quranic Verse / Quote of the Day)
        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("daily_inspiration_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FormatQuote,
                            contentDescription = "Quote",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = if (isBn) "আজকের আয়াত" else "Verse of the Day",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "إِنَّ مَعَ الْعُسْرِ يُسْرًا",
                            fontSize = 24.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Right,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = if (isBn) "“নিশ্চয় কষ্টের সাথেই স্বস্তি রয়েছে।”" else "\"For indeed, with hardship [will be] ease.\"",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 22.sp
                        )
                        Text(
                            text = if (isBn) "- সূরা আল-ইনশিরাহ (আয়াত ৫)" else "- Surah Al-Inshirah (Verse 5)",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        // Daily Prayers Schedule
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = if (isBn) "আজকের নামাযের সময়সূচী" else "Today's Prayer Schedule",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                )

                val prayers = listOf(
                    Triple(Loc.t("fajr", isBn), prayerTimes.fajr, Icons.Default.Brightness2),
                    Triple(Loc.t("sunrise", isBn), prayerTimes.sunrise, Icons.Default.WbTwilight),
                    Triple(Loc.t("dhuhr", isBn), prayerTimes.dhuhr, Icons.Default.WbSunny),
                    Triple(Loc.t("asr", isBn), prayerTimes.asr, Icons.Default.WbCloudy),
                    Triple(Loc.t("maghrib", isBn), prayerTimes.maghrib, Icons.Default.WbTwilight),
                    Triple(Loc.t("isha", isBn), prayerTimes.isha, Icons.Default.NightsStay)
                )

                prayers.forEach { (name, time, icon) ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = name,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = name,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                            Text(
                                text = time,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
    }

    // District Selector Dialog
    if (showDistrictDialog) {
        AlertDialog(
            onDismissRequest = { showDistrictDialog = false },
            title = {
                Text(
                    text = Loc.t("select_district", isBn),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Box(modifier = Modifier.height(280.dp)) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(IslamicData.districts) { d ->
                            val isSelected = d.nameEn == district.nameEn
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("district_item_${d.nameEn.lowercase()}")
                                    .clickable {
                                        viewModel.setDistrict(d.nameEn)
                                        showDistrictDialog = false
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                )
                            ) {
                                Text(
                                    text = if (isBn) d.nameBn else d.nameEn,
                                    modifier = Modifier.padding(16.dp),
                                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {}
        )
    }
}


// ---------------- QURAN SECTION ----------------
@Composable
fun QuranScreen(viewModel: IslamicViewModel) {
    val languageState by viewModel.language.collectAsState()
    val isBn = languageState == "bn"

    val searchQuery by viewModel.quranSearchQuery.collectAsState()
    val filteredSurahs by viewModel.filteredSurahs.collectAsState()
    val selectedSurah by viewModel.selectedSurah.collectAsState()
    val quranTextSize by viewModel.quranTextSize.collectAsState()
    val bookmarks by viewModel.bookmarkedSurahs.collectAsState()

    AnimatedContent(
        targetState = selectedSurah,
        transitionSpec = {
            slideInHorizontally { if (it > 0) it else -it } togetherWith slideOutHorizontally { if (it > 0) -it else it }
        },
        label = "QuranFlow"
    ) { currentSurah ->
        if (currentSurah == null) {
            // Surah Directory List
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                // Search field
                TextField(
                    value = searchQuery,
                    onValueChange = { viewModel.setQuranSearch(it) },
                    placeholder = { Text(Loc.t("search_surah", isBn)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .testTag("quran_search_field"),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    leadingIcon = { Icon(Icons.Default.Search, "Search") },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.setQuranSearch("") }) {
                                Icon(Icons.Default.Close, "Clear")
                            }
                        }
                    }
                )

                // List of Surahs
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(filteredSurahs) { surah ->
                        val isBookmarked = bookmarks.contains(surah.id)
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("surah_card_${surah.id}")
                                .clickable { viewModel.selectSurah(surah) },
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    // Circular ID badge
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primaryContainer),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = surah.id.toString(),
                                            fontWeight = FontWeight.ExtraBold,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }

                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(2.dp)
                                    ) {
                                        Text(
                                            text = if (isBn) surah.banglaName else surah.englishName,
                                            fontWeight = FontWeight.ExtraBold,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = if (isBn) surah.typeBn else surah.type,
                                                style = MaterialTheme.typography.labelMedium,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text = "•",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Color.LightGray
                                            )
                                            Text(
                                                text = "${surah.verseCount} ${Loc.t("verses", isBn)}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                            )
                                        }
                                    }
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Text(
                                        text = surah.arabicName,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.secondary,
                                        fontFamily = FontFamily.Serif
                                    )
                                    IconButton(
                                        onClick = { viewModel.toggleSurahBookmark(surah.id) }
                                    ) {
                                        Icon(
                                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                                            contentDescription = "Bookmark",
                                            tint = if (isBookmarked) MaterialTheme.colorScheme.secondary else Color.Gray
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Surah Reading Detailed View Page
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                // Header Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        modifier = Modifier.testTag("back_to_surah_list"),
                        onClick = { viewModel.selectSurah(null) }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back List")
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (isBn) currentSurah.banglaName else currentSurah.englishName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "${currentSurah.type} • ${currentSurah.verseCount} Verses",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }

                    val isBookmarked = bookmarks.contains(currentSurah.id)
                    IconButton(onClick = { viewModel.toggleSurahBookmark(currentSurah.id) }) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) MaterialTheme.colorScheme.secondary else Color.Gray
                        )
                    }
                }

                // Text size adjuster panel
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 2.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FormatSize,
                            contentDescription = "Text Size",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = Loc.t("arabic_text_size", isBn),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Slider(
                            value = quranTextSize,
                            onValueChange = { viewModel.setQuranTextSize(it) },
                            valueRange = 16f..36f,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Verses Page Scroll
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(top = 16.dp, bottom = 48.dp)
                ) {
                    // Show Bismillah except for Surah At-Tawbah
                    if (currentSurah.id != 9 && currentSurah.id != 1) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                                    fontSize = (quranTextSize + 4).sp,
                                    textAlign = TextAlign.Center,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(vertical = 12.dp)
                                )
                            }
                        }
                    }

                    items(currentSurah.verses) { v ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // Verse Meta Header Row
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(26.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = v.number.toString(),
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                }

                                // Arabic body text
                                Text(
                                    text = v.arabic,
                                    fontSize = quranTextSize.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Right,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                )

                                // Translating Text Bangla & English
                                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text(
                                        text = v.bangla,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = v.english,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


// ---------------- HADITH SECTION ----------------
@Composable
fun HadithScreen(viewModel: IslamicViewModel) {
    val languageState by viewModel.language.collectAsState()
    val isBn = languageState == "bn"

    val searchQuery by viewModel.hadithSearchQuery.collectAsState()
    val selectedTopic by viewModel.selectedHadithTopic.collectAsState()
    val filteredHadiths by viewModel.filteredHadiths.collectAsState()

    val topics = listOf("All", "Sincerity (Niyyah)", "Belief (Iman)", "Prayer (Salah)", "Character (Akhlaq)", "Kindness (Rahmah)")
    val topicsBn = mapOf(
        "All" to "সব হাদীস",
        "Sincerity (Niyyah)" to "নিয়্যত",
        "Belief (Iman)" to "ঈমান",
        "Prayer (Salah)" to "সালাত",
        "Character (Akhlaq)" to "উত্তম চরিত্র",
        "Kindness (Rahmah)" to "দয়াশীলতা"
    )

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Hadith search field
        TextField(
            value = searchQuery,
            onValueChange = { viewModel.setHadithSearch(it) },
            placeholder = { Text(Loc.t("search_hadith", isBn)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .testTag("hadith_search_field"),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            leadingIcon = { Icon(Icons.Default.Search, "Search") },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.setHadithSearch("") }) {
                        Icon(Icons.Default.Close, "Clear")
                    }
                }
            }
        )

        // Topic scroll list
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(topics) { topic ->
                val isSelected = topic == selectedTopic
                val label = if (isBn) topicsBn[topic] ?: topic else topic
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.setHadithTopic(topic) },
                    label = { Text(label, fontWeight = FontWeight.Bold) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.testTag("hadith_topic_${topic.lowercase().replace(" ", "_")}")
                )
            }
        }

        // List display scroll
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            items(filteredHadiths) { h ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Topic Badge and narrator info
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SuggestionChip(
                                onClick = {},
                                label = {
                                    Text(
                                        text = if (isBn) topicsBn[h.topicEn] ?: h.topicEn else h.topicEn,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                            )
                            IconButton(
                                onClick = {
                                    val sendIntent: Intent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, "${h.banglaText}\n\n- ${h.source}")
                                        type = "text/plain"
                                    }
                                    val shareIntent = Intent.createChooser(sendIntent, null)
                                    context.startActivity(shareIntent)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Share",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        // Body text of Hadith (Bangla prioritized)
                        Text(
                            text = h.banglaText,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 24.sp
                        )

                        Text(
                            text = h.englishText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                            lineHeight = 20.sp
                        )

                        HorizontalDivider(thickness = 0.8.dp, color = MaterialTheme.colorScheme.surfaceVariant)

                        // Footnotes / Narrators
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${Loc.t("narrator", isBn)}: ${if (isBn) h.narratorBn else h.narratorEn}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text(
                                text = "${Loc.t("source", isBn)}: ${h.source}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }
        }
    }
}


// ---------------- DUA SECTION ----------------
@Composable
fun DuaScreen(viewModel: IslamicViewModel) {
    val languageState by viewModel.language.collectAsState()
    val isBn = languageState == "bn"

    val searchQuery by viewModel.duaSearchQuery.collectAsState()
    val selectedCategory by viewModel.selectedDuaCategory.collectAsState()
    val filteredDuas by viewModel.filteredDuas.collectAsState()
    val bookmarks by viewModel.bookmarkedDuas.collectAsState()

    val categories = listOf("All", "Morning & Evening", "Daily Life", "Home & Travel", "Mosque")
    val catTranslationMap = mapOf(
        "All" to "সব দোয়া",
        "Morning & Evening" to "সকাল ও সন্ধ্যা",
        "Daily Life" to "দৈনন্দিন জীবন",
        "Home & Travel" to "ঘর ও সফর",
        "Mosque" to "মসজিদ"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Dua search field
        TextField(
            value = searchQuery,
            onValueChange = { viewModel.setDuaSearch(it) },
            placeholder = { Text(Loc.t("search_dua", isBn)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .testTag("dua_search_field"),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            leadingIcon = { Icon(Icons.Default.Search, "Search") },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.setDuaSearch("") }) {
                        Icon(Icons.Default.Close, "Clear")
                    }
                }
            }
        )

        // Categories selector scroll
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                val isSelected = category == selectedCategory
                val label = if (isBn) catTranslationMap[category] ?: category else category
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.setDuaCategory(category) },
                    label = { Text(label, fontWeight = FontWeight.Bold) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                        selectedLabelColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    modifier = Modifier.testTag("dua_chip_${category.lowercase().replace(" ", "_")}")
                )
            }
        }

        // List display scroll
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            items(filteredDuas) { d ->
                val isBookmarked = bookmarks.contains(d.id)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Title header row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isBn) d.titleBn else d.titleEn,
                                fontWeight = FontWeight.ExtraBold,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.weight(1f)
                            )
                            Row {
                                IconButton(onClick = { viewModel.toggleDuaBookmark(d.id) }) {
                                    Icon(
                                        imageVector = if (isBookmarked) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                                        contentDescription = "Favorite",
                                        tint = if (isBookmarked) MaterialTheme.colorScheme.primary else Color.Gray
                                    )
                                }
                            }
                        }

                        // Arabic text beautifully aligned to the right
                        Text(
                            text = d.arabic,
                            textAlign = TextAlign.Right,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontFamily = FontFamily.Serif,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )

                        // Pronunciation
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = if (isBn) "উচ্চারণ:" else "Pronunciation:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = if (isBn) d.pronunciationBn else d.pronunciationEn,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.surfaceVariant)

                        // Meaning
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = if (isBn) "অর্থ:" else "Meaning:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = if (isBn) d.meaningBn else d.meaningEn,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}


// ---------------- QIBLA COMPASS SCREEN ----------------
@Composable
fun QiblaScreen(viewModel: IslamicViewModel) {
    val languageState by viewModel.language.collectAsState()
    val isBn = languageState == "bn"

    val azimuth by viewModel.compassAzimuth.collectAsState()
    val qiblaBearing by viewModel.userBearingToQibla.collectAsState()

    // Qibla needle relative direction is (bearing - azimuth)
    val relativeKabaAngle = (qiblaBearing - azimuth).toFloat()
    
    // Check alignment within +/- 5 degrees tolerance
    val isAligned = abs(relativeKabaAngle % 360) < 5f || abs(relativeKabaAngle % 360 - 360f) < 5f

    // Animate rotation angle values smoothly
    val animatedCompassRotation by animateFloatAsState(
        targetValue = -azimuth,
        animationSpec = tween(durationMillis = 150),
        label = "CompassSmooth"
    )

    val animatedNeedleRotation by animateFloatAsState(
        targetValue = relativeKabaAngle,
        animationSpec = tween(durationMillis = 150),
        label = "NeedleSmooth"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = Loc.t("qibla_compass", isBn),
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Kaaba Angle: ${String.format("%.1f", qiblaBearing)}° N",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }

        // Qibla Compass housing
        Box(
            modifier = Modifier
                .size(290.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
                .border(6.dp, MaterialTheme.colorScheme.primaryContainer, CircleShape)
                .drawBehind {
                    // Draw outer minute markings on Compass dial
                    val ringRadius = size.minDimension / 2 - 12.dp.toPx()
                    val center = size / 2f
                    for (angle in 0 until 360 step 15) {
                        val angleRad = (angle * Math.PI / 180.0).toFloat()
                        val lineLength = if (angle % 90 == 0) 15.dp.toPx() else 8.dp.toPx()
                        val startX = center.width + ringRadius * kotlin.math.sin(angleRad)
                        val startY = center.height - ringRadius * kotlin.math.cos(angleRad)
                        val endX = center.width + (ringRadius - lineLength) * kotlin.math.sin(angleRad)
                        val endY = center.height - (ringRadius - lineLength) * kotlin.math.cos(angleRad)
                        
                        drawLine(
                            color = if (angle % 90 == 0) Color(0xFFA17C14) else Color.LightGray,
                            start = androidx.compose.ui.geometry.Offset(startX, startY),
                            end = androidx.compose.ui.geometry.Offset(endX, endY),
                            strokeWidth = 2.dp.toPx()
                        )
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            // Rotating Dial housing cardinal letters (N, E, S, W)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(animatedCompassRotation),
                contentAlignment = Alignment.Center
            ) {
                // North symbol indicator
                Text(
                    text = "N",
                    fontWeight = FontWeight.Black,
                    fontSize = 24.sp,
                    color = Color.Red,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp)
                )
                Text(
                    text = "S",
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                )
                Text(
                    text = "W",
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                )
                Text(
                    text = "E",
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                )
            }

            // Qibla Pointer Layer (pointing at kabaAngle)
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .clip(CircleShape)
                    .rotate(animatedNeedleRotation),
                contentAlignment = Alignment.Center
            ) {
                // Compass Inner Line & Kaaba Pointer Emblem
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Spacer(modifier = Modifier.height(18.dp))
                    
                    // Kaaba house icon or pointer
                    Icon(
                        imageVector = Icons.Default.Mosque,
                        contentDescription = "Kaaba Pointer",
                        tint = if (isAligned) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(
                                if (isAligned) MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                                else Color.Transparent
                            )
                            .padding(4.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Up Indicator",
                        tint = if (isAligned) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )

                    // Dial long vertical pointer line
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .weight(1f)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        if (isAligned) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                }
            }

            // Center Ring Center Holder
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape)
            )
        }

        // Qibla Alignment status badge footer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(100),
                colors = CardDefaults.cardColors(
                    containerColor = if (isAligned) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceVariant
                ),
                border = BorderStroke(
                    1.dp,
                    if (isAligned) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outlineVariant
                )
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = if (isAligned) Icons.Default.CheckCircle else Icons.Default.RotateRight,
                        contentDescription = "Status",
                        tint = if (isAligned) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = if (isAligned) Loc.t("aligned", isBn) else Loc.t("not_aligned", isBn),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (isAligned) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}


// ---------------- SETTINGS SECTION ----------------
@Composable
fun SettingsScreen(viewModel: IslamicViewModel) {
    val languageState by viewModel.language.collectAsState()
    val isBn = languageState == "bn"

    val currentTheme by viewModel.theme.collectAsState()
    val currentDistrict by viewModel.selectedDistrict.collectAsState()
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()

    var showDistrictDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App Identity Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_app_icon),
                    contentDescription = "App Icon Logo",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Column {
                    Text(
                        text = "DeenPath by Mahi",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Version 1.0.0 • Islamic App for BD",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }
            }
        }

        // Section Title: Preferences
        Text(
            text = if (isBn) "অ্যাপ্লিকেশন সেটিংস" else "Preferences",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        // Settings option 1: Language selection card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(Icons.Default.Language, "Lang")
                    Text(
                        text = Loc.t("language", isBn),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .testTag("lang_bn_button"),
                        onClick = { viewModel.setLanguage("bn") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isBn) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            text = "বাংলা",
                            color = if (isBn) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .testTag("lang_en_button"),
                        onClick = { viewModel.setLanguage("en") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!isBn) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            text = "English",
                            color = if (!isBn) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Settings option 2: UI theme selector card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(Icons.Default.Palette, "Theme")
                    Text(
                        text = Loc.t("theme", isBn),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                val modes = listOf("light", "dark", "system")
                val modesBn = mapOf("light" to "লাইট", "dark" to "ডার্ক", "system" to "ডিফল্ট")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    modes.forEach { mode ->
                        val isSel = currentTheme == mode
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .testTag("theme_${mode}_button"),
                            onClick = { viewModel.setTheme(mode) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSel) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceVariant
                            ),
                            contentPadding = PaddingValues(horizontal = 4.dp)
                        ) {
                            Text(
                                text = if (isBn) modesBn[mode] ?: mode else mode.replaceFirstChar { it.uppercase() },
                                color = if (isSel) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Settings option 3: Location setting card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDistrictDialog = true }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(Icons.Default.PinDrop, "Location Pin")
                    Column {
                        Text(
                            text = Loc. t("district", isBn),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = if (isBn) currentDistrict.nameBn else currentDistrict.nameEn,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = "Forward arrow",
                    tint = Color.LightGray,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        // Settings option 4: Adhan Alert notifications
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(Icons.Default.NotificationsActive, "Reminders Bell")
                    Column {
                        Text(
                            text = Loc.t("adhan_alert_title", isBn),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = Loc.t("adhan_alert_desc", isBn),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
                Switch(
                    modifier = Modifier.testTag("adhan_notification_switch"),
                    checked = notificationsEnabled,
                    onCheckedChange = { viewModel.setNotificationsEnabled(it) }
                )
            }
        }
    }

    // Reuse district alert dialog in Settings too
    if (showDistrictDialog) {
        AlertDialog(
            onDismissRequest = { showDistrictDialog = false },
            title = {
                Text(
                    text = Loc.t("select_district", isBn),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Box(modifier = Modifier.height(280.dp)) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(IslamicData.districts) { d ->
                            val isSelected = d.nameEn == currentDistrict.nameEn
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.setDistrict(d.nameEn)
                                        showDistrictDialog = false
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                )
                            ) {
                                Text(
                                    text = if (isBn) d.nameBn else d.nameEn,
                                    modifier = Modifier.padding(16.dp),
                                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {}
        )
    }
}
