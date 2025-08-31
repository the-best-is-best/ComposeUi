package io.github.tbib.kadaptiveui.dialog


import androidx.compose.ui.window.DialogProperties
import io.github.tbib.kadaptiveui.dialog.utils.AlertDialogIosAction
import io.github.tbib.kadaptiveui.dialog.utils.AlertDialogIosActionStyle
import io.github.tbib.kadaptiveui.dialog.utils.AlertDialogIosProperties
import io.github.tbib.kadaptiveui.dialog.utils.AlertDialogIosSeverity
import io.github.tbib.kadaptiveui.dialog.utils.AlertDialogIosStyle
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyle
import platform.UIKit.UIAlertActionStyleCancel
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertActionStyleDestructive
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerSeverityCritical
import platform.UIKit.UIAlertControllerStyle
import platform.UIKit.UIAlertControllerStyleActionSheet
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UITapGestureRecognizer
import platform.UIKit.UIViewController
import platform.objc.sel_registerName

/**
 * Manages the [UIAlertController] and its actions.
 *
 * @param onDismissRequest Lambda that is invoked when the dialog is dismissed.
 * @param iosProperties The iOS properties of the dialog.
 * @param properties The properties of the dialog.
 * @param parentUIViewController The [UIViewController] that will present the dialog.
 * @constructor Creates an [AlertDialogManager] instance.
 * @see [UIAlertController]
 */
class AlertDialogManager internal constructor(
    private val parentUIViewController: UIViewController,
    internal var onDismissRequest: () -> Unit,
    internal var iosProperties: AlertDialogIosProperties,
    internal var properties: DialogProperties,
) {

    /**
     * Indicates whether the dialog is presented or not.
     */
    private var isPresented = false

    /**
     * Indicates whether the dialog is animating or not.
     */
    private var isAnimating = false

    /**
     * The ui view controller that is used to present the dialog.
     */
    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    private val dialogUIViewController: UIViewController by lazy {
        UIAlertController.alertControllerWithTitle(
            title = iosProperties.title,
            message = iosProperties.text,
            preferredStyle = iosProperties.style.toUIAlertControllerStyle()
        ).apply {
            iosProperties.actions.forEach { action ->
                val uiAlertAction = action.toUIAlertAction()
                uiAlertAction.enabled = action.enabled
                addAction(uiAlertAction)

                if (action.isPreferred) {
                    setPreferredAction(uiAlertAction)
                }
            }

            if (iosProperties.severity == AlertDialogIosSeverity.Critical)
                setSeverity(UIAlertControllerSeverityCritical)
        }
    }

    /**
     * Lambda that dismisses the dialog.
     */
    private val onDismissLambda: (() -> Unit) = {
        dialogUIViewController.dismissViewControllerAnimated(
            flag = true,
            completion = {
                isPresented = false
                isAnimating = false
                onDismissRequest()
            }
        )
    }

    /**
     * Pointer to the [dismiss] method.
     */
    @OptIn(ExperimentalForeignApi::class)
    private val dismissPointer = sel_registerName("dismiss")

    /**
     * Dismisses the dialog.
     */
    @OptIn(BetaInteropApi::class)
    @ObjCAction
    fun dismiss() {
        if (!isPresented || isAnimating) return
        isAnimating = true
        onDismissLambda.invoke()
    }

    /**
     * Shows the dialog.
     */
    @OptIn(ExperimentalForeignApi::class)
    fun showAlertDialog() {
        if (isPresented || isAnimating) return
        isAnimating = true

        parentUIViewController.presentViewController(
            viewControllerToPresent = dialogUIViewController,
            animated = iosProperties.animated,
            completion = {
                isPresented = true
                isAnimating = false

                if (properties.dismissOnClickOutside) {
                    dialogUIViewController.view.superview?.setUserInteractionEnabled(true)
                    dialogUIViewController.view.superview?.addGestureRecognizer(
                        UITapGestureRecognizer(
                            target = this,
                            action = dismissPointer
                        )
                    )
                }
            }
        )
    }

    companion object {
        private fun AlertDialogIosActionStyle.toUIAlertActionStyle(): UIAlertActionStyle =
            when (this) {
                AlertDialogIosActionStyle.Default ->
                    UIAlertActionStyleDefault

                AlertDialogIosActionStyle.Cancel ->
                    UIAlertActionStyleCancel

                AlertDialogIosActionStyle.Destructive ->
                    UIAlertActionStyleDestructive
            }

        fun AlertDialogIosStyle.toUIAlertControllerStyle(): UIAlertControllerStyle =
            when (this) {
                AlertDialogIosStyle.ActionSheet ->
                    UIAlertControllerStyleActionSheet

                AlertDialogIosStyle.Alert ->
                    UIAlertControllerStyleAlert
            }

        fun AlertDialogIosAction.toUIAlertAction(): UIAlertAction {
            return UIAlertAction.actionWithTitle(
                title = title,
                style = style.toUIAlertActionStyle(),
                handler = {
                    onClick()
                }
            )
        }
    }
}