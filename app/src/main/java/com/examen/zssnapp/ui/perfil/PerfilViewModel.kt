package com.examen.zssnapp.ui.perfil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examen.zssnapp.Model.Response.ComunResponse
import com.examen.zssnapp.Model.Response.InventarioResponse
import com.examen.zssnapp.Model.Response.SobrevivientesResponse
import com.examen.zssnapp.Repository.InventarioRepository
import com.examen.zssnapp.Repository.SobrevivientesRepository
import kotlinx.coroutines.*

class PerfilViewModel(
    private val sobrevivientesRepository: SobrevivientesRepository,
    private val inventarioRepository: InventarioRepository
) : ViewModel() {
    val inventarioList = MutableLiveData<List<InventarioResponse>?>()
    val cambioCoordenadas = MutableLiveData<ComunResponse>()
    var job: Job? = null


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

    fun updateCoordenadas(
        idSobreviviente: String,
        latitud: String,
        longitud: String
    ): MutableLiveData<ComunResponse> {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response =
                sobrevivientesRepository.updateUltimaLocacion(idSobreviviente, latitud, longitud)
            withContext(Dispatchers.Main) {
                if (response != null) {
                    cambioCoordenadas.postValue(response!!)
                }
            }
        }
        return cambioCoordenadas
    }
}