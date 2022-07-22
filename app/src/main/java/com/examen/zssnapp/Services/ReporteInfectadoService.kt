package com.examen.zssnapp.Services

import com.examen.zssnapp.Model.Request.InfectadoRequest
import com.examen.zssnapp.Model.Request.SobrevientesRequest
import com.examen.zssnapp.Model.Response.ComunResponse
import com.examen.zssnapp.Model.Response.SobrevivientesResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ReporteInfectadoService {

    @POST("reporteInfectado/addInfectado")
    suspend fun addSobrevientes(@Body request: InfectadoRequest): ComunResponse
}