package uz.androbeck.kursvalyuta.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.androbeck.kursvalyuta.adapter.model.MainModel
import uz.androbeck.kursvalyuta.databinding.ItemMainBinding

class MainAdapter(
    private val listener: MainAdapterListener
) : ListAdapter<MainModel, MainAdapter.MainViewHolder>(DIFF_JOB_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context)), listener)

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind()
    }

    inner class MainViewHolder
    constructor(
        private val binding: ItemMainBinding,
        private val listener: MainAdapterListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val data = getItem(adapterPosition)
            with(binding) {
                tvBankName.text = data.bankName
                tvUsaOlish.text = data.usaUzbValyutOlish
                tvEuroOlish.text = data.euroUzbValyutOlish
                tvBritishOlish.text = data.britishUzbValyutOlish
                tvUsaSotish.text = data.usaUzbValyutSotish
                tvEuroSotish.text = data.euroUzbValyutSotish
                tvBritishSotish.text = data.britishUzbValyutSotish
                if (adapterPosition != RecyclerView.NO_POSITION)
                    ivCalculate.setOnClickListener {
                        listener.clickCalculate(adapterPosition, data)
                    }
            }
        }
    }

    companion object {
        val DIFF_JOB_CALLBACK = object : DiffUtil.ItemCallback<MainModel>() {
            override fun areItemsTheSame(oldItem: MainModel, newItem: MainModel) =
                oldItem.bankName == newItem.bankName

            override fun areContentsTheSame(oldItem: MainModel, newItem: MainModel) =
                oldItem.bankName == newItem.bankName
        }
    }

    interface MainAdapterListener {
        fun clickCalculate(position: Int, data: MainModel)
    }
}