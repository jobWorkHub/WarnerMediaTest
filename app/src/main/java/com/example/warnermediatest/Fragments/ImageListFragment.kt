package com.example.warnermediatest.Fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warnermediatest.Networking.Model.ImageCell
import com.example.warnermediatest.Networking.NetworkManager
import com.example.warnermediatest.R
import com.example.warnermediatest.Adapters.RecyclerAdapter
import com.example.warnermediatest.ImageSize
import com.example.warnermediatest.Networking.Model.PhotoResults
import com.example.warnermediatest.Networking.Model.Photos

class ImageListFragment: Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var progressSpinner: ProgressBar
    var listContent: Photos? = null
    var pastSearchesAdapter: ArrayAdapter<String>? = null

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

        progressSpinner = view.findViewById(R.id.progress_spinner)

        // Set up list
        recyclerView = view.findViewById(R.id.image_list)
        recyclerAdapter = RecyclerAdapter()
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Fill the list with searched content if coming from the ViewImageFragment
        recyclerAdapter.submitList(listContent?.photo)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu?.findItem(R.id.search_bar)
        val searchView = searchItem?.actionView as AutoCompleteTextView
        pastSearchesAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, ArrayList<String>()) }
        searchView.setAdapter(pastSearchesAdapter)

        // Set up the search view to request images based of an inputed search term
        searchView.hint = "Search images"
        searchView.imeOptions = EditorInfo.IME_ACTION_SEARCH
        searchView.inputType = EditorInfo.TYPE_TEXT_VARIATION_FILTER
        searchView.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    if (v != null && v.text != null) {
                        pastSearchesAdapter?.add(v.text.toString())
                        progressSpinner.visibility = ProgressBar.VISIBLE
                        NetworkManager.getPhotos(v.text.toString()) { imageDetails ->
                            listContent = imageDetails.photos
                            listContent?.let{
                                recyclerAdapter.submitList(it.photo)
                            }
                            progressSpinner.visibility = ProgressBar.INVISIBLE
                        }
                    }
                }
                return true
            }
        })

        // Dismisses the keyboard when the textview loses focus
        searchView.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus){
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

}