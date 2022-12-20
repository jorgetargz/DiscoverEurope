package com.jorgetargz.europa.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.jorgetargz.europa.R
import com.jorgetargz.europa.databinding.ActivityMainBinding
import com.jorgetargz.europa.domain.modelo.Pais
import com.jorgetargz.europa.ui.common.Constantes
import com.jorgetargz.europa.ui.utils.StringProvider
import com.jorgetargz.europa.ui.utils.findItemByTitle
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel: MainActivityViewModel by viewModels()
    private val stringProvider = StringProvider(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configNavController()
        configNavView()
        configAppBar()
        configBackButton()

        viewModel.handleEvent(MainActivityEvent.LoadPaisesFavoritos)

        viewModel.state.observe(this) { state ->
            state.favoritos?.let { listaPaises ->
                binding.navView.menu.findItemByTitle(stringProvider.getString(R.string.favoritos))?.subMenu?.clear()
                listaPaises.forEach { pais ->
                    addPaisToDrawerMenu(pais)
                }
            }
        }
    }

    private fun configNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.findNavController()
        //TODO: This is just for debugging right now (remove later)
        navController.addOnDestinationChangedListener() { _, destination, _ ->
            when (destination.id) {
                R.id.listPaisesFragment -> {
                    binding.navView.visibility = View.VISIBLE
                }
                else -> {
                    binding.navView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun configNavView() {
        binding.navView.setupWithNavController(navController)
        //TODO: This is a workaround don't know why binding.navView.setupWithNavController(navController) doesn't work
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.listPaisesFragment -> {
                    navController.navigate(R.id.action_global_listPaisesFragment)
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun configAppBar() {
        appBarConfiguration = AppBarConfiguration(
            navController.graph,
            binding.drawerLayout
        )
        setSupportActionBar(binding.topAppBar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun configBackButton() {
        onBackPressedDispatcher.addCallback(this) {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else if (navController.currentDestination?.id == R.id.listPaisesFragment) {
                finish()
            } else {
                navController.navigateUp()
            }
        }
    }

    private fun addPaisToDrawerMenu(pais: Pais) {
        val favoritesMenu =
            binding.navView.menu.findItemByTitle(stringProvider.getString(R.string.favoritos))?.subMenu
        val id = View.generateViewId()
        favoritesMenu?.add(Menu.NONE, id, Menu.NONE, pais.nombre).apply {
            this?.setOnMenuItemClickListener {
                navController.navigate(R.id.action_global_viewPaisFragment, Bundle().apply {
                    putString(Constantes.NOMBRE, pais.nombre)
                })
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}