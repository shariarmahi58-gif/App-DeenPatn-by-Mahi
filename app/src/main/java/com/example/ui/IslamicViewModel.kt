package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.District
import com.example.utils.DailyPrayerTimes
import com.example.data.Dua
import com.example.data.Hadith
import com.example.data.IslamicData
import com.example.data.PreferenceManager
import com.example.data.Surah
import com.example.utils.PrayerTimeCalculator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class IslamicViewModel(application: Application) : AndroidViewModel(application) {

    private val preferenceManager = PreferenceManager(application)

    // UI Preferences State
    val language: StateFlow<String> = preferenceManager.languageFlow.stateIn(
        viewModelScope, SharingStarted.Eagerly, "bn"
    )

    val theme: StateFlow<String> = preferenceManager.themeFlow.stateIn(
        viewModelScope, SharingStarted.Eagerly, "system"
    )

    private val _selectedDistrictStr = preferenceManager.districtFlow
    val selectedDistrict: StateFlow<District> = _selectedDistrictStr.combine(MutableStateFlow(IslamicData.districts)) { name, list ->
        list.firstOrNull { it.nameEn.equals(name, ignoreCase = true) } ?: list[0]
    }.stateIn(viewModelScope, SharingStarted.Eagerly, IslamicData.districts[0])

    val notificationsEnabled: StateFlow<Boolean> = preferenceManager.notificationsEnabledFlow.stateIn(
        viewModelScope, SharingStarted.Eagerly, true
    )

    // Prayer Times and dynamic tracking states
    private val _currentPrayerTimes = MutableStateFlow(
        PrayerTimeCalculator.calculateTimes(Date(), IslamicData.districts[0].lat, IslamicData.districts[0].lon, IslamicData.districts[0].timezoneOffset)
    )
    val currentPrayerTimes: StateFlow<com.example.utils.DailyPrayerTimes> = _currentPrayerTimes.asStateFlow()

    private val _nextPrayerName = MutableStateFlow("")
    val nextPrayerName: StateFlow<String> = _nextPrayerName.asStateFlow()

    private val _nextPrayerCountdown = MutableStateFlow("")
    val nextPrayerCountdown: StateFlow<String> = _nextPrayerCountdown.asStateFlow()

    // Quran Screens State
    private val _quranSearchQuery = MutableStateFlow("")
    val quranSearchQuery = _quranSearchQuery.asStateFlow()

    val filteredSurahs: StateFlow<List<Surah>> = _quranSearchQuery.combine(MutableStateFlow(IslamicData.popularSurahs)) { query, list ->
        if (query.isBlank()) list
        else list.filter {
            it.englishName.contains(query, ignoreCase = true) ||
            it.banglaName.contains(query, ignoreCase = true) ||
            it.arabicName.contains(query)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), IslamicData.popularSurahs)

    private val _selectedSurah = MutableStateFlow<Surah?>(null)
    val selectedSurah = _selectedSurah.asStateFlow()

    private val _quranTextSize = MutableStateFlow(22f) // Font size slider state
    val quranTextSize = _quranTextSize.asStateFlow()

    private val _bookmarkedSurahs = MutableStateFlow(setOf<Int>())
    val bookmarkedSurahs = _bookmarkedSurahs.asStateFlow()

    // Hadith Screens State
    private val _hadithSearchQuery = MutableStateFlow("")
    val hadithSearchQuery = _hadithSearchQuery.asStateFlow()

    private val _selectedHadithTopic = MutableStateFlow("All")
    val selectedHadithTopic = _selectedHadithTopic.asStateFlow()

    val filteredHadiths: StateFlow<List<Hadith>> = combine(
        _hadithSearchQuery, _selectedHadithTopic, MutableStateFlow(IslamicData.keyHadiths)
    ) { query, topic, list ->
        var temp = list
        if (topic != "All") {
            temp = temp.filter { it.topicEn.equals(topic, ignoreCase = true) }
        }
        if (query.isNotBlank()) {
            temp = temp.filter {
                it.banglaText.contains(query, ignoreCase = true) ||
                it.englishText.contains(query, ignoreCase = true) ||
                it.narratorEn.contains(query, ignoreCase = true) ||
                it.narratorBn.contains(query, ignoreCase = true)
            }
        }
        temp
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), IslamicData.keyHadiths)

    // Duas state
    private val _duaSearchQuery = MutableStateFlow("")
    val duaSearchQuery = _duaSearchQuery.asStateFlow()

    private val _selectedDuaCategory = MutableStateFlow("All")
    val selectedDuaCategory = _selectedDuaCategory.asStateFlow()

    val filteredDuas: StateFlow<List<Dua>> = combine(
        _duaSearchQuery, _selectedDuaCategory, MutableStateFlow(IslamicData.importantDuas)
    ) { query, category, list ->
        var temp = list
        if (category != "All") {
            temp = temp.filter { it.categoryEn.equals(category, ignoreCase = true) }
        }
        if (query.isNotBlank()) {
            temp = temp.filter {
                it.titleEn.contains(query, ignoreCase = true) ||
                it.titleBn.contains(query, ignoreCase = true) ||
                it.meaningBn.contains(query, ignoreCase = true) ||
                it.pronunciationBn.contains(query, ignoreCase = true)
            }
        }
        temp
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), IslamicData.importantDuas)

    private val _bookmarkedDuas = MutableStateFlow(setOf<Int>())
    val bookmarkedDuas = _bookmarkedDuas.asStateFlow()

    // Qibla state
    private val _compassAzimuth = MutableStateFlow(0f)
    val compassAzimuth = _compassAzimuth.asStateFlow()

    private val _userBearingToQibla = MutableStateFlow(0.0)
    val userBearingToQibla = _userBearingToQibla.asStateFlow()

    private var countdownJob: Job? = null

    init {
        // Collect preference flows to update the calculations dynamically
        viewModelScope.launch {
            selectedDistrict.collect { district ->
                recalculateTimesForDistrict(district)
                _userBearingToQibla.value = PrayerTimeCalculator.calculateQiblaDirection(district.lat, district.lon)
            }
        }
        startCountdownTimer()
    }

    private fun recalculateTimesForDistrict(district: District) {
        val calculated = PrayerTimeCalculator.calculateTimes(
            Date(), district.lat, district.lon, district.timezoneOffset
        )
        _currentPrayerTimes.value = calculated
        updateNextPrayerInfo(calculated)
    }

    fun setLanguage(langCode: String) {
        viewModelScope.launch { preferenceManager.saveLanguage(langCode) }
    }

    fun setTheme(themeVal: String) {
        viewModelScope.launch { preferenceManager.saveTheme(themeVal) }
    }

    fun setDistrict(districtName: String) {
        viewModelScope.launch { preferenceManager.saveDistrict(districtName) }
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch { preferenceManager.saveNotificationsEnabled(enabled) }
    }

    fun setQuranSearch(query: String) {
        _quranSearchQuery.value = query
    }

    fun selectSurah(surah: Surah?) {
        _selectedSurah.value = surah
    }

    fun setQuranTextSize(size: Float) {
        _quranTextSize.value = size
    }

    fun toggleSurahBookmark(id: Int) {
        val current = _bookmarkedSurahs.value
        _bookmarkedSurahs.value = if (current.contains(id)) current - id else current + id
    }

    fun setHadithSearch(query: String) {
        _hadithSearchQuery.value = query
    }

    fun setHadithTopic(topic: String) {
        _selectedHadithTopic.value = topic
    }

    fun setDuaSearch(query: String) {
        _duaSearchQuery.value = query
    }

    fun setDuaCategory(category: String) {
        _selectedDuaCategory.value = category
    }

    fun toggleDuaBookmark(id: Int) {
        val current = _bookmarkedDuas.value
        _bookmarkedDuas.value = if (current.contains(id)) current - id else current + id
    }

    fun updateAzimuth(azimuth: Float) {
        // Apply a small low pass filter to make rotation ultra-smooth
        _compassAzimuth.value = azimuth
    }

    // Dynamic timer checking current clock to find next prayer countdown
    private fun startCountdownTimer() {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            while (true) {
                val times = _currentPrayerTimes.value
                updateNextPrayerInfo(times)
                delay(1000)
            }
        }
    }

    private fun updateNextPrayerInfo(times: com.example.utils.DailyPrayerTimes) {
        val now = Calendar.getInstance()
        val format = SimpleDateFormat("hh:mm a", Locale.US)
        
        fun parsePrayerTime(timeStr: String): Calendar {
            val dateParsed = format.parse(timeStr) ?: Date()
            val cal = Calendar.getInstance()
            cal.time = dateParsed
            
            val result = Calendar.getInstance()
            result.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY))
            result.set(Calendar.MINUTE, cal.get(Calendar.MINUTE))
            result.set(Calendar.SECOND, 0)
            result.set(Calendar.MILLISECOND, 0)
            return result
        }

        val prayersList = listOf(
            Pair("Fajr", parsePrayerTime(times.fajr)),
            Pair("Sunrise", parsePrayerTime(times.sunrise)),
            Pair("Dhuhr", parsePrayerTime(times.dhuhr)),
            Pair("Asr", parsePrayerTime(times.asr)),
            Pair("Maghrib", parsePrayerTime(times.maghrib)),
            Pair("Isha", parsePrayerTime(times.isha))
        )

        var nextName = ""
        var nextCal = Calendar.getInstance()
        var found = false

        for (item in prayersList) {
            if (item.second.after(now)) {
                nextName = item.first
                nextCal = item.second
                found = true
                break
            }
        }

        // If all prayers today passed, next is Fajr tomorrow
        if (!found) {
            nextName = "Fajr"
            nextCal = parsePrayerTime(times.fajr)
            nextCal.add(Calendar.DAY_OF_YEAR, 1)
        }

        _nextPrayerName.value = nextName

        val diffMs = nextCal.timeInMillis - now.timeInMillis
        if (diffMs <= 0) {
            _nextPrayerCountdown.value = if (language.value == "bn") "এখন নামাযের সময়!" else "It's Prayer Time!"
            return
        }

        val hours = diffMs / (1000 * 60 * 60)
        val minutes = (diffMs / (1000 * 60)) % 60
        val seconds = (diffMs / 1000) % 60

        val bnPrayerNames = mapOf(
            "Fajr" to "ফজর",
            "Sunrise" to "সূর্যোদয়",
            "Dhuhr" to "যোহর",
            "Asr" to "আসর",
            "Maghrib" to "মাগরিব",
            "Isha" to "এশা"
        )
        
        val localPrayerName = if (language.value == "bn") bnPrayerNames[nextName] ?: nextName else nextName

        _nextPrayerCountdown.value = if (language.value == "bn") {
            String.format("%s %s %s", 
                if (hours > 0) "${translateNumber(hours)} ঘণ্টা " else "",
                if (minutes > 0 || hours > 0) "${translateNumber(minutes)} মিনিট " else "",
                "${translateNumber(seconds)} সেকেন্ড পর"
            )
        } else {
            String.format("%s - %02dh %02dm %02ds remaining", localPrayerName, hours, minutes, seconds)
        }
    }

    private fun translateNumber(num: Long): String {
        val banglaDigits = listOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')
        val str = num.toString()
        val builder = StringBuilder()
        for (char in str) {
            if (char.isDigit()) {
                builder.append(banglaDigits[char - '0'])
            } else {
                builder.append(char)
            }
        }
        return builder.toString()
    }
}
