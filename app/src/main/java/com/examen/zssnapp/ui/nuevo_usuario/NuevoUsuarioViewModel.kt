package com.examen.zssnapp.ui.nuevo_usuario

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examen.zssnapp.Model.Request.InventarioRequest
import com.examen.zssnapp.Model.Request.SobrevientesRequest
import com.examen.zssnapp.Model.Response.ComunResponse
import com.examen.zssnapp.Model.Response.SobrevivientesResponse
import com.examen.zssnapp.Repository.InventarioRepository
import com.examen.zssnapp.Repository.SobrevivientesRepository
import kotlinx.coroutines.*

class NuevoUsuarioViewModel(
    private val sobrevivientesRepository: SobrevivientesRepository,
    private val inventarioRepository: InventarioRepository
) : ViewModel() {
    val sobrevivientesadd = MutableLiveData<SobrevivientesResponse>()
    val inventario = MutableLiveData<ComunResponse>()
    var job: Job? = null
    var lista: ArrayList<String> = arrayListOf()


    fun addSobrevientes(request: SobrevientesRequest): MutableLiveData<SobrevivientesResponse> {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = sobrevivientesRepository.addSobrevientes(request)
            withContext(Dispatchers.Main) {
                if (response != null) {
                    sobrevivientesadd.postValue(response!!)
                }
            }
        }
        return sobrevivientesadd
    }

    fun addInventario(request: InventarioRequest): MutableLiveData<ComunResponse> {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = inventarioRepository.addInventario(request)
            withContext(Dispatchers.Main) {
                if (response != null) {
                    inventario.postValue(response!!)
                }
            }
        }
        return inventario
    }

    fun getGeneros(): ArrayList<String> {
        lista.clear()
        lista.add("Hombre")
        lista.add("Mujer")
        lista.add("Sin definir")
        return lista
    }

}