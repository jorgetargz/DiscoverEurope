package com.jorgetargz.europa.ui.edit_ruta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.jorgetargz.europa.R
import com.jorgetargz.europa.databinding.FragmentEditRutaBinding
import com.jorgetargz.europa.domain.modelo.Ciudad
import com.jorgetargz.europa.domain.modelo.Empresa
import com.jorgetargz.europa.domain.modelo.Ruta
import com.jorgetargz.europa.ui.common.Constantes
import com.jorgetargz.europa.ui.utils.StringProvider
import com.jorgetargz.europa.ui.utils.getPosition
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditRutaFragment : Fragment() {

    private lateinit var binding: FragmentEditRutaBinding
    private val viewModel: EditRutaViewModel by viewModels()

    private val argIdRuta = Constantes.ID_RUTA
    private var idRuta: Int = 0
    private lateinit var stringProvider: StringProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idRuta = it.getInt(argIdRuta)
        }
        stringProvider = StringProvider(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEditRutaBinding.inflate(layoutInflater)

        viewModel.handleEvent(EditRutaEvent.LoadRuta(idRuta))
        viewModel.handleEvent(EditRutaEvent.LoadDropdownLists)

        configEditButton()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.mensaje?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
            state.rutaMostrar?.let { ruta ->
                with(binding) {
                    tiCiudadInico.editText?.setText(ruta.ciudadInicio.toString())
                    tiCiudadFin.editText?.setText(ruta.ciudadFin.toString())
                    tiEmpresa.editText?.setText(ruta.empresa.toString())
                    tiPrecio.editText?.setText(ruta.precio.toString())
                    tiDuracion.editText?.setText(ruta.duracionMin.toString())
                    tiComentarios.editText?.setText(ruta.comentario)
                }
            }
            if (state.rutaEditada) {
                Snackbar.make(
                    binding.root,
                    stringProvider.getString(R.string.ruta_edited),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(stringProvider.getString(R.string.snackbar_undo)) {
                        viewModel.handleEvent(
                            EditRutaEvent.UndoEditRuta
                        )
                    }
                    .show()
                viewModel.handleEvent(EditRutaEvent.ClearState)
            }
            state.listaCiudades?.let { ciudadList ->
                val adapterCiudades =
                    ArrayAdapter(requireContext(), R.layout.item_dropdown, ciudadList)
                (binding.tiCiudadInico.editText as? AutoCompleteTextView)?.setAdapter(
                    adapterCiudades
                )
                (binding.tiCiudadFin.editText as? AutoCompleteTextView)?.setAdapter(adapterCiudades)
            }
            state.listaEmpresas?.let { empresaList ->
                val adapterEmpresas =
                    ArrayAdapter(requireContext(), R.layout.item_dropdown, empresaList)
                (binding.tiEmpresa.editText as? AutoCompleteTextView)?.setAdapter(adapterEmpresas)
            }
        }

        return binding.root
    }

    private fun configEditButton() {
        with(binding) {
            btnEditRuta.setOnClickListener {
                val posCiudadInicio = (tiCiudadInico.editText as? AutoCompleteTextView)?.adapter
                    ?.getPosition(tiCiudadInico.editText?.text.toString())
                val posCiudadFin = (tiCiudadFin.editText as? AutoCompleteTextView)?.adapter
                    ?.getPosition(tiCiudadFin.editText?.text.toString())
                val posEmpresa = (tiEmpresa.editText as? AutoCompleteTextView)?.adapter
                    ?.getPosition(tiEmpresa.editText?.text.toString())
                val duracion = tiDuracion.editText?.text.toString()
                val precio = tiPrecio.editText?.text.toString()
                val comentarios = tiComentarios.editText?.text.toString()

                if (posCiudadInicio != -1 && posCiudadFin != -1 && posEmpresa != -1
                    && duracion.isNotEmpty() && precio.isNotEmpty()
                ) {

                    val ciudadInicio =
                        (tiCiudadInico.editText as? AutoCompleteTextView)?.adapter?.getItem(
                            posCiudadInicio!!
                        ) as? Ciudad
                    val ciudadFin =
                        (tiCiudadFin.editText as? AutoCompleteTextView)?.adapter?.getItem(
                            posCiudadFin!!
                        ) as? Ciudad
                    val empresa =
                        (tiEmpresa.editText as? AutoCompleteTextView)?.adapter?.getItem(
                            posEmpresa!!
                        ) as? Empresa


                    val ruta = Ruta(
                        ciudadInicio = ciudadInicio!!,
                        ciudadFin = ciudadFin!!,
                        empresa = empresa!!,
                        duracionMin = duracion.toInt(),
                        precio = precio.toDouble(),
                        comentario = comentarios
                    )
                    viewModel.handleEvent(EditRutaEvent.EditRuta(ruta))
                } else {
                    Snackbar.make(
                        root,
                        stringProvider.getString(R.string.error_add_ruta),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}