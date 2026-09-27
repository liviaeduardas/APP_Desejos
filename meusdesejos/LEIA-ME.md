# Meus Desejos — como usar este projeto

Este projeto foi feito seguindo o mesmo estilo de código da sua aula
(ViewBinding + RecyclerView + Intent explícita + data class com `.copy()`),
só que aplicado ao app de Wishlist do seu print.

## Passo a passo

1. Abra o Android Studio e crie um novo projeto:
   **File → New → New Project → Empty Views Activity**
   - Name: `MeusDesejos`
   - Package name: `com.example.meusdesejos` (tem que ser exatamente esse,
     pois é o mesmo usado nos arquivos `.kt` deste zip)
   - Language: Kotlin

2. No arquivo `app/build.gradle.kts` do projeto criado, confira se existe:
   ```kotlin
   buildFeatures {
       viewBinding = true
   }
   ```
   Se não existir, adicione dentro do bloco `android { ... }`.
   Também garanta que existe a dependência do RecyclerView:
   ```kotlin
   implementation("androidx.recyclerview:recyclerview:1.3.2")
   ```
   (o arquivo `app/build.gradle.kts.exemplo`, dentro deste zip, mostra um exemplo completo)

3. Copie as pastas deste zip por cima do projeto novo, substituindo os arquivos:
   - `app/src/main/AndroidManifest.xml`
   - `app/src/main/java/com/example/meusdesejos/*.kt`
   - `app/src/main/res/layout/*.xml`
   - `app/src/main/res/drawable/*.xml`
   - `app/src/main/res/values/colors.xml`, `strings.xml`, `themes.xml`
     (pode substituir os que já existem — ou copiar o conteúdo de dentro
     deles para dentro dos arquivos que o Android Studio já criou)

4. Rode o app (Run ▶). A tela inicial é a `WishlistActivity` (Tela 1).

## Onde está cada requisito da atividade

- **Duas telas com Views/ViewGroups em XML**
  `activity_wishlist.xml` (LinearLayout, FrameLayout, RecyclerView, Space, TextView, ImageView)
  e `activity_product_detail.xml` (ScrollView, LinearLayout, TextView, ImageView, Button, View).

- **Navegação por Intent explícita com dados**
  Em `WishlistActivity.kt`, dentro do `WishlistAdapter`, o clique no card cria
  `Intent(holder.context, ProductDetailActivity::class.java)` e usa
  `putExtra(...)` para levar o **id** do produto. A `ProductDetailActivity`
  lê esse id com `intent.getStringExtra(...)`.

- **ViewBinding**
  As duas Activities e o Adapter usam só `binding.algumaCoisa`, sem nenhum
  `findViewById` — inclusive o item opcional "usar só ViewBinding" está
  atendido.

- **Interação que atualiza a interface**
  O botão "Marcar como Comprado", na Tela 2, chama
  `currentItem = currentItem.copy(purchased = true)` e depois `renderProduct()`,
  que muda o selo de status, o texto do botão e desabilita o botão.

- **data class imutável + valores opcionais**
  `WishlistItem` é uma `data class` com campos opcionais (`details`,
  `oldPrice`, `availability` são `String?`/`Double?` com valor padrão `null`).
  Em `ProductDetailActivity.renderProduct()`, cada campo opcional só aparece
  na tela quando não é `null`.

- **Dados mockados**
  A lista `wishlistMock`, no topo de `WishlistActivity.kt`, é 100% estática,
  sem API nem banco de dados.

- **(Opcional) Componente XML reutilizável**
  `badge_pill_layout.xml` é o "selo" (badge). Ele é inflado por código
  (`BadgePillLayoutBinding.inflate(...)`) dentro da função `addBadge(...)`
  em `ProductDetailActivity.kt`, e é reaproveitado tanto para o selo de
  prioridade quanto para o selo de status (Pendente/Comprado) — a cor e o
  texto mudam de acordo com o estado do produto.

## O que foi simplificado de propósito

- As imagens dos produtos são ícones simples desenhados em XML (vetor),
  não fotos reais — assim não precisamos de internet nem de arquivos de
  imagem grandes. Se quiser, você pode trocar `ic_product_placeholder`
  pelas fotos reais dos produtos (coloque um `.png`/`.jpg` na pasta
  `drawable` e troque o `android:src` nos layouts).
- Não foi implementado Fragment (esse item é opcional na atividade).
- A barra inferior (Coleções / Novo / Ajustes) do print não foi implementada,
  pois a atividade pede só duas telas navegáveis — ela é só visual.
