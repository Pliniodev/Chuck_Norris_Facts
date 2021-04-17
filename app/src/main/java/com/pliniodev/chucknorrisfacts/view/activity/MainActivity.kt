package com.pliniodev.chucknorrisfacts.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pliniodev.chucknorrisfacts.R
import com.pliniodev.chucknorrisfacts.databinding.ActivityMainBinding
import com.pliniodev.chucknorrisfacts.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mViewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel.getFreeSearch()

    }
}