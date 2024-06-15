package com.svape.schools.iu.town

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.svape.schools.R
import com.svape.schools.data.Town
import com.svape.schools.iu.institutions.InstitutionsActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TownAdapter
    private val towns = mutableListOf<Town>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        adapter = TownAdapter(towns) { town ->
            val intent = Intent(this, InstitutionsActivity::class.java).apply {
                putExtra("townId", town.dane)
            }
            startActivity(intent)
        }

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        fetchTowns()
    }

    private fun fetchTowns() {
        val client = OkHttpClient()

        val json = JSONObject().apply {
            put("User", "etraining")
            put("Password", "explorandoando2020%")
            put("option", "municipios")
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
                            val jsonArray = jsonObject.getJSONArray("data")

                            for (i in 0 until jsonArray.length()) {
                                val townObject = jsonArray.getJSONObject(i)
                                val name = townObject.getString("nombre")
                                val dane = townObject.getString("dane")
                                towns.add(Town(dane, name, dane))
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