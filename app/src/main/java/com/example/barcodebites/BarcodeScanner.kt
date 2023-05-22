package com.example.barcodebites

import android.graphics.ImageFormat
import androidx.camera.core.ImageAnalysis
import com.google.zxing.*
/*import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource*/
import com.google.zxing.common.HybridBinarizer
import java.nio.ByteBuffer
import androidx.camera.core.ImageProxy

//TODO Kommentare ergänzen!!!
class BarcodeScanner(
    private val onBarcodeScanned: (String) -> Unit
): ImageAnalysis.Analyzer {

    private val supportedImageFormats = listOf(
        ImageFormat.YUV_420_888,
        ImageFormat.YUV_422_888,
        ImageFormat.YUV_444_888,
    )

    override fun analyze(image: ImageProxy) {
        if(image.format in supportedImageFormats) {
            //TODO image.imageInfo.rotationDegree
            //wahrscheinlich für lichtverhältnisse, aus library
            //creates binary bitmap -> enthält infos des barcodes
            val bytes = image.planes.first().buffer.toByteArray()
            val source = PlanarYUVLuminanceSource(
                bytes,
                image.width,
                image.height,
                0,
                0,
                image.width,
                image.height,
                false
            )
            val binaryBmp = BinaryBitmap(HybridBinarizer(source))
            /*TODO Barcode schwieriger als QRCode, muss horizontal sein, evtl in landscape mode
              muss erst image drehen, wahrechilich über image.imageInfo.rotationDegree -> berechnung um horizontal zu drehen */
            try {
                val result = MultiFormatReader().apply {
                    setHints(
                        mapOf(
                            DecodeHintType.POSSIBLE_FORMATS to arrayListOf(
                                BarcodeFormat.QR_CODE,
                                //USA
                                BarcodeFormat.UPC_A,
                                BarcodeFormat.UPC_E,
                                BarcodeFormat.UPC_EAN_EXTENSION,
                                //Deutschland
                                BarcodeFormat.EAN_13,
                                BarcodeFormat.EAN_8
                            )
                        )
                    )
                }.decode(binaryBmp)
                onBarcodeScanned(result.text)/*TODO foodfacts API ansprechen, entsprechendes lebensmittel suchen und ausgeben*/
            } catch(e: Exception) {
                e.printStackTrace()
            } finally {
                image.close()
            }
        }
    }

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        return ByteArray(remaining()).also {
            get(it)
        }
    }
}