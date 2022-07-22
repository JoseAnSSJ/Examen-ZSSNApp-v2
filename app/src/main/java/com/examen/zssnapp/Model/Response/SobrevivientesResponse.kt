package com.examen.zssnapp.Model.Response

import com.google.gson.annotations.SerializedName

data class SobrevivientesResponse(
    @SerializedName("id_sobreviviente")
    val idSobreviviente: String? = null,
    @SerializedName("nombre_sobreviviente")
    val nombreSobreviviente: String? = null,
    @SerializedName("edad")
    val edad: String? = null,
    @SerializedName("genero")
    val genero: String? = null,
    @SerializedName("latitud")
    var latitud: String? = null,
    @SerializedName("longitud")
    var longitud: String? = null,
    @SerializedName("es_infectado")
    val esInfectado: Boolean? = null
)
