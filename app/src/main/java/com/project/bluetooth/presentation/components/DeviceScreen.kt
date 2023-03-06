package com.project.bluetooth.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.project.bluetooth.domain.chat.BluetoothDeviceDomain
import com.project.bluetooth.presentation.BluetoothUIState
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun DeviceScreen(
    state: BluetoothUIState,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        BluetoothDeviceList(
            pairedDevice = state.pairedDevices,
            scannedDevice = state.scannedDevices,
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.sdp),
            horizontalArrangement = Arrangement.SpaceAround) {
            Button(onClick = onStartScan) {
                Text(text = "Start Scan")
            }

            Button(onClick = onStopScan) {
                Text(text = "Stop Scan")
            }
        }

    }
}

@Composable
fun BluetoothDeviceList(
    pairedDevice: List<BluetoothDeviceDomain>,
    scannedDevice: List<BluetoothDeviceDomain>,
    onClick: (BluetoothDeviceDomain) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            Text(
                text = "Paired Devices",
                fontWeight = FontWeight.Bold,
                fontSize = 24.ssp,
                modifier = Modifier.padding(12.sdp)
            )
        }
        items(pairedDevice) {
            Text(
                text = it.name ?: "No Name",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.sdp)
                    .clickable { onClick(it) },
            )

        }
        item {
            Text(
                text = "Scanned Devices",
                fontWeight = FontWeight.Bold,
                fontSize = 24.ssp,
                modifier = Modifier.padding(12.sdp)
            )
        }
        items(scannedDevice) {
            Text(
                text = it.name ?: "No Name",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.sdp)
                    .clickable { onClick(it) },
            )

        }

    }

}

@Preview
@Composable
fun DefaultDeviceScreenPreview() {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        DeviceScreen(state = BluetoothUIState(), onStartScan = { /*TODO*/ }){}
    }

}