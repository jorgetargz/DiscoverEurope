package com.jorgetargz.europa.ui.list_paises

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.jorgetargz.europa.R
import com.jorgetargz.europa.databinding.FragmentListPaisesBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListPaisesFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentListPaisesBinding
    private val viewModel: ListPaisesViewModel by viewModels()
    private lateinit var adapter: PaisesAdapter

    inner class ListPaisesActionsImpl : ListPaisesActions {
        override fun onPaisClicked(nombre: String) {
            val action =
                ListPaisesFragmentDirections.actionListPaisesFragmentToViewPaisFragment(nombre)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        configBinding()
        configAdapter()
        addMenuProvider()

        viewModel.handleEvent(ListPaisesEvent.LoadPaises)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.listaFiltrada?.let { listaPaises ->
                adapter.submitList(listaPaises)
            }
        }

        return binding.root
    }

    private fun configBinding() {
        binding = FragmentListPaisesBinding.inflate(layoutInflater)
    }

    private fun configAdapter() {
        val rvPaises = binding.rvPaises
        adapter = PaisesAdapter(ListPaisesActionsImpl())
        rvPaises.adapter = adapter
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
                    viewModel.handleEvent(ListPaisesEvent.FiltrarPaises(newText))
                }
                return false
            }
        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }


}