package com.examen.zssnapp.ui.informes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.examen.zssnapp.R
import com.examen.zssnapp.Repository.InventarioRepository
import com.examen.zssnapp.Repository.SobrevivientesRepository
import com.examen.zssnapp.databinding.FragmentReportenewBinding
import com.examen.zssnapp.ui.perfil.PerfilFragment
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class InformesFragment : Fragment() {

    private lateinit var binding: FragmentReportenewBinding
    private lateinit var viewModel: InformesViewModel
    var gson: Gson? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reportenew, container, false)
        binding = FragmentReportenewBinding.inflate(layoutInflater)
        iniciar(view)

        return view
    }


    fun iniciar(view: View) {
        setViewModel()
        cargaDatos(view)
    }

    private fun cargaDatos(view: View) {
        //se manda a llamar cada servicio que llena los reportes
        viewModel.getPorcentajeBien().observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.txvReporteBien).text =
                "Porcentaje de sobrevieintes bien: ${it.porcentaje}"
        }

        viewModel.getPorcentajeInfectados().observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.txvReporteInfectados).text =
                "Porcentaje de sobrevieintes infectados: ${it.porcentaje}"
        }

        viewModel.getPuntosPerdidios().observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.txvReportePuntos).text = it.mensaje
        }

        viewModel.getPromedio().observe(viewLifecycleOwner) {
            var mensaje = ""
            for (item in it) {
                mensaje = "$mensaje El promedio de ${item.descripcion} es ${item.promedio} \n"
            }
            view.findViewById<TextView>(R.id.txvReporteSuministros).text = mensaje
        }
    }


    private fun setViewModel() {
        val repository = SobrevivientesRepository()
        val repositoryInventario = InventarioRepository()
        val mainFactory = InformesFactory(repository, repositoryInventario)
        viewModel = ViewModelProvider(this, mainFactory).get(InformesViewModel::class.java)
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