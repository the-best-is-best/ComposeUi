package io.github.tbib.kadaptiveui.dialog.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

/**
 * @param title The title of the dialog
 * @param text The text of the dialog
 * @param animated Whether the dialog should be animated
 * @param style The style of the dialog
 * @param severity The severity of the dialog
 * @param actions The actions of the dialog
 * @param confirmButtonColor The color of the confirm button (iOS/Apple)
 * @param cancelButtonColor The color of the cancel button (iOS/Apple)
 */
@Composable
fun rememberAlertDialogIosProperties(
    title: String = "",
    text: String = "",
    animated: Boolean = true,
    style: AlertDialogIosStyle = AlertDialogIosStyle.Alert,
    severity: AlertDialogIosSeverity = AlertDialogIosSeverity.Default,
    actions: List<AlertDialogIosAction> = listOf(),
    confirmButtonColor: Color? = null,
    cancelButtonColor: Color? = null
): AlertDialogIosProperties {
    return remember(
        title,
        text,
        style,
        animated,
        severity,
        actions,
        confirmButtonColor,
        cancelButtonColor
    ) {
        AlertDialogIosProperties(
            title = title,
            text = text,
            animated = animated,
            style = style,
            severity = severity,
            actions = actions,
            confirmButtonColor = confirmButtonColor,
            cancelButtonColor = cancelButtonColor
        )
    }
}

/**
 * iOS Alert Dialog properties.
 */
data class AlertDialogIosProperties(
    val title: String = "",
    val text: String = "",
    val animated: Boolean = true,
    val style: AlertDialogIosStyle = AlertDialogIosStyle.Alert,
    val severity: AlertDialogIosSeverity = AlertDialogIosSeverity.Default,
    val actions: List<AlertDialogIosAction> = listOf(),
    val confirmButtonColor: Color? = null, // iOS confirm button color
    val cancelButtonColor: Color? = null   // iOS cancel button color
)
