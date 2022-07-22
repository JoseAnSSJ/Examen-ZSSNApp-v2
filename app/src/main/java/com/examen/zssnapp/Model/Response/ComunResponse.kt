package com.examen.zssnapp.Model.Response

import com.google.gson.annotations.SerializedName

data class ComunResponse(
    @SerializedName("mensaje")
    val mensaje: String? = null,

    @SerializedName("porcentaje")
    val porcentaje: String? = null,

    @SerializedName("id_sobreviviente")
    val idSobreviviente: Int? = null,

    @SerializedName("id_reporte")
    val id_reporte: Int? = null

)