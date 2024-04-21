package com.example.diploma.ui.screens.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.diploma.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreen(qrResult: String?, goToCamera: () -> Unit) {

    Surface {
        if (qrResult == null) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { goToCamera() }) {
                    Text(text = stringResource(id = R.string.scan_qr))
                }
            }

        } else {

            val viewModel = koinViewModel<RegistrationVM>()
            viewModel.fetchData(qrResult)

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = viewModel.disciplineDisplay)

                Text(text = viewModel.studentDisplay)

                if (viewModel.editable)
                    TextField(value = viewModel.workTitle, onValueChange = {
                        viewModel.workTitle = it
                    })

                Button(onClick = { viewModel.registration() }) {
                    Text(text = "Reg")
                }
            }

        }
    }

}