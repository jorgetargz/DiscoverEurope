package com.jorgetargz.europa.ui.add_empresa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.jorgetargz.europa.R
import com.jorgetargz.europa.databinding.FragmentAddEmpresaBinding
import com.jorgetargz.europa.domain.modelo.Empresa
import com.jorgetargz.europa.ui.utils.StringProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEmpresaFragment : Fragment() {

    private lateinit var binding: FragmentAddEmpresaBinding
    private val viewModel: AddEmpresaViewModel by viewModels()

    private lateinit var stringProvider: StringProvider


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        stringProvider = StringProvider(requireContext())

        binding = FragmentAddEmpresaBinding.inflate(layoutInflater)

        binding.btnSaveCity.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val telefono = binding.etTelefono.text.toString()
            val email = binding.etEmail.text.toString()
            val website = binding.etWeb.text.toString()
            val empresa = Empresa(name = nombre, phone = telefono, email = email, website = website)
            viewModel.handleEvent(AddEmpresaEvent.AddEmpresa(empresa))
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.mensaje?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
            state.empresaAdded?.let { empresa ->
                Snackbar.make(
                    binding.root,
                    stringProvider.getString(R.string.empresa_added),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(stringProvider.getString(R.string.snackbar_undo)) {
                        viewModel.handleEvent(
                            AddEmpresaEvent.DeleteEmpresa(empresa)
                        )
                    }
                    .show()
                viewModel.handleEvent(AddEmpresaEvent.ClearState)
            }
        }

        return binding.root
    }
}