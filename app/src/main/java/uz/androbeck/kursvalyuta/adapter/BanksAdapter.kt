package uz.androbeck.kursvalyuta.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import uz.androbeck.kursvalyuta.adapter.model.BanksModel
import uz.androbeck.kursvalyuta.databinding.ItemBanksBinding

class BanksAdapter(
    private val listener: BanksAdapterListener
) : ListAdapter<BanksModel, BanksAdapter.BanksViewHolder>(DIFF_JOB_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BanksViewHolder(ItemBanksBinding.inflate(LayoutInflater.from(parent.context)), listener)

    override fun onBindViewHolder(holder: BanksViewHolder, position: Int) {
        holder.bind()
    }

    inner class BanksViewHolder
    constructor(
        private val binding: ItemBanksBinding,
        private val listener: BanksAdapterListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val data = getItem(adapterPosition)
            with(binding) {
                val imageLoader = ImageLoader.Builder(binding.root.context)
                    .componentRegistry {
                        add(SvgDecoder(binding.root.context))
                    }
                    .build()
                ivBankLogo.load(data.banksLogo, imageLoader) {
                    crossfade(true)
                    crossfade(1000)
                }
                tvBankName.text = data.bankName
                tvMoneySell.text = data.sellMoney
                tvMoneyBuy.text = data.buyMoney
                if (adapterPosition != RecyclerView.NO_POSITION)
                    root.setOnClickListener {
                        listener.itemClick(adapterPosition, data)
                    }
            }
        }
    }

    companion object {
        val DIFF_JOB_CALLBACK = object : DiffUtil.ItemCallback<BanksModel>() {
            override fun areItemsTheSame(oldItem: BanksModel, newItem: BanksModel) =
                oldItem.bankName == newItem.bankName

            override fun areContentsTheSame(oldItem: BanksModel, newItem: BanksModel) =
                oldItem.bankName == newItem.bankName
        }
    }

    interface BanksAdapterListener {
        fun itemClick(position: Int, data: BanksModel)
    }
}