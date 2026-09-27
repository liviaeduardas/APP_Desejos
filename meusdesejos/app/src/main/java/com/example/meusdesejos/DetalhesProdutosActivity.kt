package com.example.meusdesejos

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.meusdesejos.databinding.ActivityProductDetailBinding
import com.example.meusdesejos.databinding.BadgePillLayoutBinding
import java.text.NumberFormat
import java.util.Locale


class DetalhesProdutosActivity : AppCompatActivity() {
    companion object {
        const val PRODUCT_ID_KEY = "product_id"
    }

    private lateinit var binding: ActivityProductDetailBinding

    private lateinit var currentItem: WishlistItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getStringExtra(PRODUCT_ID_KEY)

        currentItem = wishlistMock.find { it.id == productId } ?: wishlistMock.first()

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.markAsPurchasedButton.setOnClickListener {
            currentItem = currentItem.copy(purchased = true)
            renderProduct()
        }

        renderProduct()
    }

    private fun renderProduct() {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

        binding.productName.text = currentItem.nome
        binding.productStore.text = getString(R.string.store_label, currentItem.loja)
        binding.currentPrice.text = currencyFormat.format(currentItem.preço)
        binding.productImage.setImageResource(currentItem.imageRes)

        if (currentItem.detalhes != null) {
            binding.productDetails.text = currentItem.detalhes
            binding.productDetails.visibility = View.VISIBLE
        } else {
            binding.productDetails.visibility = View.GONE
        }

        if (currentItem.preçoantigo != null) {
            binding.oldPrice.text = currencyFormat.format(currentItem.preçoantigo)
            binding.oldPrice.paintFlags = binding.oldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            binding.oldPrice.visibility = View.VISIBLE
        } else {
            binding.oldPrice.visibility = View.GONE
        }

        if (currentItem.disponivel != null) {
            binding.availability.text = currentItem.disponivel
            binding.availabilityCard.visibility = View.VISIBLE
        } else {
            binding.availabilityCard.visibility = View.GONE
        }

        binding.priorityBadgeContainer.removeAllViews()
        if (currentItem.prioridade) {
            addBadge(
                container = binding.priorityBadgeContainer,
                text = "⚠ Prioridade Alta",
                backgroundColorRes = R.color.pink_light,
                textColorRes = R.color.pink_dark
            )
        }

        binding.statusBadgeContainer.removeAllViews()
        if (currentItem.purchased) {
            addBadge(binding.statusBadgeContainer, "✓ COMPRADO", R.color.green_light, R.color.green_dark)
            binding.markAsPurchasedButton.text = getString(R.string.already_purchased)
            binding.markAsPurchasedButton.isEnabled = false
        } else {
            addBadge(binding.statusBadgeContainer, "● PENDENTE", R.color.gray_light, R.color.gray_dark)
            binding.markAsPurchasedButton.text = getString(R.string.mark_as_purchased)
            binding.markAsPurchasedButton.isEnabled = true
        }
    }

    private fun addBadge(
        container: LinearLayout,
        text: String,
        backgroundColorRes: Int,
        textColorRes: Int
    ) {
        val badgeBinding = BadgePillLayoutBinding.inflate(LayoutInflater.from(this), container, false)
        badgeBinding.badgeText.text = text
        badgeBinding.root.backgroundTintList = ContextCompat.getColorStateList(this, backgroundColorRes)
        badgeBinding.badgeText.setTextColor(ContextCompat.getColor(this, textColorRes))
        container.addView(badgeBinding.root)
    }
}
