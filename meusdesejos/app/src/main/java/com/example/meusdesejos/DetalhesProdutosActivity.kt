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

// Tela 2: mostra os detalhes do produto escolhido na Tela 1
class DetalhesProdutosActivity : AppCompatActivity() {

    // chave usada para levar o id do produto de uma tela para a outra
    companion object {
        const val PRODUCT_ID_KEY = "product_id"
    }

    private lateinit var binding: ActivityProductDetailBinding

    // guarda o produto atual; é "var" porque pode ser trocado por uma cópia
    // atualizada quando o usuário marcar como comprado (o data class continua imutável)
    private lateinit var currentItem: WishlistItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // recupera o id que veio da Tela 1 através da Intent
        val productId = intent.getStringExtra(PRODUCT_ID_KEY)

        // procura o produto correspondente na lista mock
        // se por algum motivo não achar, usa o primeiro item como segurança
        currentItem = wishlistMock.find { it.id == productId } ?: wishlistMock.first()

        // botão de voltar simplesmente fecha esta tela e retorna para a Tela 1
        binding.backButton.setOnClickListener {
            finish()
        }

        // interação principal da tela: ao clicar, atualiza os dados e redesenha a tela
        binding.markAsPurchasedButton.setOnClickListener {
            currentItem = currentItem.copy(purchased = true)
            renderProduct()
        }

        renderProduct()
    }

    // lê os dados de "currentItem" e escreve/atualiza as views da tela
    private fun renderProduct() {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

        binding.productName.text = currentItem.nome
        binding.productStore.text = getString(R.string.store_label, currentItem.loja)
        binding.currentPrice.text = currencyFormat.format(currentItem.preço)

        // "details" é opcional (String?): só mostramos a View se o valor existir
        if (currentItem.detalhes != null) {
            binding.productDetails.text = currentItem.detalhes
            binding.productDetails.visibility = View.VISIBLE
        } else {
            binding.productDetails.visibility = View.GONE
        }

        // "oldPrice" é opcional (Double?): só mostramos o preço riscado se ele existir
        if (currentItem.preçoantigo != null) {
            binding.oldPrice.text = currencyFormat.format(currentItem.preçoantigo)
            binding.oldPrice.paintFlags = binding.oldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            binding.oldPrice.visibility = View.VISIBLE
        } else {
            binding.oldPrice.visibility = View.GONE
        }

        // "availability" é opcional (String?): escondemos o card inteiro se não existir
        if (currentItem.disponivel != null) {
            binding.availability.text = currentItem.disponivel
            binding.availabilityCard.visibility = View.VISIBLE
        } else {
            binding.availabilityCard.visibility = View.GONE
        }

        // selo de prioridade alta -> só aparece quando highPriority = true
        binding.priorityBadgeContainer.removeAllViews()
        if (currentItem.prioridade) {
            addBadge(
                container = binding.priorityBadgeContainer,
                text = "⚠ Prioridade Alta",
                backgroundColorRes = R.color.pink_light,
                textColorRes = R.color.pink_dark
            )
        }

        // selo de status (Pendente / Comprado) + texto do botão, de acordo com "purchased"
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

    // Cria uma view a partir do componente reutilizável "badge_pill_layout.xml"
    // e adiciona dentro do container recebido. Isso evita repetir o mesmo XML várias vezes.
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
