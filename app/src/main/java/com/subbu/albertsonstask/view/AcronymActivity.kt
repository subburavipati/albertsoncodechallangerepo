package com.subbu.albertsonstask.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.subbu.albertsonstask.R
import com.subbu.albertsonstask.databinding.ActivityAcronymBinding
import com.subbu.albertsonstask.viewmodel.AcronymViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AcronymActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAcronymBinding
    private lateinit var wordAdapter: WordsAdapter

    private val viewModel: AcronymViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcronymBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }.apply {
            wordAdapter = WordsAdapter()
            with(rvMeanings) {
                layoutManager = LinearLayoutManager(this@AcronymActivity)
                adapter = wordAdapter
            }
            btSubmitAcronym.setOnClickListener {
                val enteredText =
                    etEnterAcronym.text?.toString() ?: return@setOnClickListener
                if (enteredText.isNotEmpty()) {
                    viewModel.getLfs(enteredText)
                } else {
                    wordAdapter.submitList(emptyList())
                    Toast.makeText(
                        this@AcronymActivity,
                        getString(R.string.enter_valid_acronym),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.acronymEvent.observe(this@AcronymActivity) {
                when (it) {
                    is AcronymViewModel.AcronymEvent.Success -> {
                        binding.progressBar.visibility = View.GONE
                        if(it.response.isEmpty()) {
                            Snackbar.make(binding.clAcronymContainer, getString(R.string.no_results), Snackbar.LENGTH_SHORT).show()
                            return@observe
                        }
                        wordAdapter.submitList(it.response)
                    }
                    is AcronymViewModel.AcronymEvent.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        wordAdapter.submitList(emptyList())
                        Snackbar.make(binding.clAcronymContainer, it.errorMessage, Snackbar.LENGTH_SHORT).show()
                    }
                    is AcronymViewModel.AcronymEvent.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        wordAdapter.submitList(emptyList())
                    }
                }
            }
        }
    }
}