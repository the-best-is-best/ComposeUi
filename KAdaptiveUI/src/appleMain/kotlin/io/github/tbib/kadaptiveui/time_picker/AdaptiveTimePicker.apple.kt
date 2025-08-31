package io.github.tbib.kadaptiveui.time_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import io.github.tbib.kadaptiveui.utils.applyTheme
import io.github.tbib.kadaptiveui.utils.isDark
import io.github.tbib.kadaptiveui.utils.surfaceColorAtElevation
import io.github.tbib.kadaptiveui.utils.toUiColor
import platform.UIKit.UIDatePicker

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
actual fun AdaptiveTimePicker(
    state: AdaptiveTimePickerState,
    modifier: Modifier,
    colors: TimePickerColors,
    layoutType: TimePickerLayoutType,
) {
    val datePicker = remember {
        UIDatePicker()
    }
    val datePickerManager = remember {
        TimePickerManager(
            datePicker = datePicker,
            initialMinute = state.minute,
            initialHour = state.hour,
            is24Hour = state.is24hour,
            onHourChanged = { hour ->
                state.hourState = hour
            },
            onMinuteChanged = { minute ->
                state.minuteState = minute
            }
        )
    }

    val absoluteElevation = LocalAbsoluteTonalElevation.current

    val containerColorAtElevation =
        surfaceColorAtElevation(
            color = colors.containerColor,
            elevation = absoluteElevation
        )

    LaunchedEffect(colors, containerColorAtElevation) {
        datePicker.applyTheme(dark = !isDark(colors.timeSelectorUnselectedContentColor))
        datePicker.tintColor = colors.timeSelectorSelectedContainerColor.toUiColor()
        datePicker.backgroundColor = containerColorAtElevation.toUiColor()
    }

    UIKitView(
        factory = {
            datePicker
        },
        properties = UIKitInteropProperties(
            interactionMode = UIKitInteropInteractionMode.NonCooperative,
        ),
        modifier = modifier
            .background(colors.containerColor)
            .then(
                if (datePickerManager.datePickerWidth.value > 0f)
                    Modifier.width(datePickerManager.datePickerWidth.value.dp)
                else
                    Modifier
            )
            .then(
                if (datePickerManager.datePickerHeight.value > 0f)
                    Modifier.height(datePickerManager.datePickerHeight.value.dp)
                else
                    Modifier
            )
    )
}