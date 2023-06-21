package com.example.bootapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.bootapplication.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: BooViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeLiveData()
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
                        text.text = state.boots
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
}