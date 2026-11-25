package com.youarelaunched.challenge.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.youarelaunched.challenge.ui.common.VendorCard

@Composable
fun MainScreenUI(
    viewModel: MainScreenViewModel = viewModel(factory = MainScreenViewModel.Factory)
) {
    val uiState: MainScreenState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            when (val state = uiState) {
                is MainScreenState.Loading -> {
                    Box(Modifier.align(Alignment.Center)) {
                        CircularProgressIndicator()
                    }
                }

                is MainScreenState.Ready -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(
                            items = state.vendors,
                            key = { vendor -> vendor.id }
                        ) { vendor ->
                            VendorCard(
                                modifier = Modifier.padding(
                                    top = 24.dp,
                                    start = 16.dp,
                                    end = 16.dp
                                ),
                                vendor = vendor
                            )
                        }
                    }
                }

                is MainScreenState.Error -> {
                    Box(Modifier.align(Alignment.Center)) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(state.message)
                            Button(
                                content = { Text("Retry") },
                                onClick = { viewModel.onEvent(MainScreenEvent.RetryButtonPressed) }
                            )
                        }
                    }
                }
            }
        }
    }
}