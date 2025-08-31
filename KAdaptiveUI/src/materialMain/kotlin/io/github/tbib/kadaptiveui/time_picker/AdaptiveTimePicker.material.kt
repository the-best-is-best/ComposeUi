package io.github.tbib.kadaptiveui.time_picker

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun AdaptiveTimePicker(
    state: AdaptiveTimePickerState,
    modifier: Modifier,
    colors: TimePickerColors,
    layoutType: TimePickerLayoutType,
) {
    TimePicker(
        state = state.timePickerState,
        modifier = modifier,
        colors = colors,
        layoutType = layoutType,
    )
}