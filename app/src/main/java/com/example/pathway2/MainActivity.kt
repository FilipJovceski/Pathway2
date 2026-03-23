package com.example.pathway2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pathway2.ui.theme.Pathway2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pathway2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Pathway2App()
                }
            }
        }
    }
}

// ── Навигациски екрани ──────────────────────────────────────
sealed class Screen(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Home    : Screen("home",    "Дома",      Icons.Filled.Home)
    object Theming : Screen("theming", "Теми",      Icons.Filled.Palette)
    object Anim    : Screen("anim",    "Анимации",  Icons.Filled.Animation)
}

// ── Главна апликација со Bottom Navigation ──────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pathway2App() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    val screens = listOf(Screen.Home, Screen.Theming, Screen.Anim)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (currentScreen) {
                            Screen.Home    -> "🎓 Pathway 2"
                            Screen.Theming -> "🎨 Theming"
                            Screen.Anim    -> "✨ Animations"
                            else           -> "Pathway 2"
                        },
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar {
                screens.forEach { screen ->
                    NavigationBarItem(
                        selected = currentScreen == screen,
                        onClick  = { currentScreen = screen },
                        icon     = { Icon(screen.icon, contentDescription = screen.label) },
                        label    = { Text(screen.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                Screen.Home    -> HomeScreen()
                Screen.Theming -> ThemingScreen()
                Screen.Anim    -> AnimationScreen()
                else           -> HomeScreen()
            }
        }
    }
}

// ══════════════════════════════════════════════════════════════
// ЕКРАН 1 — HOME
// ══════════════════════════════════════════════════════════════
@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Pathway 2 теми",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        item { TopicCard("🎨 Theming", "Сопствени бои, типографија и форми со Material 3") }
        item { TopicCard("✨ Animations", "animateContentSize, AnimatedVisibility, animate*AsState") }
        item { TopicCard("📐 Layouts", "Scaffold, TopAppBar, BottomNavigation, LazyColumn") }
        item { TopicCard("🎭 Scaffold", "Структура на екран со слотови за различни компоненти") }
    }
}

@Composable
fun TopicCard(title: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                description,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

// ══════════════════════════════════════════════════════════════
// ЕКРАН 2 — THEMING
// ══════════════════════════════════════════════════════════════
@Composable
fun ThemingScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                "Material 3 Theming",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Бои
        item {
            SectionTitle("🎨 Color Scheme")
            ColorShowcase()
        }

        // Типографија
        item {
            SectionTitle("✍️ Typography")
            TypographyShowcase()
        }

        // Форми
        item {
            SectionTitle("🔷 Shapes")
            ShapesShowcase()
        }

        // Копчиња
        item {
            SectionTitle("🔘 Buttons")
            ButtonsShowcase()
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 6.dp)
    )
}

@Composable
fun ColorShowcase() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        ColorRow("Primary",          MaterialTheme.colorScheme.primary)
        ColorRow("Secondary",        MaterialTheme.colorScheme.secondary)
        ColorRow("Tertiary",         MaterialTheme.colorScheme.tertiary)
        ColorRow("Error",            MaterialTheme.colorScheme.error)
        ColorRow("PrimaryContainer", MaterialTheme.colorScheme.primaryContainer)
    }
}

@Composable
fun ColorRow(name: String, color: androidx.compose.ui.graphics.Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = color
        ) {}
        Text(name, fontSize = 14.sp)
    }
}

@Composable
fun TypographyShowcase() {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("displaySmall",  style = MaterialTheme.typography.displaySmall.copy(fontSize = 20.sp))
            Text("headlineMedium", style = MaterialTheme.typography.headlineMedium)
            Text("titleLarge",   style = MaterialTheme.typography.titleLarge)
            Text("bodyLarge",    style = MaterialTheme.typography.bodyLarge)
            Text("labelSmall",   style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun ShapesShowcase() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf(4, 12, 50).forEach { radius ->
            Surface(
                modifier = Modifier.size(60.dp),
                shape = RoundedCornerShape(radius.dp),
                color = MaterialTheme.colorScheme.primary
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("${radius}dp", color = MaterialTheme.colorScheme.onPrimary, fontSize = 10.sp)
                }
            }
        }
    }
}

@Composable
fun ButtonsShowcase() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Button (Filled)")
        }
        OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("OutlinedButton")
        }
        TextButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("TextButton")
        }
        ElevatedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("ElevatedButton")
        }
    }
}

// ══════════════════════════════════════════════════════════════
// ЕКРАН 3 — ANIMATIONS
// ══════════════════════════════════════════════════════════════
@Composable
fun AnimationScreen() {
    var visible       by remember { mutableStateOf(true) }
    var expanded      by remember { mutableStateOf(false) }
    var colorToggle   by remember { mutableStateOf(false) }
    var sizeToggle    by remember { mutableStateOf(false) }

    // animate*AsState примери
    val animatedColor by animateColorAsState(
        targetValue = if (colorToggle)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.tertiary,
        animationSpec = tween(durationMillis = 600),
        label = "colorAnim"
    )
    val animatedSize by animateDpAsState(
        targetValue = if (sizeToggle) 120.dp else 60.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness    = Spring.StiffnessLow
        ),
        label = "sizeAnim"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1) AnimatedVisibility
        item {
            SectionTitle("1. AnimatedVisibility")
            Button(
                onClick = { visible = !visible },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (visible) "Сокриј" else "Прикажи")
            }
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedVisibility(
                visible = visible,
                enter   = fadeIn() + slideInVertically(),
                exit    = fadeOut() + slideOutVertically()
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        "Оваа картичка е анимирана!",
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // 2) animateContentSize
        item {
            SectionTitle("2. animateContentSize")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness    = Spring.StiffnessLow
                        )
                    ),
                onClick = { expanded = !expanded }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Клини за проширување", fontWeight = FontWeight.Bold)
                    if (expanded) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Ова е скриената содржина која се прикажува со анимација кога картичката се проширува!")
                    }
                }
            }
        }

        // 3) animateColorAsState
        item {
            SectionTitle("3. animateColorAsState")
            Button(
                onClick = { colorToggle = !colorToggle },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = animatedColor)
            ) {
                Text("Промени боја")
            }
        }

        // 4) animateDpAsState
        item {
            SectionTitle("4. animateDpAsState (Spring)")
            Button(
                onClick = { sizeToggle = !sizeToggle },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Промени големина")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Surface(
                    modifier = Modifier.size(animatedSize),
                    shape    = RoundedCornerShape(12.dp),
                    color    = MaterialTheme.colorScheme.secondary
                ) {}
            }
        }
    }
}