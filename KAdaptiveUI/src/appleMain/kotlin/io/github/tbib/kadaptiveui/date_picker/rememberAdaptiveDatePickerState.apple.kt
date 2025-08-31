package io.github.tbib.kadaptiveui.date_picker

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import io.github.tbib.kadaptiveui.date_picker.utils.KotlinxDatetimeCalendarModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import platform.Foundation.NSDate
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.UIKit.UIDatePicker
import kotlin.time.ExperimentalTime

/**
 * A state object that can be hoisted to observe the date picker state. See
 * [rememberAdaptiveDatePickerState].
 *
 * The state's [selectedDateMillis] will provide a timestamp that represents the _start_ of the day.
 *
 * @param initialSelectedDateMillis timestamp in _UTC_ milliseconds from the epoch that
 * represents an initial selection of a date. Provide a `null` to indicate no selection. Note
 * that the state's
 * [selectedDateMillis] will provide a timestamp that represents the _start_ of the day, which
 * may be different than the provided initialSelectedDateMillis.
 * @param initialDisplayedMonthMillis timestamp in _UTC_ milliseconds from the epoch that
 * represents an initial selection of a month to be displayed to the user. In case `null` is
 * provided, the displayed month would be the current one.
 * @param yearRange an [IntRange] that holds the year range that the date picker will be limited
 * to
 * @param initialMaterialDisplayMode an initial [DisplayMode] that this state will hold
 * @see rememberAdaptiveDatePickerState
 * @throws [IllegalArgumentException] if the initial selected date or displayed month represent
 * a year that is out of the year range.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Stable
actual class AdaptiveDatePickerState actual constructor(
    val initialSelectedDateMillis: Long?,
    val initialDisplayedMonthMillis: Long?,
    val yearRange: IntRange,
    val initialMaterialDisplayMode: DisplayMode,
    val initialUIKitDisplayMode: UIKitDisplayMode,
    val minDateMillis: LocalDate?,
    val maxDateMillis: LocalDate?,
) {
    @OptIn(ExperimentalTime::class)
    private fun LocalDate.toEpochMillis(): Long {
        val startOfDayUtc = this.atStartOfDayIn(TimeZone.UTC)   // LocalDateTime
        return startOfDayUtc.toEpochMilliseconds()
    }

    fun applyMinMax(datePicker: UIDatePicker) {
        minDateMillis?.let {
            // NSDate يقبل ثواني، لذلك نقسم على 1000
            datePicker.minimumDate =
                NSDate.dateWithTimeIntervalSince1970(it.toEpochMillis() / 1000.0)
        }

        maxDateMillis?.let {
            datePicker.maximumDate =
                NSDate.dateWithTimeIntervalSince1970(it.toEpochMillis() / 1000.0)
        }

        // تعيين التاريخ الافتراضي إذا موجود
        selectedDateMillis?.let {
            datePicker.date = NSDate.dateWithTimeIntervalSince1970(it / 1000.0)
        }
    }


    actual var selectedDateMillis by mutableStateOf(
        initialSelectedDateMillis?.let { KotlinxDatetimeCalendarModel().getCanonicalDate(it) }?.utcTimeMillis
    )

    /**
     * A mutable state of [DisplayMode] that represents the current display mode of the UI
     * (i.e. picker or input).
     */
    actual var displayMode: DisplayMode = initialMaterialDisplayMode

    internal actual companion object {
        /**
         * The default [Saver] implementation for [DatePickerState].
         */
        actual fun Saver(): Saver<AdaptiveDatePickerState, *> =
            Saver(
                save = {
                    listOf(
                        it.selectedDateMillis,
                        it.yearRange.first,
                        it.yearRange.last,
                        it.displayMode.value,
                        it.initialUIKitDisplayMode.value,
                    )
                },
                restore = { value ->
                    AdaptiveDatePickerState(
                        initialSelectedDateMillis = value[0] as Long?,
                        initialDisplayedMonthMillis = value[0] as Long?,
                        yearRange = IntRange(value[1] as Int, value[2] as Int),
                        initialMaterialDisplayMode = displayModeFromValue(value[3] as Int),
                        initialUIKitDisplayMode = uiKitDisplayModeFromValue(value[4] as Int),
                    )
                },
            )
    }
}
