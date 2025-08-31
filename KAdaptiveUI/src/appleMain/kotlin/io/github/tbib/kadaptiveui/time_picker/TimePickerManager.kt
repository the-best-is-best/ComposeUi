package io.github.tbib.kadaptiveui.time_picker

import androidx.compose.runtime.mutableStateOf
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.useContents
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSCalendarUnitMinute
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.Foundation.NSLocale
import platform.Foundation.localeWithLocaleIdentifier
import platform.UIKit.UIControlEventValueChanged
import platform.UIKit.UIDatePicker
import platform.UIKit.UIDatePickerMode
import platform.UIKit.UIDatePickerStyle
import platform.darwin.NSObject
import platform.objc.sel_registerName

@OptIn(ExperimentalForeignApi::class)
class TimePickerManager internal constructor(
    datePicker: UIDatePicker,
    initialMinute: Int,
    initialHour: Int,
    is24Hour: Boolean,
    private val onHourChanged: (hour: Int) -> Unit,
    private val onMinuteChanged: (minute: Int) -> Unit,
) {
    private val datePickerDelegate = object : NSObject() {
        @ObjCAction
        fun onTimeChanged(sender: UIDatePicker) {
            val components = NSCalendar.currentCalendar.components(
                NSCalendarUnitHour or NSCalendarUnitMinute,
                sender.date
            )

            onHourChanged(components.hour.toInt())
            onMinuteChanged(components.minute.toInt())
        }
    }

    val datePickerWidth = mutableStateOf(0f)
    val datePickerHeight = mutableStateOf(0f)

    init {
        val dateComponents = NSDateComponents().apply {
            minute = initialMinute.toLong()
            hour = initialHour.toLong()
        }

        val date = NSCalendar.currentCalendar.dateFromComponents(dateComponents) ?: NSDate()

        datePicker.apply {
            this.date = date
            datePickerMode = UIDatePickerMode.UIDatePickerModeTime
            preferredDatePickerStyle = UIDatePickerStyle.UIDatePickerStyleWheels

            // Force 24-hour or 12-hour by changing locale
            locale = if (is24Hour) {
                NSLocale.localeWithLocaleIdentifier("en_GB") // 24-hour format
            } else {
                NSLocale.localeWithLocaleIdentifier("en_US_POSIX") // 12-hour format
            }

            // Add target using NSObject delegate
            addTarget(
                target = datePickerDelegate,
                action = sel_registerName("onTimeChanged:"),
                forControlEvents = UIControlEventValueChanged
            )
        }

        datePicker.frame.useContents {
            datePickerWidth.value = this.size.width.toFloat()
            datePickerHeight.value = this.size.height.toFloat()
        }
    }
}
