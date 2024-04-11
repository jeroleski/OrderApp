package com.example.orderapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.orderapp.network.MenuDb
import com.example.orderapp.R
import com.example.orderapp.types.Category

/**
 * A fragment representing a list of Products.
 */
class ProductFragment : Fragment() {
    private var categoryIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            categoryIndex = it.getInt(ARG_CATEGORY_INDEX)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)
        view.findViewById<RecyclerView>(R.id.list)

        with(view.findViewById<RecyclerView>(R.id.list)) {
            //TODO set to per category
//            adapter = ProductRecyclerViewAdapter(MenuDb.menu.categories[categoryIndex].products)
            val allProducts = MenuDb.menu.categories.flatMap { c -> c.products}
            adapter = ProductRecyclerViewAdapter(allProducts)
        }

        return view
    }

    companion object {
        const val ARG_CATEGORY_INDEX = "category_index"

        @JvmStatic
        fun newInstance(category: Category) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CATEGORY_INDEX, category.id)
                }
            }
    }
}