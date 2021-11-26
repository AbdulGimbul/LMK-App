package com.abdl.mylmk_app.ui.ngaji.hafalan

import androidx.lifecycle.ViewModel
import com.abdl.mylmk_app.data.source.local.room.LmkDao
import com.abdl.mylmk_app.di.ApplicationScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAllCompletedViewModel @Inject constructor(
    private val lmkDao: LmkDao,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {

    fun onConfirmClick() = applicationScope.launch {
        lmkDao.deleteCompletedHafalan()
    }
}