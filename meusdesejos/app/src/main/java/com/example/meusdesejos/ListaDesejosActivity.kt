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

// Lista de desejos "falsa" (mock), feita na mão.
// Serve para testarmos a tela sem precisar de internet ou banco de dados.
val wishlistMock = listOf(
    WishlistItem(
        id = "1",
        nome = "Verity",
        detalhes = "Livro Suspense",
        loja = "Amazon",
        preço = 35.00,
        preçoantigo = 46.80,
        disponivel = "Disponível online",
        prioridade = true
    ),
    WishlistItem(
        id = "2",
        nome = "Blush Hode",
        loja = "Sephora",
        preço = 420.00,
        disponivel = "Disponível online e físico"
    ),
    WishlistItem(
        id = "3",
        nome = "Tênis Casual",
        loja = "Vans",
        preço = 499.99,
        preçoantigo = 600.00,
        prioridade = true
    ),
    WishlistItem(
        id = "4",
        nome = "Kit Wella",
        loja = "Sephora",
        preço = 300.00,
        preçoantigo = 590.00,
        disponivel = "Disponível online e físico"
    )
)

// Tela 1: mostra a lista de produtos desejados
class WishlistActivity : AppCompatActivity() {

    // guarda a referência de todas as views da tela (gerado pelo ViewBinding)
    private lateinit var binding: ActivityWishlistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // infla o layout XML e transforma em objeto Kotlin
        binding = ActivityWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // escreve a quantidade de itens no topo da tela (ex: "4 itens")
        binding.itemCount.text = getString(R.string.item_count, wishlistMock.size)

        // configura a lista: como os itens se organizam (vertical) e quem desenha cada item
        binding.listView.layoutManager = LinearLayoutManager(this)
        binding.listView.adapter = WishlistAdapter(wishlistMock)
    }
}

// Adapter: decide como cada item da lista aparece na tela e o que acontece ao tocar nele.
// Ele herda de RecyclerView.Adapter e precisa implementar 3 funções.
class WishlistAdapter(private val items: List<WishlistItem>) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    // guarda a referência das views de UMA linha da lista
    class ViewHolder(val itemBinding: WishlistItemLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val context: Context = itemBinding.root.context
    }

    // função 1: cria uma "caixa" nova quando ainda não existe nenhuma para reciclar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            WishlistItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    // função 2: preenche uma caixa (nova ou reciclada) com os dados de uma posição
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

        holder.itemBinding.itemName.text = item.nome
        holder.itemBinding.itemStore.text = item.loja
        holder.itemBinding.itemPrice.text = currencyFormat.format(item.preço)

        // ao tocar no card, abre a Tela 2 levando o id do produto (Intent explícita)
        holder.itemBinding.root.setOnClickListener {
            val intent = Intent(holder.context, DetalhesProdutosActivity::class.java)
            intent.putExtra(DetalhesProdutosActivity.PRODUCT_ID_KEY, item.id)
            holder.context.startActivity(intent)
        }
    }

    // função 3: quantos itens existem no total (para a lista saber até onde rolar)
    override fun getItemCount(): Int = items.size
}

// Modelo de dados imutável (data class) que representa um item da wishlist.
// Campos com "?" e valor padrão "= null" são OPCIONAIS: nem todo produto precisa deles.
data class WishlistItem(
    val id: String,
    val nome: String,
    val loja: String,
    val preço: Double,
    val detalhes: String? = null,        // ex: "Matelassê Rosa Blush" (opcional)
    val preçoantigo: Double? = null,       // só existe quando o produto está com desconto (opcional)
    val disponivel: String? = null,   // ex: "Disponível física e online" (opcional)
    val prioridade: Boolean = false,
    val purchased: Boolean = false
)
