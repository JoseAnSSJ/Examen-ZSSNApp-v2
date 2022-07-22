package com.examen.zssnapp.ui.nuevo_usuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examen.zssnapp.Repository.InventarioRepository
import com.examen.zssnapp.Repository.SobrevivientesRepository
import com.examen.zssnapp.ui.perfil.PerfilViewModel

class NuevoUsuarioFactory constructor(
    private val repository: SobrevivientesRepository,
    private val inventarioRepository: InventarioRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NuevoUsuarioViewModel::class.java!!)) {
            NuevoUsuarioViewModel(this.repository, inventarioRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}