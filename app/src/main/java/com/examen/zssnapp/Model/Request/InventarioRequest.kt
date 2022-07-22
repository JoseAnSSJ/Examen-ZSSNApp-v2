package com.examen.zssnapp.Model.Request

import com.google.gson.annotations.SerializedName

data class InventarioRequest(

    @SerializedName("inventario")
    var inventario: ArrayList<InventarioItem>? = null,

)

data class InventarioItem(
    @SerializedName("id_sobreviviente")
    var idSobreviviente: Int? = null,
    @SerializedName("cantidad")
    val cantidad: Int? = null,
    @SerializedName("id_objeto")
    val idObjeto: Int? = null
)
