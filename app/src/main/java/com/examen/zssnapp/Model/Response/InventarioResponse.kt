package com.examen.zssnapp.Model.Response

import com.google.gson.annotations.SerializedName

data class InventarioResponse(
    @SerializedName("descripcion")
    val descripcion: String? = null,
    @SerializedName("promedio")
    val promedio: Double? = null,
    @SerializedName("cantidad")
    val cantidad: Int? = null,

    )
