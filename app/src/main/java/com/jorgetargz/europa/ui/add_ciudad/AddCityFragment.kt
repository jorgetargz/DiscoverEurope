package com.jorgetargz.europa.ui.add_ciudad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.jorgetargz.europa.R
import com.jorgetargz.europa.databinding.FragmentAddCityBinding
import com.jorgetargz.europa.domain.modelo.Ciudad
import com.jorgetargz.europa.ui.common.Constantes
import com.jorgetargz.europa.ui.utils.StringProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCityFragment : Fragment() {

    private val argPais = Constantes.PAIS
    private lateinit var pais: String
    private lateinit var binding: FragmentAddCityBinding
    private val viewModel: AddCityViewModel by viewModels()

    private lateinit var stringProvider: StringProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pais = it.getString(argPais)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        stringProvider = StringProvider(requireContext())

        binding = FragmentAddCityBinding.inflate(layoutInflater)

        binding.btnSaveCity.setOnClickListener {
            val nombre = binding.etCiudad.text.toString()
            val ciudad = Ciudad(nombre = nombre, pais = pais)
            viewModel.handleEvent(AddCityEvent.AddCiudad(ciudad))
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.mensaje?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
            state.ciudadAdded?.let { ciudad ->
                Snackbar.make(
                    binding.root,
                    stringProvider.getString(R.string.ciudad_added),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(stringProvider.getString(R.string.snackbar_undo)) {
                        viewModel.handleEvent(
                            AddCityEvent.DeleteCiudad(ciudad)
                        )
                    }
                    .show()
                viewModel.handleEvent(AddCityEvent.ClearState)
            }
        }
        return binding.root
    }
}
