package ch.rmy.android.http_shortcuts.activities.editor.executionsettings

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ch.rmy.android.http_shortcuts.R
import ch.rmy.android.http_shortcuts.components.ScreenScope
import ch.rmy.android.http_shortcuts.components.SimpleScaffold
import ch.rmy.android.http_shortcuts.components.bindViewModel

@Composable
fun ScreenScope.ExecutionSettingsScreen() {
    val (viewModel, state) = bindViewModel<ExecutionSettingsViewState, ExecutionSettingsViewModel>()

    BackHandler {
        viewModel.onBackPressed()
    }

    SimpleScaffold(
        viewState = state,
        title = stringResource(R.string.label_execution_settings),
    ) { viewState ->
        ExecutionSettingsContent(
            delay = viewState.delay,
            waitForConnection = viewState.waitForConnection,
            waitForConnectionOptionVisible = viewState.waitForConnectionOptionVisible,
            requireConfirmation = viewState.requireConfirmation,
            launcherShortcutOptionVisible = viewState.launcherShortcutOptionVisible,
            launcherShortcut = viewState.launcherShortcut,
            secondaryLauncherShortcut = viewState.secondaryLauncherShortcut,
            quickSettingsTileShortcutOptionVisible = viewState.quickSettingsTileShortcutOptionVisible,
            quickSettingsTileShortcut = viewState.quickSettingsTileShortcut,
            excludeFromHistory = viewState.excludeFromHistory,
            repetitionInterval = viewState.repetitionInterval,
            onLauncherShortcutChanged = viewModel::onLauncherShortcutChanged,
            onSecondaryLauncherShortcutChanged = viewModel::onSecondaryLauncherShortcutChanged,
            onQuickSettingsTileShortcutChanged = viewModel::onQuickSettingsTileShortcutChanged,
            onExcludeFromHistoryChanged = viewModel::onExcludeFromHistoryChanged,
            onRequireConfirmationChanged = viewModel::onRequireConfirmationChanged,
            onWaitForConnectionChanged = viewModel::onWaitForConnectionChanged,
            onDelayButtonClicked = viewModel::onDelayButtonClicked,
            onRepetitionIntervalChanged = viewModel::onRepetitionIntervalChanged,
        )
    }

    ExecutionSettingsDialogs(
        dialogState = state?.dialogState,
        onConfirmAppOverlay = viewModel::onAppOverlayDialogConfirmed,
        onConfirmDelay = viewModel::onDelayChanged,
        onDismissed = viewModel::onDismissDialog,
    )
}