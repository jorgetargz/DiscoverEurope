package com.jorgetargz.europa.ui.view_pais

import android.os.Bundle
import android.view.*
import androidx.core.view.GravityCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.jorgetargz.europa.R
import com.jorgetargz.europa.databinding.FragmentViewPaisBinding
import com.jorgetargz.europa.ui.common.Constantes
import com.jorgetargz.europa.ui.main.MainActivity
import com.jorgetargz.europa.ui.utils.StringProvider
import com.jorgetargz.europa.ui.utils.findItemByTitle
import com.jorgetargz.europa.ui.utils.loadUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPaisFragment : Fragment(), MenuProvider {

    private val argNombre = Constantes.NOMBRE
    private lateinit var nombre: String;
    private lateinit var binding: FragmentViewPaisBinding
    private val viewModel: ViewPaisViewModel by viewModels()

    private lateinit var stringProvider: StringProvider

    private lateinit var navController: NavController
    private lateinit var drawerMenu: Menu
    private lateinit var drawerLayout: DrawerLayout

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
        loadMainActivityObjects()

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        stringProvider = StringProvider(requireContext())

        binding = FragmentViewPaisBinding.inflate(layoutInflater)
        viewModel.handleEvent(ViewPaisEvent.LoadPais(nombre))
        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.pais?.let { pais ->
                with(binding) {
                    ivBandera.loadUrl(pais.urlBandera)
                    tvNombre.text = pais.nombre
                    tvNombreLocal.text = pais.nombreLocal
                    tvCapital.text = pais.capital
                    tvIdiomas.text = pais.idiomas
//                    btnCiudades.setOnClickListener {
//                        val action = ViewPaisFragmentDirections.actionViewPaisFragmentToViewCiudadesFragment(pais.nombre)
//                        findNavController().navigate(action)
//                    }
                }
            }
            if (state.onMarkedAsFavorito) {
                addPaisToDrawerMenu()
                viewModel.clearStateEvents()
            }
            if (state.onUnmarkedAsFavorito) {
                removePaisFromDrawerMenu()
                viewModel.clearStateEvents()
            }
        }
        return binding.root
    }


    private fun loadMainActivityObjects() {
        val navHostFragment =
            (activity as MainActivity).supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.findNavController()
        drawerMenu =
            ((activity as MainActivity).findViewById(R.id.nav_view) as NavigationView).menu
        drawerLayout =
            (activity as MainActivity).findViewById(R.id.drawer_layout) as DrawerLayout
    }

    private fun addPaisToDrawerMenu() {

        removePaisFromDrawerMenu()

        val favoritesMenu = drawerMenu.findItemByTitle(stringProvider.getString(R.string.favoritos))?.subMenu
        val id = View.generateViewId()
        favoritesMenu?.add(Menu.NONE, id, Menu.NONE, nombre).apply {
            this?.setOnMenuItemClickListener {
                navController.navigate(R.id.action_global_viewPaisFragment, Bundle().apply {
                    putString(Constantes.NOMBRE, nombre)
                })
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
        }
    }

    private fun removePaisFromDrawerMenu() {
        val favoritesMenu = drawerMenu.findItemByTitle(stringProvider.getString(R.string.favoritos))?.subMenu
        favoritesMenu?.findItemByTitle(nombre)?.let {
            favoritesMenu.removeItem(it.itemId)
        }
    }

    override fun onPrepareMenu(menu: Menu) {
        viewModel.state.value?.pais?.let { pais ->
            menu.findItem(R.id.favorito).apply {
                isChecked = pais.favorito
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_view_pais, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.favorito -> {
                menuItem.isChecked = !menuItem.isChecked
                viewModel.handleEvent(ViewPaisEvent.OnFavClick(nombre))
                true
            }
//            R.id.listPaisesFragment -> {
//                navController.navigate(R.id.action_global_listPaisesFragment)
//                drawerLayout.closeDrawer(GravityCompat.START)
//                true
//            }
            else -> false

        }
    }
}