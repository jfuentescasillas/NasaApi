package com.example.nasaapi.ui.fragment.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasaapi.R
import com.example.nasaapi.databinding.ItemNasaListBinding
import com.example.nasaapi.ui.data.model.Item

class ListAdapter(private var dataSet: List<Item>, private val context: Context): RecyclerView.Adapter<ListAdapter.ViewHolder>() {


    class ViewHolder(val binding: ItemNasaListBinding): RecyclerView.ViewHolder(binding.root)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        return ViewHolder(ItemNasaListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.apply {
            val item = dataSet[position]

            itemNasaListTextViewTitle.text = item.data.firstOrNull()?.title ?: "N/A"
            itemNasaListTextViewDescription.text = item.data.firstOrNull()?.description_508 ?: "N/A"

            Glide.with(context)
                    .load(item.links.firstOrNull()?.href?: "https://atrevete.academy/blog/wp-content/uploads/2020/11/404-error-page-found_41910-364.jpg") //"https://image.freepik.com/free-vector/page-found-error-404_23-2147508324.jpg")
                    .centerCrop()
                    .placeholder(R.drawable.ic__04)
                    .into(itemNasaListImageView)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size


    fun updateList(newList: List<Item>) {
        dataSet = newList
        notifyDataSetChanged()
    }
}
