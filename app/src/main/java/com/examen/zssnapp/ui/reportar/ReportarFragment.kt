package com.examen.zssnapp.ui.reportar

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import com.examen.zssnapp.Model.Request.InfectadoRequest
import com.examen.zssnapp.R
import com.examen.zssnapp.Repository.ReporteInfectadoRepository
import com.examen.zssnapp.Repository.SobrevivientesRepository
import com.examen.zssnapp.databinding.FragmentPerfilnewBinding
import com.examen.zssnapp.databinding.ReportarFragmentBinding
import com.examen.zssnapp.ui.perfil.PerfilFactory
import com.examen.zssnapp.ui.perfil.PerfilFragment
import com.examen.zssnapp.ui.perfil.PerfilViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class ReportarFragment : Fragment() {

    private lateinit var binding: ReportarFragmentBinding
    private lateinit var viewModel: ReportarViewModel
    var gson: Gson? = null
    var sobrevieinteReporte: String = ""


    private var select: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sobrevieinteReporte = it.getString("idSobreviviente").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.reportar_fragment, container, false)
        iniciar(view)

        return view
    }


    fun iniciar(view: View) {
        setViewModel()
        cargaDatos(view)
        setListener(view)
    }

    private fun cargaDatos(view: View) {
        viewModel.getSobevivientesNoInfectados().observe(viewLifecycleOwner) {
            var lista: ArrayList<String> = arrayListOf()
            for (item in it!!) {
                item.nombreSobreviviente?.let { it1 -> lista.add(it1) }
            }
            setSpinner(view, lista)
        }
    }

    private fun setListener(view: View) {
        view.findViewById<AutoCompleteTextView>(R.id.spinnerSobrevieintes)
            .setOnItemClickListener { parent, view, position, id ->
                select = viewModel.lista.value?.get(position)?.idSobreviviente?.toInt() ?: 0

            }

        view.findViewById<AppCompatButton>(R.id.btnReportarInfectado).setOnClickListener {
            val request = InfectadoRequest(
                idSobrevivienteInfectado = select,
                idSobrevivienteInformante = sobrevieinteReporte.toInt()
            )
            viewModel.getSobrevieintes(request).observe(viewLifecycleOwner) {
                if (it.id_reporte != null) {
                    viewModel.validaInfectado(select).observe(viewLifecycleOwner) {
                        Toast.makeText(activity, "Reporte Existoso", Toast.LENGTH_LONG).show()
                        findNavController().popBackStack()
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
        binding = ReportarFragmentBinding.inflate(layoutInflater)
        val repository = SobrevivientesRepository()
        val reporteInfectadoRepository = ReporteInfectadoRepository()
        val mainFactory = ReportarFactory(repository, reporteInfectadoRepository)
        viewModel = ViewModelProvider(this, mainFactory).get(ReportarViewModel::class.java)
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