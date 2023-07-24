package com.example.ive.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.ive.network.model.PhotoData

class PhotoDataCallBack (private val oldList: List<PhotoData>, private val newList: List<PhotoData>) : DiffUtil.Callback() {
    override fun getOldListSize() : Int{
        return oldList.size ?: 0
    }


    override fun getNewListSize(): Int{
        return newList.size ?: 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition].id == oldList[oldItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition] == oldList[oldItemPosition]
    }

}