package com.examen.zssnapp.Model.Request

import com.google.gson.annotations.SerializedName

data class InfectadoRequest(
    @SerializedName("id_sobreviviente_infectado")
    val idSobrevivienteInfectado: Int? = null,
    @SerializedName("id_sobreviviente_informante")
    val idSobrevivienteInformante: Int? = null,
)
