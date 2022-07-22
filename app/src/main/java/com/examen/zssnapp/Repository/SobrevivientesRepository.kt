package com.examen.zssnapp.Repository

import com.examen.zssnapp.Model.Request.SobrevientesRequest
import com.examen.zssnapp.Services.SobrevivienteInjector
import com.examen.zssnapp.Services.SobrevivienteServices

class SobrevivientesRepository(private val sobrevivienteService: SobrevivienteServices? = SobrevivienteInjector.injectDoggoApiService()) {
    suspend fun getSobrevientes() = sobrevivienteService?.getSobrevientes()

    suspend fun addSobrevientes(request: SobrevientesRequest) =
        sobrevivienteService?.addSobrevientes(request)

    suspend fun updateUltimaLocacion(idSobreviviente: String, latitud: String, longitud: String) =
        sobrevivienteService?.updateUltimaLocacion(idSobreviviente, latitud, longitud)

    suspend fun getSobevivientesNoInfectados() =
        sobrevivienteService?.getSobevivientesNoInfectados()

    suspend fun updateInfectado(idSobreviviente: String) =
        sobrevivienteService?.updateInfectado(idSobreviviente)

    suspend fun getPorcentajeInfectados() = sobrevivienteService?.getPorcentajeInfectados()

    suspend fun getPorcentajeBien() = sobrevivienteService?.getPorcentajeBien()

    suspend fun validaInfectado(idSobreviviente: Int) =
        sobrevivienteService?.validaInfectado(idSobreviviente)

}