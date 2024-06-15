package com.svape.schools.iu.group

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.svape.schools.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.svape.schools.data.Group
import com.svape.schools.iu.group.detailgroup.GroupDetailActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class GroupsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GroupAdapter
    private val groups = mutableListOf<Group>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_grupos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        adapter = GroupAdapter(groups) { group ->
            val intent = Intent(this, GroupDetailActivity::class.java).apply {
                putExtra("groupId", group.id)
            }
            startActivity(intent)
        }
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        val siteId = intent.getStringExtra("siteId") ?: ""
        fetchGroups(siteId)
    }


    private fun fetchGroups(siteId: String) {
        val client = OkHttpClient()

        val json = JSONObject().apply {
            put("User", "etraining")
            put("Password", "explorandoando2020%")
            put("option", "grupos")
            put("CodSede", siteId)
        }

        val requestBody = json.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request = Request.Builder()
            .url("https://www.php.engenius.com.co/DatabaseIE.php")
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) throw IOException("Unexpected code $it")

                    val responseBody = it.body?.string()
                    val jsonObject = responseBody?.let { it1 -> JSONObject(it1) }

                    if (jsonObject != null) {
                        if (jsonObject.has("data")) {
                            val data = jsonObject.getJSONArray("data")

                            for (i in 0 until data.length()) {
                                val groupObject = data.getJSONObject(i)
                                val name = groupObject.getString("nombre")
                                val id = groupObject.getString("id")
                                val numGroup = groupObject.getString("numGrupo")
                                groups.add(Group(id, name, numGroup))
                            }

                            runOnUiThread {
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        })
    }
}