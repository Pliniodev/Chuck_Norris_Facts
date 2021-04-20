package com.pliniodev.chucknorrisfacts.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
        binding.viewFlipperFacts.displayedChild = VIEW_HELLO_HELP
    }

    override fun onResume() {
        super.onResume()
        val message = intent.getStringExtra("searchText")
        if (message != null) {
            mViewModel.getFactsFromFreeSearch(message)
            binding.viewFlipperFacts.displayedChild = MY_PROGRESSBAR
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_search,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                navigateToSearch()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToSearch(){
        startActivity(Intent(this, SearchActivity::class.java))
        finish()
    }

    private fun observe(){
        mViewModel.searchResultLiveData.observe(this, Observer {
            it?.let { facts ->
                mAdapter.setFacts(facts)
            }
        })

        mViewModel.viewFlipperLiveData.observe(this, Observer {
            it?.let { viewFlipper ->
                binding.viewFlipperFacts.displayedChild = viewFlipper.first

                viewFlipper.second?.let { errorMessageResId ->
                    binding.textViewError.text = getString(errorMessageResId)
                }
            }
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

    companion object {
        private const val VIEW_HELLO_HELP = 0
        private const val MY_PROGRESSBAR = 1
    }
}