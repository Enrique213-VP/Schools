package com.svape.schools.iu.group.detailgroup

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.svape.schools.R
import android.widget.TextView
import com.svape.schools.data.GroupDetail
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class GroupDetailActivity : AppCompatActivity() {

    private lateinit var groupNameTextView: TextView
    private lateinit var siteNameTextView: TextView
    private lateinit var institutionNameTextView: TextView
    private lateinit var townNameTextView: TextView
    private lateinit var numGroupTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_grupo_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        groupNameTextView = findViewById(R.id.grupo_name)
        siteNameTextView = findViewById(R.id.sede_name)
        institutionNameTextView = findViewById(R.id.institucion_name)
        townNameTextView = findViewById(R.id.municipio_name)
        numGroupTextView = findViewById(R.id.num_grupo)

        val groupId = intent.getStringExtra("groupId") ?: ""
        fetchGroupDetails(groupId)
    }

    private fun fetchGroupDetails(groupId: String) {
        val client = OkHttpClient()

        val json = JSONObject().apply {
            put("User", "etraining")
            put("Password", "explorandoando2020%")
            put("option", "infoGrupo")
            put("IdGrupo", groupId)
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

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) throw IOException("Unexpected code $it")

                    val responseBody = it.body?.string()
                    val jsonObject = responseBody?.let { it1 -> JSONObject(it1) }

                    if (jsonObject != null) {
                        if (jsonObject.has("data")) {
                            val data = jsonObject.getJSONArray("data")
                            val groupDetails = data.getJSONObject(0)
                            val groupDetail = GroupDetail(
                                idGrupo = groupDetails.getString("id"),
                                group = groupDetails.getString("nombre"),
                                siteName = groupDetails.getString("sede"),
                                IEName = groupDetails.getString("institución"),
                                town = groupDetails.getString("municipio"),
                                numGroup = groupDetails.getString("numGrupo")
                            )

                            runOnUiThread {
                                groupNameTextView.text = groupDetail.group
                                siteNameTextView.text = groupDetail.siteName
                                institutionNameTextView.text = groupDetail.IEName
                                townNameTextView.text = groupDetail.town
                                numGroupTextView.text = groupDetail.numGroup
                            }
                        }
                    }
                }
            }
        })
    }
}