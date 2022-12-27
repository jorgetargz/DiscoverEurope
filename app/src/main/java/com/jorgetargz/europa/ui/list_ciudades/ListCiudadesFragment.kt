package com.jorgetargz.europa.ui.list_ciudades

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.jorgetargz.europa.R
import com.jorgetargz.europa.databinding.FragmentListPaisesBinding
import com.jorgetargz.europa.ui.common.Constantes
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListCiudadesFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentListPaisesBinding
    private val viewModel: ListCiudadesViewModel by viewModels()
    private lateinit var adapter: CiudadesAdapter

    private val argNombre = Constantes.NOMBRE
    private lateinit var nombre: String

    inner class ListCiudadesActionsImpl : ListCiudadesActions {
        override fun onPaisClicked(nombre: String) {
//            val action =
//                ListCiudadesFragmentDirections.actionListPaisesFragmentToViewPaisFragment(nombre)
//            findNavController().navigate(action)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nombre = it.getString(argNombre)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        configBinding()
        configAdapter()
        addMenuProvider()

        viewModel.handleEvent(ListCiudadesEvent.LoadCiudades(nombre))

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
        adapter = CiudadesAdapter(ListCiudadesActionsImpl())
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
                    viewModel.handleEvent(ListCiudadesEvent.FiltrarCiudades(newText))
                }
                return false
            }
        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }


}