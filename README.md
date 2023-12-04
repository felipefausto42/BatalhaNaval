# Batalha Naval

## O jogo

O projeto Batalha Naval implementa o conhecido jogo de tabuleiro em Java utilizando a plataforma JavaFX. O objetivo deste projeto é proporcionar uma experiência interativa e envolvente do jogo, onde os jogadores podem posicionar seus navios estrategicamente e realizar ataques no tabuleiro do oponente.

## Setup 

Para conseguir rodar o projeto, é necessário possuir instalada uma versão do Java acima do [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html), com o qual o jogo foi criado. 

Além disso, é necessário ter instalado o [JavaFX](https://gluonhq.com/products/javafx/), pelo menos a partir da versão SDK 19.

### Execução

Exitem algumas formas de fazer o projeto ser executado:

#### **Arquivo Jar**

Se você tem configurado em seu sistema o javaw.exe para abrir arquivos .jar, é possível executar diretamente o arquivo BatalhaNaval.jar

#### Prompt de Comando

Acessando o diretório onde o projeto está localizado, digite os comandos abaixo:

**Compilação**
```
javac --module-path "C:\Caminho\Para\JavaFX\lib" -d bin src\application\Main.java src\br\ufrn\imd\modelo\Navio.java src\br\ufrn\imd\modelo\Tabuleiro.java
```
**Execução**
```
java --module-path "C:\Caminho\Para\JavaFX\lib" -cp bin application.Main
``` 

#### IDE

Também é possível abrir o jogo diretamente de qualquer IDE configurada para projetos JAVA. Usando o Eclipse de exemplo:

    1 - Abrir a pasta do projeto
    2 - Selecionar o arquivo src > application > Main.java
    3 - Selecionar a opção Run