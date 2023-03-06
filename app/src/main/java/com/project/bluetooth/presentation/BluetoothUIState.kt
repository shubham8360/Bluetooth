package com.project.bluetooth.presentation

import com.project.bluetooth.domain.chat.BluetoothDeviceDomain

data class BluetoothUIState(
        val scannedDevices: List<BluetoothDeviceDomain> = emptyList(),
        val pairedDevices: List<BluetoothDeviceDomain> = emptyList()
)