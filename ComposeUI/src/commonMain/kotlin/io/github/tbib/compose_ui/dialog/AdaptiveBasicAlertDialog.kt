package io.github.tbib.compose_ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import io.github.tbib.compose_ui.dialog.utils.AlertDialogIosProperties
import io.github.tbib.compose_ui.dialog.utils.rememberAlertDialogIosProperties

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
expect fun AdaptiveBasicAlertDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,

    iosProperties: AlertDialogIosProperties = rememberAlertDialogIosProperties(),

    properties: DialogProperties = DialogProperties(),

    materialContent: @Composable () -> Unit,
)