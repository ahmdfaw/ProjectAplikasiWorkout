package com.example.projectaplikasi

import android.content.Intent
import android.net.Uri
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class WorkoutAdapter(private val workoutList : ArrayList<Workout>) : RecyclerView.Adapter<WorkoutAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =  LayoutInflater.from(parent.context).inflate(R.layout.viewholder_workout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return workoutList.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = workoutList[position]

        holder.name.text = currentitem.name
        holder.duration.text = currentitem.duration

        Glide.with(holder.itemView.context)
            .load(currentitem.image)
            .into(holder.image)

        holder.playButton.setOnClickListener{
            val youtubeUrl = currentitem.youtubeUrl
            if (!youtubeUrl.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                holder.itemView.context.startActivity(intent)
            } else {
                Toast.makeText(
                    holder.itemView.context,
                    "Link Youtube Tidak Tersedia",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.nameTxt)
        val duration : TextView = itemView.findViewById((R.id.durasiTxt))
        val image : ImageView = itemView.findViewById(R.id.pic)
        val playButton : ImageButton = itemView.findViewById(R.id.playImg)
    }
}