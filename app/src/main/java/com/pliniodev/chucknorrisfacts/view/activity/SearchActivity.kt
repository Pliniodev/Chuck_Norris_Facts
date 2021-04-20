package com.pliniodev.chucknorrisfacts.view.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.pliniodev.chucknorrisfacts.R
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
        validateSearch()
        binding.buttonSearch.setOnClickListener(this)
        binding.textInputLayoutSearch.helperText = getString(R.string.do_the_search)
    }

    private fun validateSearch() {
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.isEmpty()){
                    mViewModel.validate(0)
                }
            }
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                mViewModel.validate(s.length)
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                mViewModel.validate(s.length)
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
        }else{
            binding.buttonSearch.isEnabled = searchChecker
            binding.buttonSearch.setBackgroundColor(this.getColor(R.color.button_unabled));
        }
    }

    override fun onClick(view: View) {
        if (view.id == binding.buttonSearch.id) {
            //todo
            Toast.makeText(this, "Borãosikndoa", Toast.LENGTH_LONG).show()
        }
    }

}
