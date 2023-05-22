package com.example.barcodemvvm

import android.graphics.ImageFormat
import androidx.camera.core.ImageProxy
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer

import com.google.zxing.PlanarYUVLuminanceSource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.ByteBuffer

class BarcodeModel {
    // Example function for interacting with an external API to retrieve product information
    suspend fun getProductInfo(barcode: String) {

    }

    //TODO: Database and Datahandling
}


class MyViewModel : ViewModel() {
    private val supportedImageFormats = listOf(
        ImageFormat.YUV_420_888,
        ImageFormat.YUV_422_888,
        ImageFormat.YUV_444_888
    )

    val scannedBarcode: MutableLiveData<String> = MutableLiveData()
    val barcodeScanError: MutableLiveData<String> = MutableLiveData()
    val scanningInProgress: MutableLiveData<Boolean> = MutableLiveData(false)

    fun startScanning(image: ImageProxy) {
        if (image.format in supportedImageFormats) {
            // Bilddaten als Bytes
            val bytes = image.planes.first().buffer.toByteArray()

            // Coroutine für Imagehandling
            viewModelScope.launch {
                // Backround Prozess
                val result = withContext(Dispatchers.Default) {
                    scanBarcode(bytes, image.width, image.height)
                }

                // UI Update
                if (result != null) {
                    onBarcodeScanned(result.text)
                } else {
                    onScanError("No barcode found.")
                }

                // Schließen
                image.close()
            }
        }
    }

    private fun scanBarcode(bytes: ByteArray, width: Int, height: Int): Result? {
        val source = PlanarYUVLuminanceSource(
            bytes,
            width,
            height,
            0,
            0,
            width,
            height,
            false
        )
        val binaryBmp = BinaryBitmap(HybridBinarizer(source))

        return try {
            val reader = MultiFormatReader().apply {
                setHints(
                    mapOf(
                        DecodeHintType.POSSIBLE_FORMATS to arrayListOf(
                            BarcodeFormat.QR_CODE,
                            BarcodeFormat.UPC_A,
                            BarcodeFormat.UPC_E,
                            BarcodeFormat.UPC_EAN_EXTENSION,
                            BarcodeFormat.EAN_13,
                            BarcodeFormat.EAN_8
                        )
                    )
                )
            }
            reader.decode(binaryBmp)
        } catch (e: Exception) {
            null
        }
    }

    private fun onBarcodeScanned(barcode: String) {
        // Im Fall das es klappt
        scannedBarcode.postValue(barcode)
    }

    private fun onScanError(errorMessage: String) {
        // Im Fall eines Errors
        barcodeScanError.postValue(errorMessage)
    }

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        return ByteArray(remaining()).also {
            get(it)
        }
    }
}
