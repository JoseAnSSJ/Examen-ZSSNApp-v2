package com.examen.zssnapp.ui.reportar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examen.zssnapp.Repository.ReporteInfectadoRepository
import com.examen.zssnapp.Repository.SobrevivientesRepository
import com.examen.zssnapp.ui.perfil.PerfilViewModel

class ReportarFactory constructor(
    private val repository: SobrevivientesRepository,
    private val reporteInfectadoRepository: ReporteInfectadoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ReportarViewModel::class.java!!)) {
            ReportarViewModel(this.repository, reporteInfectadoRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}