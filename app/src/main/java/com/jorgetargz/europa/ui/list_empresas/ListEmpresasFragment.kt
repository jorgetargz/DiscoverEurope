package com.jorgetargz.europa.ui.list_empresas

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.jorgetargz.europa.R
import com.jorgetargz.europa.databinding.FragmentListBusinessBinding
import com.jorgetargz.europa.domain.modelo.Empresa
import com.jorgetargz.europa.ui.utils.StringProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListEmpresasFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentListBusinessBinding
    private val viewModel: ListEmpresasViewModel by viewModels()
    private lateinit var adapter: EmpresasAdapter
    private lateinit var stringProvider: StringProvider

    inner class ListEmpresasActionsImpl : ListEmpresasActions {
        override fun onEmpresaClicked(nombre: String) {
            TODO("Not yet implemented")
        }

        override fun onEmpresaSwipedLeft(empresa: Empresa) {
            viewModel.handleEvent(ListEmpresasEvent.DeleteEmpresa(empresa))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stringProvider = StringProvider(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        configBinding()
        configAdapter()
        addMenuProvider()

        binding.fabAdd.setOnClickListener {
            val action =
                ListEmpresasFragmentDirections.actionListEmpresasFragmentToAddEmpresaFragment()
            findNavController().navigate(action)
        }

        viewModel.handleEvent(ListEmpresasEvent.LoadEmpresas)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.listaFiltrada?.let { listaEmpresas ->
                adapter.submitList(listaEmpresas)
            }
            state.empresaEliminada?.let { empresa ->
                Snackbar.make(
                    binding.rvEmpresas,
                    stringProvider.getString(R.string.empresa_borrada),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(stringProvider.getString(R.string.snackbar_undo)) {
                        viewModel.handleEvent(
                            ListEmpresasEvent.UndoDeleteEmpresa(empresa)
                        )
                    }
                    .show()
                viewModel.handleEvent(ListEmpresasEvent.ClearState)
            }
        }

        return binding.root
    }

    private fun configBinding() {
        binding = FragmentListBusinessBinding.inflate(layoutInflater)
    }

    private fun configAdapter() {
        val rvEmpresas = binding.rvEmpresas
        adapter = EmpresasAdapter(ListEmpresasActionsImpl())
        rvEmpresas.adapter = adapter
        val touchHelper = ItemTouchHelper(adapter.touchHelper)
        touchHelper.attachToRecyclerView(rvEmpresas)
    }

    private fun addMenuProvider() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_buscador, menu)
        val actionSearch = menu.findItem(R.id.search).actionView as SearchView
        actionSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.handleEvent(ListEmpresasEvent.FiltrarEmpresas(newText))
                }
                return false
            }
        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }

}