# Termo Ideal

Aplicativo Android de jogo de palavras inspirado no **Termo**. O jogador tenta descobrir a palavra secreta em até seis tentativas, soma pontos conforme o desempenho e sobe no ranking. Um perfil de administrador cadastra e gerencia os jogos (as palavras) disponíveis.

> Encontre a palavra ideal nesse desafio surreal!

## Sumário

- [Demonstração](#demonstração)
- [Telas](#telas)
- [Como o jogo funciona](#como-o-jogo-funciona)
- [Perfis e telas](#perfis-e-telas)
- [Sistema de pontuação](#sistema-de-pontuação)
- [Validação de palavras (API)](#validação-de-palavras-api)
- [Tecnologias](#tecnologias)
- [Como executar](#como-executar)

## Demonstração

<!-- TODO: colar aqui o link do vídeo de demonstração (YouTube, Google Drive, etc.). -->

📹 **Vídeo de demonstração:** [COLOQUE O LINK AQUI](https://link-do-video)

## Telas

### Geral

| Tela inicial | Login |
| :---: | :---: |
| <img width="250" alt="Tela inicial" src="https://github.com/user-attachments/assets/42ba40e0-7650-47aa-860b-034db384840e" /> | <img width="250" alt="Tela de login" src="https://github.com/user-attachments/assets/a6bc1da4-4484-4c55-b15e-3f2c0552c4b4" /> |

### Perfil Jogador

| Jogo diário | Jogos passados | Ranking |
| :---: | :---: | :---: |
| <img width="250" alt="Ranking do Admin" src="https://github.com/user-attachments/assets/f58dd9a1-ba00-4bf1-aea5-648894da322b" />| ![Jogos passados do jogador](imagens/jogador-jogos-passados.png) | ![Ranking do jogador](imagens/jogador-ranking.png) |

### Perfil Administrador

| Cadastrar palavras | Jogos passados | Ranking |
| :---: | :---: | :---: |
| ![Cadastrar palavras](imagens/admin-cadastrar.png) | ![Jogos passados do admin](imagens/admin-jogos-passados.png) | <img width="250" alt="Ranking do Admin" src="https://github.com/user-attachments/assets/09cd3156-06ef-4689-8f5f-f4722e6bf38c" /> |

## Como o jogo funciona

1. Na tela inicial, o usuário toca em **JOGAR**.
2. Na tela de login, escolhe como deseja entrar: **Admin** ou **Jogador**.
3. O administrador cadastra jogos, cada um com uma palavra secreta.
4. O jogador joga o jogo mais recente (o **jogo diário**) ou revisita jogos anteriores.
5. A cada tentativa, o jogo informa quais letras estão corretas, quais existem na palavra em outra posição e quais não existem.
6. Quanto menos tentativas o jogador usar para acertar, mais pontos ele ganha.

O **progresso do jogador é salvo**: ele pode sair do app e continuar de onde parou, sem perder tentativas nem pontuação. Os pontos acumulados definem a posição no ranking.

## Perfis e telas

### Telas comuns

| Tela | Descrição |
| --- | --- |
| **Tela inicial** | Apresentação do jogo e botão para entrar. |
| **Login** | O usuário escolhe como entrar: administrador ou jogador. |

### Perfil Administrador

| Tela | Descrição |
| --- | --- |
| **Cadastrar palavras** | Cria jogos cadastrando palavras e exclui jogos removendo palavras. |
| **Jogos passados** | Mostra todos os jogos já cadastrados. |
| **Ranking** | Permite acompanhar o progresso dos jogadores. |

### Perfil Jogador

| Tela | Descrição |
| --- | --- |
| **Jogo diário** | Permite jogar o último jogo cadastrado. |
| **Jogos passados** | Permite jogar e visualizar os jogos já jogados anteriormente. |
| **Ranking** | Mostra a posição do jogador e a dos demais participantes. |

A barra de navegação inferior muda conforme o perfil escolhido no login, e não aparece na tela inicial nem na de login.

## Sistema de pontuação

A pontuação depende da tentativa em que o jogador acerta a palavra:

| Tentativa | Pontos |
| :---: | :---: |
| 1ª | +500 |
| 2ª | +100 |
| 3ª | +70 |
| 4ª | +40 |
| 5ª | +25 |
| 6ª | +10 |

Os pontos são somados à pontuação total do jogador, que é usada para ordenar o ranking.

## Validação de palavras (API)

O app consulta uma **API de palavras** para confirmar que o texto digitado é uma palavra existente no **dicionário brasileiro**. A verificação acontece em dois momentos:

- na tela de **cadastrar palavras**, para que o administrador só cadastre palavras válidas;
- na tela do **jogo**, para que o jogador só envie tentativas que sejam palavras reais.

Repository: https://github.com/viniciusmesquitac/papalavras-server.git

## Tecnologias

- **Kotlin**
- **Jetpack Compose** (interface) e **Material 3**
- **Navigation Compose** (navegação entre telas)
- **Room** (banco de dados local) com **KSP**
- **ViewModel**, **StateFlow** e **coroutines**
- **API de palavras** para validação no dicionário brasileiro

Configuração do projeto: `minSdk` 24 e `targetSdk` 37.

## Como executar

**Pré-requisitos**

- Android Studio atualizado
- Um emulador ou celular Android (Android 7.0 ou superior)

**Passos**

1. Clone o repositório:

   ```bash
   git clone https://github.com/luanaSejanoski/TermoIdeal.git
   ```

2. Abra a pasta do projeto no Android Studio e aguarde o *Gradle Sync* terminar.
3. Selecione um emulador ou conecte um celular com a depuração USB ativada.
4. Clique em **Run**.

O banco de dados local é criado na primeira execução. Se o esquema do banco mudar entre versões do app, desinstale o app do aparelho antes de rodar de novo.


