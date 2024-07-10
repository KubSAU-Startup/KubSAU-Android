package com.kubsau.regrab.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kubsau.regrab.R
import com.kubsau.regrab.common.SettingsController
import com.kubsau.regrab.network.ErrorDomain
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    onError: (ErrorDomain) -> Unit,
    onLogOut: () -> Unit,
    openChangeUrl: () -> Unit,
    viewModel: ProfileViewModel = koinViewModel<ProfileViewModelImpl>()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val greenThemeEnabled by SettingsController.greenThemeEnabled.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.round_face_24),
                contentDescription = null,
                modifier = Modifier.size(164.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = screenState.fullName,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(.9f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = buildString {
                    append(stringResource(id = R.string.department))
                    append(" ")
                    append(screenState.department.lowercase())
                },
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.use_green_theme))
                Spacer(modifier = Modifier.width(16.dp))
                Switch(
                    checked = greenThemeEnabled,
                    onCheckedChange = SettingsController::updateGreenThemeEnabled,
                )
            }


            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = openChangeUrl) {
                Text(text = stringResource(id = R.string.action_change_url))
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onLogOut,
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .height(56.dp)
            ) {
                Text(text = stringResource(id = R.string.logout))
            }
        }
    }
}
