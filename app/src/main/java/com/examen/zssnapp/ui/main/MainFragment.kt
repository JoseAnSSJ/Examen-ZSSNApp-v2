package com.examen.zssnapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.examen.zssnapp.Model.Response.SobrevivientesResponse
import com.examen.zssnapp.R
import com.examen.zssnapp.Repository.SobrevivientesRepository
import com.examen.zssnapp.adapter.SobrevivientesAdapter
import com.examen.zssnapp.databinding.MainFragmentBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * A fragment representing a list of Items.
 */
class MainFragment : Fragment() {
    //se especifica cuantas columnas debe de tener la lista
    private var columnCount = 4
    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel
    var gson: Gson? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        //se llama un mentodo que contiene toda la informacion de inicializacion
        iniciar(view)

        return view
    }


    fun iniciar(view: View) {
        setViewModel()
        observables(view)
    }


    private fun onListItemClick(item: SobrevivientesResponse) {
        //se manda el sobreviviente seleccionado por un bundle y se convierte el objeto a String para que sea mas facil
        val bundle = Bundle()
        val gson = Gson()
        val myJson = gson.toJson(item)
        bundle.putString("item", myJson)
        findNavController().navigate(R.id.mainFragmenttonavPerfil, bundle)
    }

    private fun observables(view: View) {
        try {
            //aqui se mandan a llamar los servicios y con los observales se maneja la respuesta
            viewModel.getSobrevieintes().observe(viewLifecycleOwner) {
                if (!it.isNullOrEmpty()) {
                    setReclycler(it, view)
                } else {
                    Toast.makeText(
                        context,
                        "Error al conseguir los sobrevivientes",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setReclycler(list: List<SobrevivientesResponse>, view: View) {
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = SobrevivientesAdapter(list) { it ->
                    onListItemClick(it)
                }
            }
        }
    }

    private fun setViewModel() {
        binding = MainFragmentBinding.inflate(layoutInflater)
        val repository = SobrevivientesRepository()
        val mainFactory = MainFactory(repository)
        viewModel = ViewModelProvider(this, mainFactory).get(MainViewModel::class.java)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

}