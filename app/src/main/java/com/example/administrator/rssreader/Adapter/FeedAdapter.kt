package com.example.administrator.rssreader.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.administrator.rssreader.Interface.ItemClickListener
import com.example.administrator.rssreader.Model.RootObject
import com.example.administrator.rssreader.R
import kotlinx.android.synthetic.main.item_list.*

/**
 * Created by Administrator on 09/07/2017.
 */
class FeedViewHolder(itemView:View):RecyclerView.ViewHolder(itemView),View.OnClickListener, View.OnLongClickListener
{
    val txtTitle:TextView
    val txtPubdate:TextView
    val txtContent:TextView

    private var itemClickListener : ItemClickListener?=null

    init {
        txtTitle = itemView.findViewById(R.id.txtTitle)
        txtPubdate = itemView.findViewById(R.id.txtPubDate)
        txtContent = itemView.findViewById(R.id.txtContent)
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener;
    }

    override fun onClick(p0: View?) {

        itemClickListener!!.onClick(p0,adapterPosition,false)
    }

    override fun onLongClick(p0: View?): Boolean {
        itemClickListener!!.onClick(p0,adapterPosition,true)
        return true
    }

}

class FeedAdapter(private val rssObject:RootObject, private val mContext:Context):RecyclerView.Adapter<FeedViewHolder>(){

    private val inflater:LayoutInflater

    init {
        inflater = LayoutInflater.from(mContext)
    }
    override fun getItemCount(): Int {
        return rssObject.items.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.txtTitle.text = rssObject.items[position].title
        holder.txtPubdate.text = rssObject.items[position].pubDate
        holder.txtContent.text = rssObject.items[position].content
        holder.setItemClickListener(ItemClickListener {view, positon, isLongClick ->
            if (!isLongClick){
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.items[position].link))
                mContext.startActivity(browserIntent)
            }
        })

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FeedViewHolder {
        val itemView = inflater.inflate(R.layout.item_list,parent,false)
        return FeedViewHolder(itemView)
    }

}