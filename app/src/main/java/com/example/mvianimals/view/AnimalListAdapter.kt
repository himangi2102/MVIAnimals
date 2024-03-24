package com.example.mvianimals.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvianimals.R
import com.example.mvianimals.api.AnimalService
import com.example.mvianimals.model.Animal

class AnimalListAdapter(private val animals:ArrayList<Animal>):RecyclerView.Adapter<AnimalListAdapter.DataViewHolder>() {
    fun newAnimal(newAnimal:List<Animal>){
        animals.clear()
        animals.addAll(newAnimal)
        notifyDataSetChanged()
    }
    class DataViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(animal: Animal){
            itemView.findViewById<TextView>(R.id.animalName).text=animal.name
            itemView.findViewById<TextView>(R.id.animalLocation).text=animal.location
            val url=AnimalService.BASE_URL+animal.image
            Glide.with(itemView.findViewById<ImageView>(R.id.animalImage).context)
                .load(url)
                .into(itemView.findViewById(R.id.animalImage))

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.animal_item,parent, false
            )
        )
    }

    override fun getItemCount()=animals.size



    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(animals[position])
    }
}