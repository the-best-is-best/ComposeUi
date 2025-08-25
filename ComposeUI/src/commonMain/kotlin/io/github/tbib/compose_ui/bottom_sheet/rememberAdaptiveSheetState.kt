package io.github.tbib.compose_ui.bottom_sheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import kotlinx.coroutines.CancellationException

private class SheetValueHolder @OptIn(ExperimentalMaterial3Api::class) constructor(
    var value: SheetValue
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberAdaptiveSheetState(
    skipPartiallyExpanded: Boolean? = null,
    confirmValueChange: (SheetValue) -> Boolean = { true },
): AdaptiveSheetState {
    val density = LocalDensity.current

    val sheetValueHolder = remember {
        SheetValueHolder(SheetValue.Hidden)
    }

    val state = rememberSaveable(
        skipPartiallyExpanded,
        confirmValueChange,
        density,
        sheetValueHolder,
        saver = AdaptiveSheetState.Saver(
            skipPartiallyExpanded = skipPartiallyExpanded,
            confirmValueChange = confirmValueChange,
            density = density,
        )
    ) {
        if (skipPartiallyExpanded == true && sheetValueHolder.value == SheetValue.PartiallyExpanded)
            sheetValueHolder.value = SheetValue.Expanded

        AdaptiveSheetState(
            skipPartiallyExpanded,
            density,
            sheetValueHolder.value,
            confirmValueChange
        )
    }


    LaunchedEffect(state) {
        snapshotFlow { state.currentValue }
            .collect {
                sheetValueHolder.value = it
            }
    }

    return state
}

@OptIn(ExperimentalMaterial3Api::class)
@Stable
expect class AdaptiveSheetState(
    skipPartiallyExpanded: Boolean? = null,
    density: Density,
    initialValue: SheetValue = SheetValue.Hidden,
    confirmValueChange: (SheetValue) -> Boolean = { true },
    skipHiddenState: Boolean = false,
) {
    val currentValue: SheetValue
    val isVisible: Boolean

    /**
     * Expand the bottom sheet with animation and suspend until it is [PartiallyExpanded] if defined
     * else [Expanded].
     * @throws [CancellationException] if the animation is interrupted
     */
    suspend fun show()

    /**
     * Hide the bottom sheet with animation and suspend until it is fully hidden or animation has
     * been cancelled.
     * @throws [CancellationException] if the animation is interrupted
     */
    suspend fun hide()

    companion object {
        /**
         * The default [Saver] implementation for [AdaptiveSheetState].
         */
        fun Saver(
            skipPartiallyExpanded: Boolean? = null,
            confirmValueChange: (SheetValue) -> Boolean,
            density: Density
        ): Saver<AdaptiveSheetState, SheetValue>
    }
}