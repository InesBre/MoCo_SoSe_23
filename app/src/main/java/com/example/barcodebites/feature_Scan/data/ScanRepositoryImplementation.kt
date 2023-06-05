package com.example.barcodebites.feature_Scan.data

import android.content.Context
import com.example.barcodebites.core.data.BaBiDb


class ScanRepositoryImplementation(context: Context){
    val dao = BaBiDb.getDatabase(context).scanDao()
}