package com.examen.zssnapp.ui.reportar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examen.zssnapp.Model.Request.InfectadoRequest
import com.examen.zssnapp.Model.Response.ComunResponse
import com.examen.zssnapp.Model.Response.SobrevivientesResponse
import com.examen.zssnapp.Repository.ReporteInfectadoRepository
import com.examen.zssnapp.Repository.SobrevivientesRepository
import kotlinx.coroutines.*

class ReportarViewModel(
    private val sobrevivientesRepository: SobrevivientesRepository,
    private val reporteInfectadoRepository: ReporteInfectadoRepository
) : ViewModel() {
    val reportar = MutableLiveData<ComunResponse>()
    val valida = MutableLiveData<Int>()
    var job: Job? = null
    var lista = MutableLiveData<List<SobrevivientesResponse>?>()


    fun getSobrevieintes(request: InfectadoRequest): MutableLiveData<ComunResponse> {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = reporteInfectadoRepository.addSobrevientes(request)
            withContext(Dispatchers.Main) {
                if (response != null) {
                    reportar.postValue(response!!)
                }
            }
        }
        return reportar
    }

    fun getSobevivientesNoInfectados(): MutableLiveData<List<SobrevivientesResponse>?> {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = sobrevivientesRepository.getSobevivientesNoInfectados()
            withContext(Dispatchers.Main) {
                if (response != null) {
                    lista.postValue(response)
                }
            }
        }
        return lista
    }

    fun validaInfectado(idSobreviviente: Int): MutableLiveData<Int> {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = sobrevivientesRepository.validaInfectado(idSobreviviente)
            withContext(Dispatchers.Main) {
                if (response != null) {
                    valida.postValue(response!!)
                }
            }
        }
        return valida
    }
}