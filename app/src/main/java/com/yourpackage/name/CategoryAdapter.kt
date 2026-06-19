package com.yourpackage.name

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maw.R

class CategoryAdapter(
    private var list: List<Category>,
    private val onDeleteClicked: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.iv_category_icon)
        val name: TextView = view.findViewById(R.id.tv_category_name)
        val details: TextView = view.findViewById(R.id.tv_category_details)
        val deleteBtn: ImageButton = view.findViewById(R.id.btn_delete_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = list[position]
        holder.name.text = item.nameAr
        holder.details.text = "معرف القسم: ${item.id} | الترتيب: ${item.order}"
        holder.deleteBtn.setOnClickListener { onDeleteClicked(item) }
    }

    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<Category>) {
        val diffCallback = CategoryDiffCallback(this.list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.list = newList
        diffResult.dispatchUpdatesTo(this)
    }
}

class CategoryDiffCallback(
    private val oldList: List<Category>,
    private val newList: List<Category>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
