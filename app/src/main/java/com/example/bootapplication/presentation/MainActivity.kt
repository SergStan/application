package com.example.bootapplication.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.bootapplication.R
import com.example.bootapplication.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: BooViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeLiveData()
        grantPermission()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadHistory()
    }

    private fun observeLiveData() {
        viewModel.result.observe(this) { state ->
            with(binding) {
                when (state) {
                    is MainState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }

                    is MainState.Content -> {
                        progressBar.visibility = View.GONE
                        text.text = if (state.boots.isEmpty()) {
                            "\u2022 ${resources.getString(R.string.no_boots_detected)}"
                        } else {
                            state.boots
                        }
                    }

                    is MainState.Error -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(
                            this@MainActivity,
                            state.throwable.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                        Log.e("TAAG", "Exception loading data", state.throwable)
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun grantPermission() {
        val requestPermissionLauncher: ActivityResultLauncher<String> = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (!it) {
                Snackbar.make(
                    findViewById<View>(android.R.id.content).rootView,
                    "Please grant Notification permission from App Settings",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS) }
    }
}