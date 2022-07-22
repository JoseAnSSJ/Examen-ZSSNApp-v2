package com.examen.zssnapp.ui.perfil

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.examen.zssnapp.Model.Response.SobrevivientesResponse
import com.examen.zssnapp.R
import com.examen.zssnapp.Repository.InventarioRepository
import com.examen.zssnapp.Repository.SobrevivientesRepository
import com.examen.zssnapp.databinding.FragmentPerfilnewBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.GsonBuilder


class PerfilFragment : Fragment() {

    private lateinit var binding: FragmentPerfilnewBinding
    private lateinit var viewModel: PerfilViewModel
    var gson: Gson? = Gson()
    private lateinit var sobrevivientesResponse: SobrevivientesResponse


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val item = it.getString("item")
            sobrevivientesResponse =
                gson?.fromJson<SobrevivientesResponse>(item, SobrevivientesResponse::class.java)!!

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfilnew, container, false)
        binding = FragmentPerfilnewBinding.inflate(layoutInflater)
        iniciar(view)

        return view
    }


    fun iniciar(view: View) {
        setViewModel()
        setListener(view)
        setCargaDatos(view)
        validaEstado(view)
    }

    private fun validaEstado(view: View) {
        //se valida que no sea Infectado para desabilitar los botones o habilitarlos segun sea el caso
        if (sobrevivientesResponse.esInfectado == true) {
            view.findViewById<AppCompatImageView>(R.id.imagenPerfil)
                .setImageResource(R.drawable.ic_baseline_screen_lock_portrait_24)
            view.findViewById<Button>(R.id.btnInformes).isEnabled = false
            view.findViewById<Button>(R.id.btnCambioCoordenas).isEnabled = false
            view.findViewById<Button>(R.id.btnMercado).isEnabled = false
            view.findViewById<Button>(R.id.btnReportar).isEnabled = false
            view.findViewById<Button>(R.id.btnSalir).isEnabled = false
            view.findViewById<FloatingActionButton>(R.id.fab).isEnabled = false
        } else {
            view.findViewById<Button>(R.id.btnInformes).isEnabled = true
            view.findViewById<Button>(R.id.btnCambioCoordenas).isEnabled = true
            view.findViewById<Button>(R.id.btnMercado).isEnabled = true
            view.findViewById<Button>(R.id.btnReportar).isEnabled = true
            view.findViewById<Button>(R.id.btnSalir).isEnabled = true
            view.findViewById<FloatingActionButton>(R.id.fab).isEnabled = true
        }
    }

    private fun setCargaDatos(view: View) {

        view.findViewById<TextView>(R.id.txvNombre).text =
            sobrevivientesResponse.nombreSobreviviente
        view.findViewById<TextView>(R.id.txvEdad).text = sobrevivientesResponse.edad
        view.findViewById<TextView>(R.id.txvGenero).text = sobrevivientesResponse.genero
        view.findViewById<TextView>(R.id.txvCoordenadas).text =
            "${sobrevivientesResponse.latitud}, ${sobrevivientesResponse.longitud}"

        try {
            viewModel.getInventarioById(sobrevivientesResponse.idSobreviviente.toString())
                .observe(viewLifecycleOwner) {
                    if (!it.isNullOrEmpty()) {
                        for (item in it) {
                            when (item.descripcion) {
                                "Agua" -> {
                                    view.findViewById<TextView>(R.id.txvAgua).text =
                                        "Agua tienes: ${item.cantidad}"
                                }
                                "Comida" -> {
                                    view.findViewById<TextView>(R.id.txvComida).text =
                                        "Comida tienes: ${item.cantidad}"
                                }
                                "Medicamentos" -> {
                                    view.findViewById<TextView>(R.id.txvMedicina).text =
                                        "Medicamentos tienes: ${item.cantidad}"
                                }
                                "Municiones" -> {
                                    view.findViewById<TextView>(R.id.txvMuniciones).text =
                                        "Municion tienes: ${item.cantidad}"
                                }
                            }
                        }
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun setListener(view: View) {
        view.findViewById<Button>(R.id.btnInformes).setOnClickListener {
            findNavController().navigate(R.id.perfilFragmenttoInfromes)
        }

        view.findViewById<Button>(R.id.btnCambioCoordenas).setOnClickListener {
            //se manda llamar un dialog personalizado para la modificacion de la ubicacion
            showDialog(
                sobrevivientesResponse.latitud,
                sobrevivientesResponse.longitud,
                sobrevivientesResponse.idSobreviviente!!,
                view
            ).show()
        }

        view.findViewById<Button>(R.id.btnMercado).setOnClickListener {
            findNavController().navigate(R.id.perfilFragmenttoMercado)
        }

        view.findViewById<Button>(R.id.btnReportar).setOnClickListener {
            val bundle = Bundle()
            bundle.putString("idSobreviviente", sobrevivientesResponse.idSobreviviente)
            findNavController().navigate(R.id.perfilFragmenttoReportar, bundle)
        }

        view.findViewById<Button>(R.id.btnSalir).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            findNavController().navigate(R.id.mainFragmenttonuevoUsuarioFragment1)
        }
    }

    private fun showDialog(
        latitud: String?,
        longitud: String?,
        idSobreviviente: String,
        viewParent: View
    ): AlertDialog {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(activity)
        val view = layoutInflater.inflate(R.layout.alert_dialog, null)
        var latitudNuevo = ""
        var longitudNuevo = ""

        view.findViewById<AppCompatEditText>(R.id.edtNuevaLatitud).setText(latitud)
        view.findViewById<AppCompatEditText>(R.id.edtNuevaLongitud).setText(longitud)

        view.findViewById<AppCompatButton>(R.id.btnCanelar)
            .setOnClickListener(View.OnClickListener {
                dialog?.dismiss()
            })

        view.findViewById<AppCompatButton>(R.id.btnAceptar)
            .setOnClickListener(View.OnClickListener {
                latitudNuevo =
                    view.findViewById<AppCompatEditText>(R.id.edtNuevaLatitud).text.toString()
                longitudNuevo =
                    view.findViewById<AppCompatEditText>(R.id.edtNuevaLongitud).text.toString()
                viewModel.updateCoordenadas(idSobreviviente, latitudNuevo, longitudNuevo)
                    .observe(viewLifecycleOwner) {
                        if (it.mensaje.equals("Ok")) {
                            viewParent.findViewById<TextView>(R.id.txvCoordenadas).text =
                                "${latitudNuevo} ,${longitudNuevo}"
                            sobrevivientesResponse.latitud = latitudNuevo
                            sobrevivientesResponse.longitud = longitudNuevo
                            Toast.makeText(activity, "Modificacion correcta", Toast.LENGTH_SHORT)
                                .show()
                            dialog?.dismiss()
                        }
                    }

            })

        builder.setView(view)
        dialog = builder.create()
        //dialog.show()

        return dialog
    }


    private fun setViewModel() {
        binding = FragmentPerfilnewBinding.inflate(layoutInflater)
        val repository = SobrevivientesRepository()
        val inventarioRepository = InventarioRepository()
        val mainFactory = PerfilFactory(repository, inventarioRepository)
        viewModel = ViewModelProvider(this, mainFactory).get(PerfilViewModel::class.java)
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