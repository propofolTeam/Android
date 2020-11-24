package com.example.propofolteam.view.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.propofolteam.R
import com.junhyuk.daedo.main.bottomItem.home.adapter.FeedAdapter
import com.junhyuk.daedo.main.bottomItem.home.model.FeedViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.feedRecyclerView.setHasFixedSize(true)

        val feedViewModel = ViewModelProvider(this).get(FeedViewModel::class.java)

        val feedAdapter = FeedAdapter(activity?.applicationContext!!)

        feedViewModel.feedPagedList.observe(viewLifecycleOwner, Observer {
            feedAdapter.submitList(it)
        })
        return view
    }
}