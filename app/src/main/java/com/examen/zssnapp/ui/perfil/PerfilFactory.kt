package com.examen.zssnapp.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examen.zssnapp.Model.Response.SobrevivientesResponse
import com.examen.zssnapp.Repository.InventarioRepository
import com.examen.zssnapp.Repository.SobrevivientesRepository
import com.examen.zssnapp.ui.main.MainViewModel

class PerfilFactory constructor(
    private val repository: SobrevivientesRepository,
    private val inventarioRepository: InventarioRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PerfilViewModel::class.java!!)) {
            PerfilViewModel(this.repository, inventarioRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}