package ch.rmy.android.http_shortcuts.activities.variables.editor.types

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ch.rmy.android.http_shortcuts.R
import ch.rmy.android.http_shortcuts.components.Spacing
import com.alorma.compose.settings.storage.base.rememberBooleanSettingState
import com.alorma.compose.settings.ui.SettingsCheckbox

@Composable
fun TimeTypeEditor(
    viewState: TimeTypeViewState,
    onViewStateChanged: (TimeTypeViewState) -> Unit,
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.MEDIUM),
        label = {
            Text(stringResource(R.string.label_variable_time_format))
        },
        value = viewState.timeFormat,
        onValueChange = {
            onViewStateChanged(
                viewState.copy(
                    timeFormat = it.take(50),
                    invalidFormat = false,
                )
            )
        },
        isError = viewState.invalidFormat,
        supportingText = if (viewState.invalidFormat) {
            {
                Text(stringResource(R.string.error_invalid_time_format))
            }
        } else null,
        singleLine = true,
    )

    SettingsCheckbox(
        title = {
            Text(stringResource(R.string.label_remember_value))
        },
        state = rememberBooleanSettingState(viewState.rememberValue),
        onCheckedChange = {
            onViewStateChanged(viewState.copy(rememberValue = it))
        },
    )
}