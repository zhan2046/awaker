package com.ruzhan.day

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DayNewFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() = DayNewFragment()
    }

    private var dayViewModel: DayViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.day_frag_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dayViewModel = ViewModelProviders.of(this).get(DayViewModel::class.java)
        this.dayViewModel = dayViewModel
        dayViewModel.dayNewLiveData.observe(this, Observer { dayNewList ->
            if (dayNewList != null) {

            }
        })
        dayViewModel.getDayNewList(1)
    }
}