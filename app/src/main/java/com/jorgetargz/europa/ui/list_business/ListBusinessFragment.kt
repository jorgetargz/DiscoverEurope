package com.jorgetargz.europa.ui.list_business

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import com.jorgetargz.europa.R
import com.jorgetargz.europa.databinding.FragmentListBusinessBinding
import com.jorgetargz.europa.domain.modelo.Business
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListBusinessFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentListBusinessBinding
    private val viewModel: ListBusinessViewModel by viewModels()
    private lateinit var adapter: BusinessAdapter

    inner class ListBusinessActionsImpl : ListBusinessActions {
        override fun onBusinessClicked(nombre: String) {
            TODO("Not yet implemented")
        }

        override fun onBusinessSwipedLeft(empresa: Business) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        configBinding()
        configAdapter()
        addMenuProvider()

        viewModel.handleEvent(ListBusinessEvent.LoadBusiness)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.listaFiltrada?.let { listaEmpresas ->
                adapter.submitList(listaEmpresas)
            }
        }

        return binding.root
    }

    private fun configBinding() {
        binding = FragmentListBusinessBinding.inflate(layoutInflater)
    }

    private fun configAdapter() {
        val rvEmpresas = binding.rvEmpresas
        adapter = BusinessAdapter(ListBusinessActionsImpl())
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
                    viewModel.handleEvent(ListBusinessEvent.FiltrarBusiness(newText))
                }
                return false
            }
        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }

}