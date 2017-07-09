package com.example.administrator.rssreader

import android.app.ProgressDialog
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.administrator.rssreader.Adapter.FeedAdapter
import com.example.administrator.rssreader.Common.HTTPDataHandler
import com.example.administrator.rssreader.Model.RootObject
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val RSS_LINK = "http://rss.nytimes.com/services/xml/rss/nyt/Science.xml"
    private val RSS_TO_JSON_API = "https://api.rss2json.com/v1/api.json?rss_url="
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbars.title = "Science News"
        setSupportActionBar(toolbars)

        val linearLayoutManager = LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false)
        recycle_view.layoutManager = linearLayoutManager

        loadRss()
    }

    private fun loadRss() {
        val loadRSSAsync = object:AsyncTask<String,String,String>(){
            internal var mDialog = ProgressDialog(this@MainActivity)
            override fun doInBackground(vararg p0: String): String {
                val result:String
                val http = HTTPDataHandler()
                result = http.getHTTPDataHandler(p0[0])
                return result
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                mDialog.dismiss()
                var rssObject:RootObject
                rssObject = Gson().fromJson<RootObject>(result,RootObject::class.java)
                val adapter = FeedAdapter(rssObject,this@MainActivity)
                recycle_view.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun onPreExecute() {
                super.onPreExecute()
                mDialog.setMessage("Loading..")
                mDialog.show()
            }
        }

        val url_get_data = StringBuilder(RSS_TO_JSON_API)
        url_get_data.append(RSS_LINK)
        loadRSSAsync.execute(url_get_data.toString())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.refresh_btn){
            loadRss()
        }
        return true
    }
}
