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
import uz.androbeck.kursvalyuta.utils.Helper
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
                if (data.buyUsd == "0" && data.saleUsd == "0")
                    rlUsd.visible(false)
                else {
                    rlUsd.visible(true)
                    tvBuyUsd.text = "${Helper.formatNumber(data.buyUsd.toInt())} so'm"
                    tvSaleUsd.text = "${Helper.formatNumber(data.saleUsd.toInt())} so'm"
                }
                if (data.buyEur == "0" && data.saleEur == "0")
                    rlEur.visible(false)
                else {
                    rlEur.visible(true)
                    tvBuyEur.text = "${Helper.formatNumber(data.buyEur.toInt())} so'm"
                    tvSaleEur.text = "${Helper.formatNumber(data.saleEur.toInt())} so'm"
                }
                if (data.buyGbp == "0" && data.saleGbp == "0")
                    rlGbp.visible(false)
                else {
                    rlGbp.visible(true)
                    tvBuyGbp.text = "${Helper.formatNumber(data.buyGbp.toInt())} so'm"
                    tvSaleGbp.text = "${Helper.formatNumber(data.saleGbp.toInt())} so'm"
                }
                if (data.buyChf == "0" && data.saleChf == "0")
                    rlChf.visible(false)
                else {
                    rlChf.visible(true)
                    tvBuyChf.text = "${Helper.formatNumber(data.buyChf.toInt())} so'm"
                    tvSaleChf.text = "${Helper.formatNumber(data.saleChf.toInt())} so'm"
                }
                if (data.buyJpy == "0" && data.saleJpy == "0")
                    rlJpy.visible(false)
                else {
                    rlJpy.visible(true)
                    tvBuyJpy.text = "${Helper.formatNumber(data.buyJpy.toInt())} so'm"
                    tvSaleJpy.text = "${Helper.formatNumber(data.saleJpy.toInt())} so'm"
                }
                if (data.buyRub == "0" && data.saleRub == "0")
                    rlRub.visible(false)
                else {
                    rlRub.visible(true)
                    tvBuyRub.text = "${Helper.formatNumber(data.buyRub.toInt())} so'm"
                    tvSaleRub.text = "${Helper.formatNumber(data.saleRub.toInt())} so'm"
                }
                if (data.buyUsdAtm == "0" && data.saleUsdAtm == "0")
                    rlUsdAtm.visible(false)
                else {
                    rlUsdAtm.visible(true)
                    tvBuyUsdAtm.text = "${Helper.formatNumber(data.buyUsdAtm.toInt())} so'm"
                    tvSaleUsdAtm.text = "${Helper.formatNumber(data.saleUsdAtm.toInt())} so'm"
                }
                if (data.buyChf == "0" && data.saleChf == "0"
                    && data.buyJpy == "0" && data.saleJpy == "0"
                    && data.buyRub == "0" && data.saleRub == "0"
                )
                    ivArrowDownAndUp.visible(false)
                else
                    ivArrowDownAndUp.visible(true)
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