package com.pliniodev.chucknorrisfacts.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pliniodev.chucknorrisfacts.R
import com.pliniodev.chucknorrisfacts.databinding.ActivityMainBinding
import com.pliniodev.chucknorrisfacts.view.adapter.FactsAdapter
import com.pliniodev.chucknorrisfacts.view.listener.FactsListener
import com.pliniodev.chucknorrisfacts.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), FactsListener{

    private val mViewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: FactsAdapter
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
        observe()

        var query = "food"//temporario
        //Só poderá ser chamado na pesquisa
        mViewModel.getFreeSearch(query)

    }

    private fun observe(){
        mViewModel.searchResult.observe(this, Observer {
            if (it != null) {
                mAdapter.setFact(it.result)
            }
        })
    }

    private fun setView() {
        mAdapter = FactsAdapter(this,this)
        mRecyclerView = binding.root.findViewById(R.id.facts_recycler)
        mRecyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL,false)
        mRecyclerView.adapter = mAdapter
    }

    override fun onClick(url: String) {
        Toast.makeText(this, "URL $url", Toast.LENGTH_LONG).show()
        //todo compartilhamento
    }
}