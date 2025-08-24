package io.github.tbib.compose_ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.uikit.LocalUIViewController
import androidx.compose.ui.window.DialogProperties
import io.github.tbib.compose_ui.dialog.utils.AlertDialogIosProperties

/**
 * Displays an adaptive alert dialog, which is a dialog that adapts to the platform it is running on.
 *
 * On iOS, it uses a native `UIAlertController` and on other platforms, it uses a material3 `BasicAlertDialog`.
 *
 * Non-iOS platforms is a shortcut for Android, Desktop, and Web.
 *
 * The styling parameters are only used for non-iOS platforms.
 *
 * @param onDismissRequest Lambda that is invoked when the dialog is dismissed.
 * @param modifier The modifier of the dialog.
 * @param iosProperties The iOS properties of the dialog.
 * This is used to configure the iOS dialog.
 * @param properties The properties of the dialog.
 * @param materialContent The composable that represents the confirm button for non-iOS platforms.
 * @see [AlertDialogIosProperties]
 */
@Composable
actual fun AdaptiveBasicAlertDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    iosProperties: AlertDialogIosProperties,
    properties: DialogProperties,
    materialContent: @Composable () -> Unit
) {
    val currentUIViewController = LocalUIViewController.current

    val alertDialogManager = remember(currentUIViewController) {
        AlertDialogManager(
            parentUIViewController = currentUIViewController,
            onDismissRequest = onDismissRequest,
            iosProperties = iosProperties,
            properties = properties,
        )
    }

    LaunchedEffect(onDismissRequest, iosProperties, properties) {
        alertDialogManager.onDismissRequest = onDismissRequest
        alertDialogManager.iosProperties = iosProperties
        alertDialogManager.properties = properties
    }

    DisposableEffect(Unit) {
        alertDialogManager.showAlertDialog()

        onDispose {
            alertDialogManager.dismiss()
        }
    }
}