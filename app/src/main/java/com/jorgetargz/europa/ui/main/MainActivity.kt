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
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.jorgetargz.europa.R
import com.jorgetargz.europa.databinding.ActivityMainBinding
import com.jorgetargz.europa.domain.modelo.Pais
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configNavController()
        configNavView()
        configAppBar()
        configDrawerMenu()
        configBackButton()

        viewModel.handleEvent(MainActivityEvent.LoadPaisesFavoritos)

        viewModel.state.observe(this) { state ->
            state.favoritos?.let { listaPaises ->
                binding.navView.menu.removeGroup(R.id.drawer_group)
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
    }

    private fun configNavView() {
        binding.navView.setupWithNavController(navController)
    }

    private fun configAppBar() {
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.listPaisesFragment, R.id.viewPaisFragment),
            binding.drawerLayout
        )
        setSupportActionBar(binding.topAppBar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun configDrawerMenu() {
        val drawerMenu = binding.navView.menu
        drawerMenu.add(Menu.NONE, Menu.NONE, 0,"Ver paises").apply {
            setOnMenuItemClickListener {
                navController.navigate(R.id.action_global_listPaisesFragment)
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
        }
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
        val drawerMenu = binding.navView.menu
        val id = View.generateViewId()
        drawerMenu.add(R.id.drawer_group, id, Menu.NONE, pais.nombre).apply {
            setOnMenuItemClickListener {
                navController.navigate(R.id.action_global_viewPaisFragment, Bundle().apply {
                    putString("nombre", pais.nombre)
                })
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        binding.drawerLayout.openDrawer(GravityCompat.START)
        return true
    }

}