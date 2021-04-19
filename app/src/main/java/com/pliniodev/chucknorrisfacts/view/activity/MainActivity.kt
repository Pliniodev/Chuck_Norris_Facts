package com.pliniodev.chucknorrisfacts.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pliniodev.chucknorrisfacts.R
import com.pliniodev.chucknorrisfacts.databinding.ActivityMainBinding
import com.pliniodev.chucknorrisfacts.service.model.Fact
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
        mViewModel.getFactsFromFreeSearch(query)

    }

    private fun observe(){
        mViewModel.searchResultLiveData.observe(this, Observer {
            it?.let { facts ->
                mAdapter.setFacts(facts)
            }
        })

        mViewModel.showError.observe(this, Observer {
            Toast.makeText(this, "ERRO: $it", Toast.LENGTH_LONG).show()
            //todo tratamento de erros
        })
    }

    private fun setView() {
        mAdapter = FactsAdapter(this@MainActivity, this)
        mRecyclerView = binding.root.findViewById(R.id.facts_recycler)
        mRecyclerView.layoutManager = LinearLayoutManager(
            this@MainActivity, LinearLayoutManager.VERTICAL,false)
        mRecyclerView.adapter = mAdapter
    }

    override fun onClickShareImage(url: String) {
        Toast.makeText(this, "URL $url", Toast.LENGTH_LONG).show()
        //todo compartilhamento
    }
}