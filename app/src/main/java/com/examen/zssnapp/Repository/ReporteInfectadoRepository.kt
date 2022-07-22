package com.examen.zssnapp.Repository

import com.examen.zssnapp.Model.Request.InfectadoRequest
import com.examen.zssnapp.Services.ReporteInfectadoService
import com.examen.zssnapp.Services.SobrevivienteInjector

class ReporteInfectadoRepository(private val service: ReporteInfectadoService? = SobrevivienteInjector.injectDoggoApiServiceReporteInfectado()) {
    suspend fun addSobrevientes(request: InfectadoRequest) = service?.addSobrevientes(request)
}