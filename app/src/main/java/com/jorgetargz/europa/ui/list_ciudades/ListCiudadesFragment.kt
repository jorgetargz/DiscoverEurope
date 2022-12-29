package com.jorgetargz.europa.ui.list_ciudades

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.jorgetargz.europa.R
import com.jorgetargz.europa.databinding.FragmentListCiudadesBinding
import com.jorgetargz.europa.ui.common.Constantes
import com.jorgetargz.europa.ui.utils.StringProvider
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListCiudadesFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentListCiudadesBinding
    private val viewModel: ListCiudadesViewModel by viewModels()
    private lateinit var adapter: CiudadesAdapter

    private val argNombre = Constantes.NOMBRE
    private lateinit var nombre: String

    private lateinit var stringProvider: StringProvider

    inner class ListCiudadesActionsImpl : ListCiudadesActions {
        override fun onCityClicked(nombre: String) {

        }

        override fun onCitySwipedLeft(position: Int) {
            val ciudad = adapter.currentList[position]
            viewModel.handleEvent(ListCiudadesEvent.DeleteCiudad(ciudad))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nombre = it.getString(argNombre)!!
        }
        stringProvider = StringProvider(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        configBinding()
        configRecyclerView()
        addMenuProvider()

        viewModel.handleEvent(ListCiudadesEvent.LoadCiudades(nombre))

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.listaFiltrada?.let { listaCiudades ->
                adapter.submitList(listaCiudades.sortedBy { it.nombre })
            }
            state.ciudadEliminada?.let { ciudad ->
                Snackbar.make(
                    binding.rvCiudades,
                    stringProvider.getString(R.string.ciudad_borrada),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(stringProvider.getString(R.string.snackbar_undo)) {
                        viewModel.handleEvent(
                            ListCiudadesEvent.UndoDeleteCiudad(ciudad)
                        )
                    }
                    .show()
                viewModel.handleEvent(ListCiudadesEvent.ClearState)
            }
        }

        return binding.root
    }

    private fun configBinding() {
        binding = FragmentListCiudadesBinding.inflate(layoutInflater)
    }

    private fun configRecyclerView() {
        val rvCiudades = binding.rvCiudades
        adapter = CiudadesAdapter(ListCiudadesActionsImpl())
        rvCiudades.adapter = adapter
        val touchHelper = ItemTouchHelper(adapter.touchHelper)
        touchHelper.attachToRecyclerView(rvCiudades)
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