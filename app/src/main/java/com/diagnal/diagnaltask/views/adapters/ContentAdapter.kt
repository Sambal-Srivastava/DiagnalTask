package com.diagnal.diagnaltask.views.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diagnal.diagnaltask.R
import com.diagnal.diagnaltask.data.model.Content
import com.diagnal.diagnaltask.databinding.RowItemsBinding

class ContentAdapter(
    private var list: ArrayList<Content>,
    private var context: Context,
    private val listener: (data: Any) -> Unit
) : RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    private var mainList = list

    inner class ViewHolder(val binding: RowItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    //use notify = true when changes needed on adapter else false during testing
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: ArrayList<Content>, search: Boolean, notify: Boolean = true)
    {
        if (!search) {
            if (list.size == 0) {
                this.list = list
            } else {
                this.list.addAll(list)
            }
        } else {
            this.list = list
        }
        if (notify) {
            notifyDataSetChanged()
        }
    }


    fun updateListWithMain() {
        this.list = mainList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowItemsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.itemView.context)
            .load(
                holder.itemView.context.getResources()
                    .getIdentifier(
                        list[position].posterImage?.replace(".jpg", ""),
                        "drawable",
                        holder.itemView.context.getPackageName()
                    )
            )
            .placeholder(R.drawable.placeholder_for_missing_posters)
            .into(holder.binding.ivPoster)
        holder.binding.tvTitle.text = list[position].name

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun filterOld(text: String?) {
        val temp: ArrayList<Content> = ArrayList()
        for (data in mainList) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (data.name!!.toLowerCase().contains(text!!.trim().toString().toLowerCase())) {
                temp.add(data)
            }
        }
        //update recyclerview
//        this.updateList(temp, true)
    }

    fun filter(query: String) {
        val filteredList = if (query.length >= 3) {
            mainList.filter { contentItem ->
                val name = contentItem.name?.toLowerCase() ?: ""
                val lowerCaseQuery = query.toLowerCase()
                name.contains(lowerCaseQuery)
            }.map { contentItem ->
                val name = contentItem.name ?: ""
                val lowerCaseName = name.toLowerCase()
                val lowerCaseQuery = query.toLowerCase()
                val startIndex = lowerCaseName.indexOf(lowerCaseQuery)
                val endIndex = startIndex + query.length
                contentItem.copy(name = highlightText(name, startIndex, endIndex))
            }
        } else {
            mainList
        }

        updateList(filteredList as ArrayList<Content>, true, true)
    }


    fun highlightText(text: String, startIndex: Int, endIndex: Int): String? {
        val spannable = SpannableString(text)
        spannable.setSpan(
            BackgroundColorSpan(context.getColor(R.color.yellow)),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable.toString()
    }
}