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
import com.bumptech.glide.Glide
import com.pliniodev.chucknorrisfacts.R
import com.pliniodev.chucknorrisfacts.constants.Constants
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
    }

    override fun onResume() {
        super.onResume()
        val bundle = intent.extras
        if (bundle != null) {
            loadData(bundle)
        } else{
            binding.viewFlipperFacts.displayedChild = VIEW_HELLO_HELP
        }
    }

    private fun loadData(bundle: Bundle) {
        val message = bundle.getString(Constants.SEARCH_MESSAGE)
        val isSearchByRandom = bundle.getBoolean(Constants.IS_SEARCH_BY_RANDOM)
        val isSearchByCategory = bundle.getBoolean(Constants.IS_SEARCH_BY_CATEGORY)

        when {
            !isSearchByCategory && !isSearchByRandom -> {
                if (message != null) {
                    mViewModel.getByFreeSearch(message)
                    binding.viewFlipperFacts.displayedChild = MY_PROGRESSBAR
                } else {
                    binding.textHelp.text = getString(R.string.new_search_question)
                    binding.viewFlipperFacts.displayedChild = VIEW_HELLO_HELP
                }
            }
            isSearchByRandom -> {
                mViewModel.getByRandom()
                binding.viewFlipperFacts.displayedChild = MY_PROGRESSBAR
            }
            isSearchByCategory -> {
                if (message != null) {
                    mViewModel.getByCategory(message)
                    binding.viewFlipperFacts.displayedChild = MY_PROGRESSBAR
                }
            }
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
        setAdapter()
        setHelloImage()
    }

    private fun setHelloImage() {
        val welcomeImage = binding.welcomeImage
        Glide.with(this)
            .load("https://assets.chucknorris.host/img/avatar/chuck-norris.png")
            .placeholder(R.drawable.ic_baseline_sentiment_very_satisfied_24)
            .into(welcomeImage);
    }

    private fun setAdapter() {
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