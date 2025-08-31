# KAdaptiveUI

KAdaptiveUI is a Kotlin Multiplatform library that provides a set of adaptive UI components for Android and iOS using Jetpack Compose and UIKit.  
It helps developers write once and run adaptive UI for both platforms with platform-specific look and feel.

## Installation

Add dependency to your build.gradle.kts:

```kotlin
implementation("io.github.tbib.kadaptiveui:core:1.0.0")
```

## Components

### 1. AdaptiveCircularProgressIndicator

```kotlin
AdaptiveCircularProgressIndicator(
    isVisible = true
)
```

### 2. AdaptiveTile

```kotlin
AdaptiveTile(
    title = "Settings",
    subtitle = "Manage preferences",
    leadingIcon = adaptiveIcon,
    trailingIcon = adaptiveIcon,
    onClick = {}
)
```

### 3. AdaptiveAppBar

```kotlin
AdaptiveAppBar(
    title = "Home",
    actions = listOf(
        AdaptiveActionItem("Search", adaptiveIcon) { /* onClick */ }
    )
)
```

### 4. AdaptiveBottomNavBar

```kotlin
AdaptiveBottomNavBar(
    items = listOf(
        AdaptiveBottomNavItem("Home", adaptiveIcon, true),
        AdaptiveBottomNavItem("Profile", adaptiveIcon, false)
    ),
    onItemSelected = { /* index */ }
)
```

### 5. AdaptiveBottomSheet

```kotlin
AdaptiveBottomSheet(
    state = rememberAdaptiveSheetState(),
    content = { Text("Sheet content") }
)
```

### 6. AdaptiveDatePicker

```kotlin
AdaptiveDatePicker(
    state = rememberAdaptiveDatePickerState(),
    onDateSelected = { date -> }
)
```

### 7. AdaptiveDialog

```kotlin
AdaptiveDialog(
    onDismissRequest = { },
    title = "Confirm",
    text = "Are you sure?",
    confirmButtonText = "OK",
    dismissButtonText = "Cancel",
    onConfirm = { }
)
```

### 8. AdaptiveIconButton

```kotlin
AdaptiveIconButton(
    onClick = {},
    adaptiveIcons = adaptiveIcon
)
```

### 9. AdaptiveSlider

```kotlin
AdaptiveSlider(
    value = 0.5f,
    onValueChange = { }
)
```

### 10. AdaptiveTextButton

```kotlin
AdaptiveTextButton(
    text = "Continue",
    onClick = { }
)
```

### 11. AdaptiveTimePicker

```kotlin
AdaptiveTimePicker(
    state = rememberAdaptiveTimePickerState(),
    onTimeSelected = { time -> }
)
```

### 12. AdaptiveSwitch

```kotlin
AdaptiveSwitch(
    checked = true,
    onCheckedChange = { }
)
```

## License

MIT License
