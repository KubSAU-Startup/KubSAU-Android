package com.example.diploma.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.diploma.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    onError: (String) -> Unit,
    onLogOut: () -> Unit,
    openChangeUrl: () -> Unit,
    viewModel: ProfileViewModel = koinViewModel<ProfileViewModelImpl>()
) {
    val screenState by viewModel.screenState.collectAsState()

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
