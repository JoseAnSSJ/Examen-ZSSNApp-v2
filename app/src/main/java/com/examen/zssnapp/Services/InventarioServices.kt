package com.examen.zssnapp.Services

import com.examen.zssnapp.Model.Request.InventarioRequest
import com.examen.zssnapp.Model.Request.SobrevientesRequest
import com.examen.zssnapp.Model.Response.ComunResponse
import com.examen.zssnapp.Model.Response.InventarioResponse
import com.examen.zssnapp.Model.Response.SobrevivientesResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface InventarioServices {
    @GET("inventario/getPromedio")
    suspend fun getPromedio(): List<InventarioResponse>

    @GET("inventario/getPuntosPerdidios")
    suspend fun getPuntosPerdidios(): ComunResponse

    @POST("inventario/addInventarioList")
    suspend fun addInventario(@Body request: InventarioRequest): ComunResponse

    @GET("inventario/getInventarioById")
    suspend fun getInventarioById(@Query("id_sobreviviente") idSobreviviente: String): List<InventarioResponse>

}