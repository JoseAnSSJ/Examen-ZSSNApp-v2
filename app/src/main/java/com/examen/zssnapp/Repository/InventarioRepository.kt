package com.examen.zssnapp.Repository

import com.examen.zssnapp.Model.Request.InventarioRequest
import com.examen.zssnapp.Services.InventarioServices
import com.examen.zssnapp.Services.SobrevivienteInjector
import com.examen.zssnapp.Services.SobrevivienteServices

class InventarioRepository(private val inventarioServices: InventarioServices? = SobrevivienteInjector.injectDoggoApiServiceInventario()) {
    suspend fun getPromedio() = inventarioServices?.getPromedio()
    suspend fun getPuntosPerdidios() = inventarioServices?.getPuntosPerdidios()
    suspend fun addInventario(request: InventarioRequest) = inventarioServices?.addInventario(request)

    suspend fun getInventarioById(idSobreviviente: String) =
        inventarioServices?.getInventarioById(idSobreviviente)
}