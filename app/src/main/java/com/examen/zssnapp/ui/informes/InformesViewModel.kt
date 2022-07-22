package com.examen.zssnapp.ui.informes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examen.zssnapp.Model.Response.ComunResponse
import com.examen.zssnapp.Model.Response.InventarioResponse
import com.examen.zssnapp.Model.Response.SobrevivientesResponse
import com.examen.zssnapp.Repository.InventarioRepository
import com.examen.zssnapp.Repository.SobrevivientesRepository
import kotlinx.coroutines.*

class InformesViewModel(
    private val sobrevivientesRepository: SobrevivientesRepository,
    private val inventarioRepository: InventarioRepository
) : ViewModel() {
    val infectados = MutableLiveData<ComunResponse>()
    val noInfectados = MutableLiveData<ComunResponse>()
    val promedioResponse = MutableLiveData<List<InventarioResponse>>()
    val puntos = MutableLiveData<ComunResponse>()
    var job: Job? = null


    fun getPorcentajeInfectados(): MutableLiveData<ComunResponse> {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = sobrevivientesRepository.getPorcentajeInfectados()
            withContext(Dispatchers.Main) {
                if (response != null) {
                    infectados.postValue(response!!)
                }
            }
        }
        return infectados
    }

    fun getPromedio(): MutableLiveData<List<InventarioResponse>> {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = inventarioRepository.getPromedio()
            withContext(Dispatchers.Main) {
                if (response != null) {
                    promedioResponse.postValue(response!!)
                }
            }
        }
        return promedioResponse
    }

    fun getPorcentajeBien(): MutableLiveData<ComunResponse> {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = sobrevivientesRepository.getPorcentajeBien()
            withContext(Dispatchers.Main) {
                if (response != null) {
                    noInfectados.postValue(response!!)
                }
            }
        }
        return noInfectados
    }

    fun getPuntosPerdidios(): MutableLiveData<ComunResponse> {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = inventarioRepository.getPuntosPerdidios()
            withContext(Dispatchers.Main) {
                if (response != null) {
                    puntos.postValue(response!!)
                }
            }
        }
        return puntos
    }
}