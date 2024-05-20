package com.example.diccionario

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diccionario.databinding.MeaningRecyclerRowBinding

class MeaningAdapter(private var meaningList: List<Meaning> ) : RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>() {

    class MeaningViewHolder( private val binding : MeaningRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(meaning : Meaning){
            binding.partOfSpeechTextview.text = meaning.partOfSpeech
            binding.definitionsTextview.text = meaning.definitions.joinToString("\n\n") {
                var currentIndex = meaning.definitions.indexOf(it)
                (currentIndex + 1).toString() + ". " + it.definition.toString()
            }
            if(meaning.synonyms.isEmpty()){
                binding.synonimsTitleTextview.visibility = View.GONE
                binding.synonimsTextview.visibility = View.GONE
            }else{
                binding.synonimsTitleTextview.visibility = View.VISIBLE
                binding.synonimsTextview.visibility = View.VISIBLE

                binding.synonimsTextview.text = meaning.synonyms.joinToString(", ")
            }
            if (meaning.antonyms.isEmpty()){
                binding.antonymsTitleTextview.visibility = View.GONE
                binding.antonymsTextview.visibility = View.GONE
            }else{
                binding.antonymsTitleTextview.visibility = View.VISIBLE
                binding.antonymsTextview.visibility = View.VISIBLE

                binding.antonymsTextview.text = meaning.antonyms.joinToString(", ")
            }
        }
    }

    fun updateNewData(newMeaningList: List<Meaning>){
        meaningList = newMeaningList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val binding = MeaningRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeaningViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return meaningList.size
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        holder.bind(meaningList[position])
    }
}