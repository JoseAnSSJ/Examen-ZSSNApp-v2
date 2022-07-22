package com.examen.zssnapp.Services

import com.examen.zssnapp.Model.Response.SobrevivientesResponse
import com.examen.zssnapp.Model.Request.SobrevientesRequest
import com.examen.zssnapp.Model.Response.ComunResponse
import retrofit2.http.*

interface SobrevivienteServices {
    @GET("sobrevivientes/getSobrevivientes")
    suspend fun getSobrevientes(): List<SobrevivientesResponse>

    @POST("sobrevivientes/addSobreviviente")
    suspend fun addSobrevientes(@Body request: SobrevientesRequest): SobrevivientesResponse

    @PUT("sobrevivientes/updateUltimaLocacion")
    suspend fun updateUltimaLocacion(
        @Query("id_sobreviviente") idSobreviviente: String, @Query("latitud") latitud: String,
        @Query("longitud") longitud: String
    ): ComunResponse

    @GET("sobrevivientes/getSobrevivientesNoInfectados")
    suspend fun getSobevivientesNoInfectados(): List<SobrevivientesResponse>

    @PUT("sobrevivientes/updateInfectado")
    suspend fun updateInfectado(@Query("id_sobrevieinte") idSobreviviente: String): ComunResponse

    @GET("sobrevivientes/getPorcentajeInfectados")
    suspend fun getPorcentajeInfectados(): ComunResponse

    @GET("sobrevivientes/getPorcentajeBien")
    suspend fun getPorcentajeBien(): ComunResponse

    @GET("sobrevivientes/validaInfectado/")
    suspend fun validaInfectado(@Query("id_objeto") idSobreviviente: Int): Int


}