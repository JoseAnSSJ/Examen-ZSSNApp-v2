package com.examen.zssnapp.ui.mercado

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.examen.zssnapp.R
import com.examen.zssnapp.Repository.InventarioRepository
import com.examen.zssnapp.Repository.ReporteInfectadoRepository
import com.examen.zssnapp.Repository.SobrevivientesRepository
import com.examen.zssnapp.ui.perfil.PerfilFragment
import com.examen.zssnapp.ui.reportar.ReportarFactory
import com.google.gson.Gson

class MercadoFragment : Fragment() {

    private lateinit var binding: MercadoFragment
    private lateinit var viewModel: MercadoViewModel
    var gson: Gson? = null


    private var select: String = ""
    private var nombre: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mercado, container, false)
        iniciar(view)

        return view
    }


    fun iniciar(view: View) {
        setViewModel()
        cargaDatos(view)
        setListener(view)
    }

    private fun cargaDatos(view: View) {
        viewModel.getSobrevieintes().observe(viewLifecycleOwner) {
            var lista: ArrayList<String> = arrayListOf()
            for (item in it!!) {
                item.nombreSobreviviente?.let { it1 -> lista.add(it1) }
            }
            setSpinner(view, lista)
        }
    }

    private fun setListener(viewPadre: View) {
        viewPadre.findViewById<AutoCompleteTextView>(R.id.spinnerSobrevieintes)
            .setOnItemClickListener { parent, view, position, id ->
                select = viewModel.sobrevivientesList.value?.get(position)?.idSobreviviente ?: ""
                nombre = viewModel.sobrevivientesList.value?.get(position)?.nombreSobreviviente ?:""
                viewModel.getInventarioById(select).observe(viewLifecycleOwner){
                    viewPadre.findViewById<CardView>(R.id.cardInventarioMercado).visibility = View.VISIBLE
                    for (item in it!!) {
                        when (item.descripcion) {
                            "Agua" -> {
                                viewPadre.findViewById<TextView>(R.id.txvAgua).text =
                                    "$nombre tiene ${item.cantidad} de agua"
                            }
                            "Comida" -> {
                                viewPadre.findViewById<TextView>(R.id.txvComida).text =
                                    "$nombre tiene ${item.cantidad} de comida"
                            }
                            "Medicamentos" -> {
                                viewPadre.findViewById<TextView>(R.id.txvMedicina).text =
                                    "$nombre tiene ${item.cantidad} de medicamentos"
                            }
                            "Municiones" -> {
                                viewPadre.findViewById<TextView>(R.id.txvMuniciones).text =
                                    "$nombre tiene ${item.cantidad} de municines"
                            }
                        }
                    }
                }
            }

    }

    private fun setSpinner(view: View, list: List<String>) {
        val adapter = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                list
            )
        }
        view.findViewById<AutoCompleteTextView>(R.id.spinnerSobrevieintes).setAdapter(adapter)

    }


    private fun setViewModel() {
        val repository = SobrevivientesRepository()
        val inventarioRepository = InventarioRepository()
        val mainFactory = MercadoFactory(repository, inventarioRepository)
        viewModel = ViewModelProvider(this, mainFactory).get(MercadoViewModel::class.java)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            PerfilFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

}