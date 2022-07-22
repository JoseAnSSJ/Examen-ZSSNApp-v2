package com.examen.zssnapp.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examen.zssnapp.Model.Response.SobrevivientesResponse
import com.examen.zssnapp.Repository.SobrevivientesRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*

class MainViewModel(private val sobrevivientesRepository: SobrevivientesRepository) : ViewModel() {
    val sobrevivientesList = MutableLiveData<List<SobrevivientesResponse>?>()
    var job: Job? = null


    fun getSobrevieintes(): MutableLiveData<List<SobrevivientesResponse>?> {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = sobrevivientesRepository.getSobrevientes()
            withContext(Dispatchers.Main) {
                if (response != null) {
                    sobrevivientesList.postValue(response)
                }
            }
        }
        return sobrevivientesList
    }
}