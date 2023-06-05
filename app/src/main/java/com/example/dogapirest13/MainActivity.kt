package com.example.dogapirest13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var svDogs:SearchView;
    private lateinit var rvDogs:RecyclerView;

    private lateinit var adapter: DogsAdapter;
    private var dogImages: MutableList<String> = mutableListOf<String>();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        svDogs = findViewById(R.id.svDogs);
        svDogs.setOnQueryTextListener(this);
        initRecyclerView();
    }

    private fun initRecyclerView() {
        adapter = DogsAdapter(dogImages);
        rvDogs = findViewById(R.id.rvDogs);
        rvDogs.layoutManager = LinearLayoutManager(this);
        rvDogs.adapter = adapter;
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    private fun searchByName(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getDogsByBreeds("$query/images");
            val pupies = call.body();

            runOnUiThread {
                if (call.isSuccessful){
                    val images = pupies?.images ?: emptyList();
                    dogImages.clear();
                    dogImages.addAll(images);
                    adapter.notifyDataSetChanged();
                }else{
                    showError();
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(this, "error con la conexion", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()){
            searchByName(query.toLowerCase());
        }

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}