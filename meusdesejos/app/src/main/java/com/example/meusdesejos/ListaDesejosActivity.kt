package com.example.meusdesejos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.graphics.Paint
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meusdesejos.databinding.ActivityListaDesejosBinding
import com.example.meusdesejos.databinding.ItemListaDesejosLayoutBinding
import java.text.NumberFormat
import java.util.Locale

val wishlistMock = mutableListOf(
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
        id = "5",
        nome = "Amor Teoricamente",
        detalhes = "Livro Romance",
        loja = "Amazon",
        preço = 40.00,
        disponivel = "Disponível online",
        imageRes = R.drawable.livroromance,
        prioridade = true
    ),
    WishlistItem(
        id = "6",
        nome = "Vestido",
        loja = "Farm",
        preço = 390.00,
        imageRes = R.drawable.vestido,
        disponivel = "Disponível online e físico"
    ),
    WishlistItem(
        id = "7",
        nome = "Tênis Adidas",
        loja = "Adidas",
        preço = 699.99,
        preçoantigo = 800.00,
        imageRes = R.drawable.adidas,
        prioridade = true
    ),
    WishlistItem(
        id = "8",
        nome = "Kit Princípia",
        loja = "Amazon",
        preço = 70.00,
        imageRes = R.drawable.kitprinci,
        disponivel = "Disponível online"
    )
)

class WishlistActivity : AppCompatActivity() {


    private lateinit var binding: ActivityListaDesejosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityListaDesejosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.itemCount.text = getString(R.string.item_count, wishlistMock.size)

        binding.listView.layoutManager = LinearLayoutManager(this)
        binding.listView.adapter = WishlistAdapter(wishlistMock)
    }

    override fun onResume() {
        super.onResume()
        binding.listView.adapter?.notifyDataSetChanged()
    }
}


class WishlistAdapter(private val items: List<WishlistItem>) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    class ViewHolder(val itemBinding: ItemListaDesejosLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val context: Context = itemBinding.root.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemListaDesejosLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

        holder.itemBinding.itemName.text = item.nome
        holder.itemBinding.itemStore.text = item.loja
        holder.itemBinding.itemPrice.text = currencyFormat.format(item.preço)
        holder.itemBinding.itemImage.setImageResource(item.imageRes)

        if (item.purchased) {
            holder.itemBinding.itemName.paintFlags =
                holder.itemBinding.itemName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.itemBinding.itemName.paintFlags =
                holder.itemBinding.itemName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

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
