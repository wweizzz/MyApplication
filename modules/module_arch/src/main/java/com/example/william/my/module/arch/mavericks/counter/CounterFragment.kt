package com.example.william.my.module.arch.mavericks.counter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.example.william.my.basic.basic_module.databinding.BasicsLayoutResponseBinding
import com.example.william.my.module.arch.R
import com.example.william.my.module.arch.mavericks.utils.viewBinding

/**
 * Fragments in Mavericks are simple and rarely do more than bind your state to views.
 * Mavericks works well with Fragments but you can use it with whatever view architecture you use.
 */
class CounterFragment : Fragment(R.layout.basics_layout_response), MavericksView {

    private val binding: BasicsLayoutResponseBinding by viewBinding()
    private val viewModel: CounterViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.basicsResponse.setOnClickListener {
            viewModel.incrementCount()
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        binding.basicsResponse.text = "Count: ${state.count}"
    }
}