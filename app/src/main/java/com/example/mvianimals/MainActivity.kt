package com.example.mvianimals


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvianimals.api.AnimalService
import com.example.mvianimals.view.AnimalListAdapter
import com.example.mvianimals.view.MainIntent
import com.example.mvianimals.view.MainState
import com.example.mvianimals.view.MainViewModel
import com.example.mvianimals.view.ViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private var adapter=AnimalListAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpUI()
        setUpObservables()
    }
    private fun setUpUI() {

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        mainViewModel = ViewModelProvider(this, ViewModelFactory(AnimalService.api))
            .get(MainViewModel::class.java)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        recyclerView.adapter=adapter
        findViewById<Button>(R.id.buttonFetchAnimals).setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchAnimals)
            }
        }
    }
    private fun setUpObservables(){
     lifecycleScope.launch {
         mainViewModel.state.collect{collector->
             when(collector){
                 is MainState.Idle->{

                 }
                 is MainState.Loading->{
                     findViewById<Button>(R.id.buttonFetchAnimals).visibility= View.GONE
                     findViewById<ProgressBar>(R.id.progressBar).visibility=View.VISIBLE
                 }
                 is MainState.Animals->{
                     findViewById<ProgressBar>(R.id.progressBar).visibility=View.GONE
                     findViewById<Button>(R.id.buttonFetchAnimals).visibility=View.GONE
                     findViewById<RecyclerView>(R.id.recyclerView).visibility=View.VISIBLE
                     collector.animals.let {
                         adapter.newAnimal(it)
                     }
                 }
                 is MainState.Error->{
                     findViewById<ProgressBar>(R.id.progressBar).visibility=View.GONE
                     findViewById<Button>(R.id.buttonFetchAnimals).visibility=View.GONE
                     Toast.makeText(this@MainActivity, collector.error, Toast.LENGTH_SHORT).show()

                 }
             }
         }
     }
    }
}