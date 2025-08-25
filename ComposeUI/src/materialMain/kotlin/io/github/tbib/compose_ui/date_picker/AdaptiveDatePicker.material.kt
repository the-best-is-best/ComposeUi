package io.github.tbib.compose_ui.date_picker

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun AdaptiveDatePicker(
    state: AdaptiveDatePickerState,
    modifier: Modifier,
    dateFormatter: DatePickerFormatter,
    title: @Composable() (() -> Unit)?,
    headline: @Composable() (() -> Unit)?,
    showModeToggle: Boolean,
    colors: DatePickerColors
) {
    DatePicker(
        state = state.datePickerState,
        modifier = modifier,
        dateFormatter = dateFormatter,
        title = title,
        headline = headline,
        showModeToggle = showModeToggle,
        colors = colors,
    )
}
