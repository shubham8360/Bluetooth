package com.project.bluetooth.data.chat

import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.IntentFilter
import com.project.bluetooth.domain.chat.BluetoothController
import com.project.bluetooth.domain.chat.BluetoothDeviceDomain
import com.project.bluetooth.utils.PermissionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AndroidBluetoothController(private val context: Context) : BluetoothController {

    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }
    private val bluetoothAdapter by lazy {
        bluetoothManager.adapter
    }

    private val _scannedDevice = MutableStateFlow<List<BluetoothDeviceDomain>>(emptyList())
    private val _pairedDevice = MutableStateFlow<List<BluetoothDeviceDomain>>(emptyList())

    override val scannedDevice: StateFlow<List<BluetoothDeviceDomain>>
        get() = _scannedDevice.asStateFlow()

    override val pairedDevice: StateFlow<List<BluetoothDeviceDomain>>
        get() = _pairedDevice

    private val foundDeviceReceiver: FoundDeviceReceiver = FoundDeviceReceiver { device ->
        _scannedDevice.update { devices ->
            val newDevice = device.toBluetoothDeviceDomain()
            if (newDevice in devices) devices else devices + newDevice
        }

    }

    init {
        updatePairedDevices()
    }

    @SuppressLint("MissingPermission")
    override fun startDiscovery() {
        if (PermissionManager.hasPermission(context, android.Manifest.permission.BLUETOOTH_SCAN)) {
            context.registerReceiver(
                foundDeviceReceiver,
                IntentFilter(android.bluetooth.BluetoothDevice.ACTION_FOUND)
            )
            updatePairedDevices()
            bluetoothAdapter.startDiscovery()
        } else {
            return
        }
    }

    @SuppressLint("MissingPermission")
    private fun updatePairedDevices() {
        if (PermissionManager.hasPermission(
                context = context,
                android.Manifest.permission.BLUETOOTH_CONNECT
            )
        ) {
            bluetoothAdapter.bondedDevices.map {
                it.toBluetoothDeviceDomain()
            }.also { updatedList ->
                _pairedDevice.update { updatedList }
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun stopDiscovery() {
        if (PermissionManager.hasPermission(context, android.Manifest.permission.BLUETOOTH_SCAN)) {
            bluetoothAdapter.cancelDiscovery()
        } else {
            return
        }
    }

    override fun release() {
        context.unregisterReceiver(foundDeviceReceiver)
    }

}