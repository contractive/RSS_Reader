package com.example.administrator.rssreader.Model

import java.util.*

/**
 * Created by Administrator on 09/07/2017.
 */
data class Item(val title:String, val pubDate:String, val link:String,
                val guid:String, val author:String, val thumbnail:String, val description:String, val content:String, val enclosure:Object,val categoris:List<String>)
