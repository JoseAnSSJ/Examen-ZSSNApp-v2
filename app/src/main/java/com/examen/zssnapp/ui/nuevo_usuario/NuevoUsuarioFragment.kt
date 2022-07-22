package com.examen.zssnapp.ui.nuevo_usuario

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import com.examen.zssnapp.Model.Request.InventarioItem
import com.examen.zssnapp.Model.Request.InventarioRequest
import com.examen.zssnapp.Model.Request.SobrevientesRequest
import com.examen.zssnapp.R
import com.examen.zssnapp.Repository.InventarioRepository
import com.examen.zssnapp.Repository.SobrevivientesRepository
import com.examen.zssnapp.databinding.NuevoUsuarioFragmentBinding
import com.examen.zssnapp.ui.perfil.PerfilFragment
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class NuevoUsuarioFragment : Fragment() {

    private var select: String = ""
    private var idSobre: Int = 0
    private lateinit var binding: NuevoUsuarioFragmentBinding
    private lateinit var viewModel: NuevoUsuarioViewModel
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
        val view = inflater.inflate(R.layout.nuevo_usuario_fragment, container, false)
        iniciar(view)

        return view
    }


    private fun iniciar(view: View) {
        setViewModel()
        setListener(view)
        setCargaDatos(view)
    }

    private fun setCargaDatos(view: View) {
        val adapter = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                viewModel.getGeneros()
            )
        }
        view.findViewById<AutoCompleteTextView>(R.id.spinnerGenero).setAdapter(adapter)
    }

    private fun setListener(view: View) {

        view.findViewById<AutoCompleteTextView>(R.id.spinnerGenero)
            .setOnItemClickListener { parent, view, position, id ->
                select = viewModel.lista[position] ?: ""

            }

        view.findViewById<AppCompatButton>(R.id.agregar).setOnClickListener {
            try {
                //se guardan los objetos que se van a mandar para crear un nuevo sobreviviente
                val sobrevientesRequest = SobrevientesRequest(
                    nombreSobreviviente = view.findViewById<AppCompatAutoCompleteTextView>(R.id.edtNombre).text.toString(),
                    edad = view.findViewById<AppCompatAutoCompleteTextView>(R.id.edtEdad).text.toString(),
                    latitud = view.findViewById<AppCompatAutoCompleteTextView>(R.id.edtLatitud).text.toString(),
                    longitud = view.findViewById<AppCompatAutoCompleteTextView>(R.id.edtLongitud).text.toString(),
                    genero = select,
                    esInfectado = false
                )
                //se crea una lista con el inventario para que sea mas facil cuando se mande


                var inventarioAgua = InventarioItem(
                    idSobreviviente = 6,
                    cantidad = view.findViewById<AppCompatAutoCompleteTextView>(R.id.edtAgua).text.toString()
                        .toInt(),
                    idObjeto = 1
                )
                var inventarioAComida = InventarioItem(
                    idSobreviviente = 6,
                    cantidad = view.findViewById<AppCompatAutoCompleteTextView>(R.id.edtComida).text.toString()
                        .toInt(),
                    idObjeto = 2
                )
                var inventarioMunicion = InventarioItem(
                    idSobreviviente = 6,
                    cantidad = view.findViewById<AppCompatAutoCompleteTextView>(R.id.edtMuniciones).text.toString()
                        .toInt(),
                    idObjeto = 4
                )
                var inventarioMedicamento = InventarioItem(
                    idSobreviviente = 6,
                    cantidad = view.findViewById<AppCompatAutoCompleteTextView>(R.id.edtMedicamento).text.toString()
                        .toInt(),
                    idObjeto = 3
                )
                //valida que todos los objetos esten llenos
                if (validar(
                        sobrevientesRequest,
                        inventarioAgua,
                        inventarioAComida,
                        inventarioMunicion,
                        inventarioMedicamento
                    )
                ) {

                    viewModel.addSobrevientes(sobrevientesRequest).observe(viewLifecycleOwner) {
                        var contador = 0
                        //se recorre la lista para llenarlo con el id_sobreviviente nuevo
                        val lista: ArrayList<InventarioItem> = arrayListOf()
                        inventarioAComida.idSobreviviente = it.idSobreviviente!!.toInt()
                        inventarioAgua.idSobreviviente = it.idSobreviviente!!.toInt()
                        inventarioMedicamento.idSobreviviente = it.idSobreviviente!!.toInt()
                        inventarioMunicion.idSobreviviente = it.idSobreviviente!!.toInt()
                        lista.add(inventarioAComida)
                        lista.add(inventarioAgua)
                        lista.add(inventarioMedicamento)
                        lista.add(inventarioMunicion)
                        val request = InventarioRequest(
                            inventario = lista
                        )

                        viewModel.addInventario(request)
                            .observe(viewLifecycleOwner) { respuesta ->
                                findNavController().popBackStack()
                            }

                    }
                } else {
                    Toast.makeText(activity, "Llene todos los campos por favor", Toast.LENGTH_LONG)
                        .show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }


        }

    }


    private fun validar(
        sobrevientesRequest: SobrevientesRequest,
        inventarioAgua: InventarioItem,
        inventarioAComida: InventarioItem,
        inventarioMunicion: InventarioItem,
        inventarioMedicamento: InventarioItem
    ): Boolean {
        var regresa = true

        if (sobrevientesRequest.edad.isNullOrEmpty() || sobrevientesRequest.genero.isNullOrEmpty() || sobrevientesRequest.latitud.isNullOrEmpty()
            || sobrevientesRequest.longitud.isNullOrEmpty() || sobrevientesRequest.nombreSobreviviente.isNullOrEmpty() || inventarioAComida.cantidad == null
            || inventarioAgua.cantidad == null || inventarioMedicamento.cantidad == null || inventarioMunicion.cantidad == null
        ) {
            regresa = false
        }

        return regresa
    }


    private fun setViewModel() {

        val repository = SobrevivientesRepository()
        val inventarioRepository = InventarioRepository()
        val mainFactory = NuevoUsuarioFactory(repository, inventarioRepository)
        viewModel = ViewModelProvider(this, mainFactory).get(NuevoUsuarioViewModel::class.java)
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