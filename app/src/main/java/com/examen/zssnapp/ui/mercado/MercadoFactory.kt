package com.examen.zssnapp.ui.mercado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examen.zssnapp.Repository.InventarioRepository
import com.examen.zssnapp.Repository.SobrevivientesRepository
import com.examen.zssnapp.ui.nuevo_usuario.NuevoUsuarioViewModel

class MercadoFactory constructor(
    private val repository: SobrevivientesRepository,
    private val inventarioRepository: InventarioRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MercadoViewModel::class.java!!)) {
            MercadoViewModel(this.repository, inventarioRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}