package com.jorgetargz.europa.ui.edit_empresa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.jorgetargz.europa.R
import com.jorgetargz.europa.databinding.FragmentEditEmpresaBinding
import com.jorgetargz.europa.domain.modelo.Empresa
import com.jorgetargz.europa.ui.common.Constantes
import com.jorgetargz.europa.ui.utils.StringProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditEmpresaFragment : Fragment() {

    private lateinit var binding: FragmentEditEmpresaBinding
    private val viewModel: EditEmpresaViewModel by viewModels()

    private val argIdEmpresa = Constantes.ID_EMPRESA
    private var idEmpresa: Int = 0
    private lateinit var stringProvider: StringProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idEmpresa = it.getInt(argIdEmpresa)
        }
        stringProvider = StringProvider(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEditEmpresaBinding.inflate(layoutInflater)

        viewModel.handleEvent(EditEmpresaEvent.LoadEmpresa(idEmpresa))

        with(binding) {
            btnEditEmpresa.setOnClickListener {
                val nombre = etNombre.text.toString()
                val telefono = etTelefono.text.toString()
                val email = etEmail.text.toString()
                val website = etWeb.text.toString()
                val empresa = Empresa(
                    id = idEmpresa,
                    name = nombre,
                    phone = telefono,
                    email = email,
                    website = website
                )
                viewModel.handleEvent(EditEmpresaEvent.EditEmpresa(empresa))
            }
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.mensaje?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
            state.empresaMostrar?.let { empresa ->
                with(binding) {
                    etNombre.setText(empresa.name)
                    etEmail.setText(empresa.email)
                    etWeb.setText(empresa.website)
                    etTelefono.setText(empresa.phone)
                }
            }
            if (state.empresaEditada) {
                Snackbar.make(
                    binding.root,
                    stringProvider.getString(R.string.empresa_edited),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(stringProvider.getString(R.string.snackbar_undo)) {
                        viewModel.handleEvent(
                            EditEmpresaEvent.UndoEditEmpresa
                        )
                    }
                    .show()
                viewModel.handleEvent(EditEmpresaEvent.ClearState)
            }
        }

        return binding.root
    }
}