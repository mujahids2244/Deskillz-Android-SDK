package com.arhamsoft.deskilz.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.GridLayoutManager

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tablitsolutions.crm.activities.OnLoadMoreListener


class RecyclerViewLoadMoreScroll(private val mLayoutManager: LinearLayoutManager?) : RecyclerView.OnScrollListener() {
    private var visibleThreshold = 5
    private var mOnLoadMoreListener: OnLoadMoreListener? = null
    private var isLoading = false
    private var lastVisibleItem = 0
    private  var totalItemCount:Int = 0

    fun setLoaded() {
        isLoading = false
    }

    fun getLoaded(): Boolean {
        return isLoading
    }

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener?) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
      if (dy <= 0) return
        totalItemCount = mLayoutManager!!.itemCount
        if (mLayoutManager is StaggeredGridLayoutManager) {
            val lastVisibleItemPositions = (mLayoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
            // get maximum element within the list
            lastVisibleItem = getLastVisibleItem(lastVisibleItemPositions)
        } else if (mLayoutManager is GridLayoutManager) {
            lastVisibleItem = (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
        } else if (mLayoutManager is LinearLayoutManager) {
            lastVisibleItem = (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }
        if (!isLoading && totalItemCount == lastVisibleItem + visibleThreshold) {
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener?.onLoadMore()
            }
            isLoading = true
        }
    }

    fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

}