package com.examen.zssnapp.Model.Request

import com.google.gson.annotations.SerializedName

data class SobrevientesRequest(
    @SerializedName("id_sobreviviente")
    val idSobreviviente: String? = null,
    @SerializedName("nombre_sobreviviente")
    val nombreSobreviviente: String? = null,
    @SerializedName("edad")
    val edad: String? = null,
    @SerializedName("genero")
    val genero: String? = null,
    @SerializedName("latitud")
    val latitud: String? = null,
    @SerializedName("longitud")
    val longitud: String? = null,
    @SerializedName("es_infectado")
    val esInfectado: Boolean? = null
)
