package com.example.warnermediatest

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warnermediatest.Networking.Model.ImageCell
import com.example.warnermediatest.Networking.NetworkManager

class ImageListFragment: Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter
    var listContent: ArrayList<ImageCell>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.image_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.image_list)
        recyclerAdapter = RecyclerAdapter()
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        if (listContent != null){
            recyclerAdapter.submitList(listContent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu?.findItem(R.id.search_bar)
        val searchView = searchItem?.actionView as SearchView

        searchView.queryHint = "Search images"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                context?.let {
                    if (query != null) {
                        NetworkManager.getPhotos(query) {
                            listContent = it
                            recyclerAdapter.submitList(it)
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

}