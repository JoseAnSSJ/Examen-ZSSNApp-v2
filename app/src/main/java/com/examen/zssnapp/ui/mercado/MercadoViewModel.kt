package com.examen.zssnapp.ui.mercado

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examen.zssnapp.Model.Request.InfectadoRequest
import com.examen.zssnapp.Model.Response.ComunResponse
import com.examen.zssnapp.Model.Response.InventarioResponse
import com.examen.zssnapp.Model.Response.SobrevivientesResponse
import com.examen.zssnapp.Repository.InventarioRepository
import com.examen.zssnapp.Repository.ReporteInfectadoRepository
import com.examen.zssnapp.Repository.SobrevivientesRepository
import kotlinx.coroutines.*

class MercadoViewModel(
    private val sobrevivientesRepository: SobrevivientesRepository,
    private val inventarioRepository: InventarioRepository
) : ViewModel() {
    val inventarioList = MutableLiveData<List<InventarioResponse>?>()
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

    fun getInventarioById(idSobreviviente: String): MutableLiveData<List<InventarioResponse>?> {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = inventarioRepository.getInventarioById(idSobreviviente)
            withContext(Dispatchers.Main) {
                if (response != null) {
                    inventarioList.postValue(response)
                }
            }
        }
        return inventarioList
    }
}