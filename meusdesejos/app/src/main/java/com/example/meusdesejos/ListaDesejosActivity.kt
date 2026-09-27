package com.example.meusdesejos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meusdesejos.databinding.ActivityWishlistBinding
import com.example.meusdesejos.databinding.WishlistItemLayoutBinding
import java.text.NumberFormat
import java.util.Locale

val wishlistMock = listOf(
    WishlistItem(
        id = "1",
        nome = "Verity",
        detalhes = "Livro Suspense",
        loja = "Amazon",
        preço = 35.00,
        preçoantigo = 46.80,
        disponivel = "Disponível online",
        imageRes = R.drawable.verity,
        prioridade = true
    ),
    WishlistItem(
        id = "2",
        nome = "Blush Rhode",
        loja = "Sephora",
        preço = 420.00,
        imageRes = R.drawable.blush,
        disponivel = "Disponível online e físico"
    ),
    WishlistItem(
        id = "3",
        nome = "Tênis Casual",
        loja = "Vans",
        preço = 499.99,
        preçoantigo = 600.00,
        imageRes = R.drawable.tenis,
        prioridade = true
    ),
    WishlistItem(
        id = "4",
        nome = "Kit Wella",
        loja = "Sephora",
        preço = 300.00,
        preçoantigo = 590.00,
        imageRes = R.drawable.wella,
        disponivel = "Disponível online e físico"
    ),
    WishlistItem(
        id = "1",
        nome = "Verity",
        detalhes = "Livro Suspense",
        loja = "Amazon",
        preço = 35.00,
        preçoantigo = 46.80,
        disponivel = "Disponível online",
        imageRes = R.drawable.verity,
        prioridade = true
    ),
    WishlistItem(
        id = "2",
        nome = "Blush Rhode",
        loja = "Sephora",
        preço = 420.00,
        imageRes = R.drawable.blush,
        disponivel = "Disponível online e físico"
    ),
    WishlistItem(
        id = "3",
        nome = "Tênis Casual",
        loja = "Vans",
        preço = 499.99,
        preçoantigo = 600.00,
        imageRes = R.drawable.tenis,
        prioridade = true
    ),
    WishlistItem(
        id = "4",
        nome = "Kit Wella",
        loja = "Sephora",
        preço = 300.00,
        preçoantigo = 590.00,
        imageRes = R.drawable.wella,
        disponivel = "Disponível online e físico"
    )
)

class WishlistActivity : AppCompatActivity() {


    private lateinit var binding: ActivityWishlistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.itemCount.text = getString(R.string.item_count, wishlistMock.size)

        binding.listView.layoutManager = LinearLayoutManager(this)
        binding.listView.adapter = WishlistAdapter(wishlistMock)
    }
}


class WishlistAdapter(private val items: List<WishlistItem>) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    class ViewHolder(val itemBinding: WishlistItemLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val context: Context = itemBinding.root.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            WishlistItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

        holder.itemBinding.itemName.text = item.nome
        holder.itemBinding.itemStore.text = item.loja
        holder.itemBinding.itemPrice.text = currencyFormat.format(item.preço)
        holder.itemBinding.itemImage.setImageResource(item.imageRes)

        holder.itemBinding.root.setOnClickListener {
            val intent = Intent(holder.context, DetalhesProdutosActivity::class.java)
            intent.putExtra(DetalhesProdutosActivity.PRODUCT_ID_KEY, item.id)
            holder.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}

data class WishlistItem(
    val id: String,
    val nome: String,
    val loja: String,
    val preço: Double,
    val imageRes: Int,
    val detalhes: String? = null,
    val preçoantigo: Double? = null,
    val disponivel: String? = null,
    val prioridade: Boolean = false,
    val purchased: Boolean = false
)
