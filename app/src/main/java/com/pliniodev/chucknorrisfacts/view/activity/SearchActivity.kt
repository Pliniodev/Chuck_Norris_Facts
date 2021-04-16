package com.pliniodev.chucknorrisfacts.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pliniodev.chucknorrisfacts.R
import com.pliniodev.chucknorrisfacts.databinding.ActivityMainBinding
import com.pliniodev.chucknorrisfacts.databinding.ActivitySearchBinding
import com.pliniodev.chucknorrisfacts.viewmodel.MainViewModel
import com.pliniodev.chucknorrisfacts.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {
    private val viewModel: SearchViewModel by viewModel()
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}