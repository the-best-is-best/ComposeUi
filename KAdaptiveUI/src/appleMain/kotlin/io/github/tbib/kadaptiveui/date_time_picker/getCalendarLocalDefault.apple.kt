package io.github.tbib.kadaptiveui.date_time_picker

import androidx.compose.material3.CalendarLocale
import platform.Foundation.currentLocale


internal actual fun getCalendarLocalDefault(): CalendarLocale = CalendarLocale.currentLocale
