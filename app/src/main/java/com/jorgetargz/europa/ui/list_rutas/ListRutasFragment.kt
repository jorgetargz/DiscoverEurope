package com.jorgetargz.europa.ui.list_rutas

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
import com.jorgetargz.europa.databinding.FragmentListRutasBinding
import com.jorgetargz.europa.domain.modelo.Ruta
import com.jorgetargz.europa.ui.utils.StringProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListRutasFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentListRutasBinding
    private val viewModel: ListRutasViewModel by viewModels()
    private lateinit var adapter: RutasAdapter
    private lateinit var stringProvider: StringProvider

    inner class ListRutasActionsImpl : ListRutasActions {
        override fun onRutaClicked(ruta: Ruta) {
            val action = ListRutasFragmentDirections.actionListRutasFragmentToEditRutaFragment(ruta.id)
            findNavController().navigate(action)
        }

        override fun onRutaSwipedLeft(ruta: Ruta) {
            viewModel.handleEvent(ListRutasEvent.DeleteRuta(ruta))
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
            val action = ListRutasFragmentDirections.actionListRutasFragmentToAddRutaFragment()
            findNavController().navigate(action)
        }

        viewModel.handleEvent(ListRutasEvent.LoadRutas)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.listaFiltrada?.let { listaRutas ->
                adapter.submitList(listaRutas)
            }
            state.rutaEliminada?.let { ruta ->
                Snackbar.make(
                    binding.rvRutas,
                    stringProvider.getString(R.string.ruta_borrada),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(stringProvider.getString(R.string.snackbar_undo)) {
                        viewModel.handleEvent(
                            ListRutasEvent.UndoDeleteRuta(ruta)
                        )
                    }
                    .show()
                viewModel.handleEvent(ListRutasEvent.ClearState)
            }
        }

        return binding.root
    }

    private fun configBinding() {
        binding = FragmentListRutasBinding.inflate(layoutInflater)
    }

    private fun configAdapter() {
        val rvRutas = binding.rvRutas
        adapter = RutasAdapter(ListRutasActionsImpl())
        rvRutas.adapter = adapter
        val touchHelper = ItemTouchHelper(adapter.touchHelper)
        touchHelper.attachToRecyclerView(rvRutas)
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
                    viewModel.handleEvent(ListRutasEvent.FiltrarRutas(newText))
                }
                return false
            }
        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }

}