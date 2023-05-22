package com.example.barcodebites

import android.Manifest
import android.content.pm.PackageManager
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.camera.core.Preview

@Composable
fun RunScanner() {

        var code by remember {
            mutableStateOf("")
        }
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        val cameraProviderFuture = remember {
            ProcessCameraProvider.getInstance(context)
        }

        //TODO siehe camera permission vid
        var hasCameraPermission by remember {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            )
        }
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { granted -> hasCameraPermission = granted }
        )
        LaunchedEffect(key1 = true) {
            launcher.launch(Manifest.permission.CAMERA)
        }

        Box() {
            Box(
                modifier = Modifier.height(300.dp)
                    .width(300.dp)
                    .align(Alignment.Center)
            ) {
                if (hasCameraPermission) {
                    AndroidView(
                        factory = { context ->
                            val previewView = PreviewView(context)
                            //load camera image
                            val preview = Preview.Builder().build()
                            val selector = CameraSelector.Builder()
                                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                .build()
                            preview.setSurfaceProvider(previewView.surfaceProvider)
                            /*val imageAnalysis = ImageAnalysis.Builder()
                         .setTargetResolution(
                             Size(
                             //previewView.width,
                             //previewView.height
                             700,
                             500
                         )
                         )*/
                            val imageAnalysis = ImageAnalysis.Builder()
                                .setTargetResolution(
                                    Size(
                                        700,
                                        500
                                    )
                                )
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build()
                            /*analyse fun für jeden frame der camera,
                    wenn code langsamer als ankommende frames
                    */
                            imageAnalysis.setAnalyzer(
                                ContextCompat.getMainExecutor(context),
                                BarcodeScanner { result -> code = result }
                                //code = state in compose
                            )
                            try {
                                cameraProviderFuture.get().bindToLifecycle/*of composable*/(
                                    lifecycleOwner,
                                    selector,
                                    preview,
                                    imageAnalysis
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            previewView
                        },
                        //modifier = Modifier.weight(1f)
                    )

                    //TODO Ergebnis Scann erst auf nächster page
                    /*
                    Text(
                        text = code,//TODO ändern zu aufruf info von DB
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp)
                    )*/
                }
            }
        }
    }
