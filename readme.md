### **Code Challenge da ArcTouch**

    Android App que consome a API da TMdb e cujo objetivo é manter os usuários 
    atualizados sobre filmes. Esse App foi desenvolvimento usando Clean Architecture 
    devido a sua caracterísca de produzir código testável e manutenível.

#### Como instalar

    A aplicação pode ser executada com facilidade apenas executando git clone do 
    projeto, realizando um git checkout development dentro da pasta do projeto,
    abrir ele no Android Studio 3.6 e executando-o em algum smartphone ou emulador Android.

#### Issues

- O Cache offline não funciona ao encerrar a aplicação. Ele foi tirado pois entraria
em conflito com a paginação.
- Algumas partes do código não foram refatoradas devido ao prazo de entrega
- O código não possui container de injeção de dependência (Dagger2), o que certamente
daria mais flexibilidade para o código, mas exigiria um prazo maior.

#### Melhorias

- Adicionar testes unitários
- Adicionar um container de inversão de controle

#### Stack

- MVVM
- Clean Architecture
- RxJava2
- LiveData
- Kotlin
- Retrofit
- Realm
- Stetho

#### Features

- Rotacionamento de tela
- Cache Offline de filmes (Até fechar a aplicação)
- Paginação de filmes
- Pesquisa de filmes
