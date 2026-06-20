package com.example

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.ui.IslamicViewModel
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var rotationSensor: Sensor? = null
    private var accelerometer: Sensor? = null
    private var magnetometer: Sensor? = null

    private val rMat = FloatArray(9)
    private val orientation = FloatArray(3)
    private val gravity = FloatArray(3)
    private val geomagnetic = FloatArray(3)

    private var hasGravity = false
    private var hasGeomagnetic = false

    private lateinit var viewModel: IslamicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize ViewModel via standard Provider
        viewModel = ViewModelProvider(this)[IslamicViewModel::class.java]

        // Setup Qibla compass hardware sensors
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        if (rotationSensor == null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        }

        enableEdgeToEdge()

        setContent {
            val themeState by viewModel.theme.collectAsState()
            val languageState by viewModel.language.collectAsState()
            val isBn = languageState == "bn"

            val darkTheme = when (themeState) {
                "light" -> false
                "dark" -> true
                else -> isSystemInDarkTheme()
            }

            MyApplicationTheme(darkTheme = darkTheme) {
                var currentTab by remember { mutableStateOf("dashboard") }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(
                            modifier = Modifier
                                .navigationBarsPadding()
                                .testTag("app_bottom_bar"),
                            containerColor = MaterialTheme.colorScheme.surface,
                            tonalElevation = 8.dp
                        ) {
                            val items = listOf("dashboard", "quran", "hadith", "dua", "qibla", "settings")
                            val iconsSelected = listOf(
                                Icons.Default.Dashboard,
                                Icons.Default.MenuBook,
                                Icons.Default.Book,
                                Icons.Default.Favorite,
                                Icons.Default.Explore,
                                Icons.Default.Settings
                            )
                            val iconsUnselected = listOf(
                                Icons.Outlined.Dashboard,
                                Icons.Outlined.MenuBook,
                                Icons.Outlined.Book,
                                Icons.Outlined.FavoriteBorder,
                                Icons.Outlined.Explore,
                                Icons.Outlined.Settings
                            )

                            items.forEachIndexed { index, tab ->
                                val isSelected = currentTab == tab
                                NavigationBarItem(
                                    modifier = Modifier.testTag("nav_item_$tab"),
                                    selected = isSelected,
                                    onClick = { currentTab = tab },
                                    icon = {
                                        Icon(
                                            imageVector = if (isSelected) iconsSelected[index] else iconsUnselected[index],
                                            contentDescription = tab
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = Loc.t(tab, isBn),
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .windowInsetsPadding(WindowInsets.statusBars)
                    ) {
                        when (currentTab) {
                            "dashboard" -> DashboardScreen(
                                viewModel = viewModel,
                                onNavigateToDua = { currentTab = "dua" },
                                onNavigateToQibla = { currentTab = "qibla" }
                            )
                            "quran" -> QuranScreen(viewModel = viewModel)
                            "hadith" -> HadithScreen(viewModel = viewModel)
                            "dua" -> DuaScreen(viewModel = viewModel)
                            "qibla" -> QiblaScreen(viewModel = viewModel)
                            "settings" -> SettingsScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        rotationSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        } ?: run {
            accelerometer?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
            magnetometer?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(rMat, event.values)
            val azimuthRad = SensorManager.getOrientation(rMat, orientation)[0]
            val azimuthDeg = Math.toDegrees(azimuthRad.toDouble()).toFloat()
            val compassAzimuth = (azimuthDeg + 360f) % 360f
            viewModel.updateAzimuth(compassAzimuth)
        } else {
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                System.arraycopy(event.values, 0, gravity, 0, event.values.size)
                hasGravity = true
            } else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                System.arraycopy(event.values, 0, geomagnetic, 0, event.values.size)
                hasGeomagnetic = true
            }
            if (hasGravity && hasGeomagnetic) {
                if (SensorManager.getRotationMatrix(rMat, null, gravity, geomagnetic)) {
                    val azimuthRad = SensorManager.getOrientation(rMat, orientation)[0]
                    val azimuthDeg = Math.toDegrees(azimuthRad.toDouble()).toFloat()
                    val compassAzimuth = (azimuthDeg + 360f) % 360f
                    viewModel.updateAzimuth(compassAzimuth)
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
}
