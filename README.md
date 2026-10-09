Livia Eduarda - 843461
# Meus Desejos

Aplicativo Android para criar uma lista de produtos que o usuário deseja comprar.
O usuário pode visualizar os produtos, conferir seus detalhes e marcar os itens que já comprou.

## Objetivo do aplicativo
O app funciona como uma lista de desejos, onde é possível salvar produtos como roupas, perfumes, livros e outros itens.
O aplicativo possui duas telas:

### 1. Lista de Desejos
Mostra todos os produtos salvos, com foto, loja e preço.
Quando um produto é marcado como comprado, ele aparece com o texto riscado, a foto em preto e branco e o card mais apagado, facilitando a identificação dos itens já comprados.

### 2. Detalhes do Produto
Mostra mais informações sobre o produto selecionado, como preço atual, preço antigo, desconto, disponibilidade e prioridade.
Nessa tela, o usuário também pode marcar o produto como comprado.

## Como executar o projeto

### Requisitos
Android Studio Quail 3 (versão 2026.1.3) ou mais recente.
Conexão com a internet na primeira execução para baixar as ferramentas e bibliotecas necessárias.
Emulador Android Pixel 10 Pro ou outro dispositivo compatível.

### Passo a passo
1. Baixe o projeto usando o comando `[git clone <LINK-DO-REPOSITORIO>](https://github.com/liviaeduardas/APP_Desejos.git)`.
2. Abra o Android Studio e selecione **File > Open** para abrir a pasta do projeto.
3. Aguarde o Android Studio preparar o projeto e baixar os componentes necessários.
4. Se aparecer uma solicitação para instalar algum componente, clique em **Install/Accept**.
5. Crie ou selecione um emulador em **Tools > Device Manager**.
6. Clique em **Run** para executar o aplicativo.
A primeira tela exibida será a Lista de Desejos.

## Bibliotecas utilizadas
**AndroidX AppCompat:** ajuda a manter a compatibilidade do aplicativo com diferentes versões do Android.
**Material Components:** fornece componentes visuais, como o botão flutuante para adicionar produtos.
**AndroidX RecyclerView:** permite mostrar a lista de produtos de forma organizada e eficiente.
**AndroidX ConstraintLayout:** biblioteca incluída no template padrão, mas não utilizada diretamente nas telas.

## Decisões técnicas
**ViewBinding:** permite conectar os elementos do layout XML ao código Kotlin de forma mais simples.
**Data class:** usada para guardar as informações de cada produto, como nome, preço, detalhes e prioridade.
**Intent:** permite abrir a tela de detalhes e enviar o identificador do produto selecionado.
**Marcar como comprado:** quando o usuário marca um produto como comprado, o aplicativo atualiza seu estado e mostra a alteração na lista.
**Alteração visual:** os produtos comprados aparecem com o texto riscado, a foto em preto e branco e o card mais transparente. O aplicativo também restaura a aparência dos itens que ainda não foram comprados.
**Componentes reutilizáveis:** foi criado um layout para mostrar etiquetas, como “Prioridade Alta”, “Pendente” e “Comprado”.

## Estrutura principal do projeto
**ListaDesejosActivity.kt:** controla a tela com a lista de produtos.
**DetalhesProdutosActivity.kt:** controla a tela com as informações do produto e o botão para marcá-lo como comprado.
**res/layout/:** pasta que contém os arquivos XML responsáveis pela aparência das telas, dos produtos da lista e das etiquetas.
