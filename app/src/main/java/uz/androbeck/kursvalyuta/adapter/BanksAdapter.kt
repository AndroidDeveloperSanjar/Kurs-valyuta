package uz.androbeck.kursvalyuta.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import uz.androbeck.kursvalyuta.R
import uz.androbeck.kursvalyuta.adapter.model.BanksModel
import uz.androbeck.kursvalyuta.databinding.ItemBanksBinding
import uz.androbeck.kursvalyuta.visible

@SuppressLint("SetTextI18n")
class BanksAdapter(
    private val listener: BanksAdapterListener
) : ListAdapter<BanksModel, BanksAdapter.BanksViewHolder>(DIFF_JOB_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BanksViewHolder(
            ItemBanksBinding.inflate(LayoutInflater.from(parent.context)),
            listener
        )

    override fun onBindViewHolder(holder: BanksViewHolder, position: Int) {
        holder.bind()
    }

    private var isDown = false

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
                tvBuyUsd.text = data.buyUsd
                tvSaleUsd.text = data.saleUsd
                tvBuyEur.text = data.buyEur
                tvSaleEur.text = data.saleEur
                tvBuyGbp.text = data.buyGbp
                tvSaleGbp.text = data.saleGbp
                tvBuyChf.text = data.buyChf
                tvSaleChf.text = data.saleChf
                tvBuyJpy.text = data.buyJpy
                tvSaleJpy.text = data.saleJpy
                tvBuyRub.text = data.buyRub
                tvSaleRub.text = data.buyRub
                tvBuyUsdAtm.text = data.buyUsdAtm
                tvSaleUsdAtm.text = data.saleUsdAtm
                ivArrowDownAndUp.setOnClickListener {
                    isDown = !isDown
                    if (isDown) {
                        ivArrowDownAndUp.setImageResource(R.drawable.ic_arrow_up)
                        llAllValyuta.visible(true)
                    } else {
                        ivArrowDownAndUp.setImageResource(R.drawable.ic_arrow_down)
                        llAllValyuta.visible(false)
                    }
                }
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