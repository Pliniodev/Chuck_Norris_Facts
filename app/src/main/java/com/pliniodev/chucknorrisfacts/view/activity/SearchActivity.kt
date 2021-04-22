package com.pliniodev.chucknorrisfacts.view.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.pliniodev.chucknorrisfacts.R
import com.pliniodev.chucknorrisfacts.constants.Constants
import com.pliniodev.chucknorrisfacts.databinding.ActivitySearchBinding
import com.pliniodev.chucknorrisfacts.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchActivity : AppCompatActivity(), View.OnClickListener {
    private val mViewModel: SearchViewModel by viewModel()
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observe()
        setListeners()
        setView()
    }

    private fun setView() {
        //todo livedata<listof>
        val items = listOf(
            "Animal",
            "Career",
            "Celebrity",
            "Dev",
            "Explicit",
            "Fashion",
            "Food",
            "History",
            "Money",
            "Movie",
            "Music",
            "Political",
            "Religion",
            "Science",
            "Sport",
            "Travel"
        )//TEMPORÁRIO
        val adapter = ArrayAdapter(this, R.layout.category_list, items)
        (binding.editListCategory).setAdapter(adapter)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigateToMain(null, isSearchByCategory = false, isSearchByRandom = false)
    }

    private fun setListeners() {
        setEditSearchListener()
        setEditCategoryListener()
        binding.buttonSearch.setOnClickListener(this)
        binding.radioRandom.setOnClickListener(this)
        binding.radioCategory.setOnClickListener(this)
        binding.radioFree.setOnClickListener(this)
    }

    private fun setEditCategoryListener() {
        (binding.editListCategory).setOnItemClickListener { parent, view, position, id ->
            binding.buttonSearch.isEnabled = true
            binding.buttonSearch.setBackgroundColor(this.getColor(R.color.blue_background));
        }
    }

    private fun setEditSearchListener() {
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(search: Editable) {
                if (search.isEmpty()) {
                    mViewModel.validate(search.toString())
                }
            }

            override fun beforeTextChanged(
                search: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                mViewModel.validate(search.toString())
            }

            override fun onTextChanged(
                search: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                mViewModel.validate(search.toString())
            }
        })
    }

    private fun observe() {
        mViewModel.validatorMsgLiveData.observe(this, Observer {
            it?.let { validatorMsgPair ->
                activateSearch(validatorMsgPair.first)

                if (validatorMsgPair.first) {
                    validatorMsgPair.second?.let { msgValidate ->
                        binding.textInputLayoutSearch.helperText = getString(msgValidate)
                    }
                } else {
                    validatorMsgPair.second?.let { msgValidate ->
                        binding.textInputLayoutSearch.error = getString(msgValidate)
                    }
                }
            }
        })
    }

    private fun activateSearch(searchChecker: Boolean) {
        if (searchChecker) {
            binding.buttonSearch.isEnabled = searchChecker
            binding.buttonSearch.setBackgroundColor(this.getColor(R.color.blue_background));
        } else {
            binding.buttonSearch.isEnabled = searchChecker
            binding.buttonSearch.setBackgroundColor(this.getColor(R.color.button_unabled));
        }
    }

    override fun onClick(view: View) {
        val search = binding.editSearch.text.toString()
        val category = binding.editListCategory.text.toString()
        val isSearchByRandom = binding.radioRandom.isChecked
        val isSearchByCategory = binding.radioCategory.isChecked

        when (view.id) {
            binding.radioRandom.id -> {
                navigateToMain(null, isSearchByRandom, isSearchByCategory)
            }
            binding.radioCategory.id -> {
                if (binding.textInputLayoutSearch.visibility == View.VISIBLE) {
                    binding.textInputLayoutSearch.visibility = View.GONE
                }
                binding.layoutToWriteMsg.visibility = View.VISIBLE
                binding.dropmenuCategory.visibility = View.VISIBLE
            }
            binding.radioFree.id -> {
                if (binding.dropmenuCategory.visibility == View.VISIBLE) {
                    binding.dropmenuCategory.visibility = View.GONE
                }
                binding.textInputLayoutSearch.helperText = getString(R.string.do_the_search)
                binding.textInputLayoutSearch.visibility = View.VISIBLE
                binding.layoutToWriteMsg.visibility = View.VISIBLE

            }
            binding.buttonSearch.id -> {
                if (binding.radioCategory.isChecked) {
                    navigateToMain(cleanSearch(category), isSearchByRandom, isSearchByCategory)
                } else {
                    navigateToMain(cleanSearch(search), isSearchByRandom, isSearchByCategory)
                }
            }
        }
    }

    private fun navigateToMain(
        search: String?,
        isSearchByRandom: Boolean,
        isSearchByCategory: Boolean
    ) {

        val intent = Intent(this, MainActivity::class.java)

        val bundle = Bundle()
        bundle.putString(Constants.SEARCH_MESSAGE, search)
        bundle.putBoolean(Constants.IS_SEARCH_BY_RANDOM, isSearchByRandom)
        bundle.putBoolean(Constants.IS_SEARCH_BY_CATEGORY, isSearchByCategory)
        intent.putExtras(bundle)

        startActivity(intent)
        finish()
    }

    private fun cleanSearch(search: String): String {
        //if translate is necessary check the insert Locate in toLowerCase
        return search.filter { it.isLetterOrDigit() }.toLowerCase()
    }

}
