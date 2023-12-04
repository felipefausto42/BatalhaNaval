/**
 * Classe principal que inicia o jogo Batalha Naval em JavaFX.
 */
package application;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import br.ufrn.imd.modelo.Navio;
import br.ufrn.imd.modelo.Tabuleiro;
import br.ufrn.imd.modelo.Tabuleiro.Celula;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.HBox;

/**
 * A classe principal que inicia o jogo Batalha Naval.
 * Esta classe estende a classe Application do JavaFX.
 */
public class Main extends Application {

    private boolean running = false;
    private Tabuleiro tabuleiroInimigo, tabuleiroJogador;

    private int qtdNavios = 4;

    private boolean rodadaInimigo = false;

    private Random random = new Random();

    /**
     * Método que cria o conteúdo da interface gráfica do jogo.
     * 
     * @return O nó raiz da interface gráfica.
     */
    private Parent createContent() {
        // Definindo os tamanhos mínimo e máximo para a quantidade de navios
        int minSize = 2;
        int maxSize = 6;

        qtdNavios = minSize;

        // Configurando o layout principal da aplicação
        BorderPane root = new BorderPane();
        root.setPrefSize(1000, 800);

        // Criando os tabuleiros para o jogador e o inimigo
        tabuleiroInimigo = new Tabuleiro(true, event -> {
            if (!running)
                return;

            Celula celula = (Celula) event.getSource();
            if (celula.foiAtingido)
                return;

            rodadaInimigo = !celula.atirar();

            if (tabuleiroInimigo.navios == 0) {
                System.out.println("VOCÊ VENCEU");
                System.exit(0);
            }

            if (rodadaInimigo)
                rodadaInimigo();
        });

        tabuleiroJogador = new Tabuleiro(false, event -> {
            if (running)
                return;

            Celula cell = (Celula) event.getSource();
            if (tabuleiroJogador.colocarNavio(new Navio(qtdNavios, event.getButton() == MouseButton.PRIMARY), cell.x,
                    cell.y)) {
                if (++qtdNavios == maxSize) {
                    iniciarJogo();
                }
            }
        });

        // Criando as labels para os tabuleiros
        Text labelTabuleiroInimigo = new Text("Tabuleiro Inimigo");
        labelTabuleiroInimigo.setStyle("-fx-font-size: 22;");

        Text labelTabuleiroJogador = new Text("Tabuleiro do Jogador");
        labelTabuleiroJogador.setStyle("-fx-font-size: 22;");

        // Adicionando os tabuleiros e labels ao layout principal
        VBox vboxLabels = new VBox(10, labelTabuleiroInimigo, tabuleiroInimigo, labelTabuleiroJogador,
                tabuleiroJogador);
        vboxLabels.setAlignment(Pos.CENTER);

        root.setCenter(vboxLabels);

        // Adicionando a legenda "COMANDOS" acima dos comandos
        Text labelComandos = new Text("COMANDOS");
        labelComandos.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        Text labelLegendas = new Text("Colocar navio (vertical):\n" + "Mouse Esquerdo\n\n"
                + "Colocar navio (horizontal):\n" + "Mouse Direito");
        labelLegendas.setStyle("-fx-font-size: 20;");

        // Adicionando as labels de comandos ao layout principal
        VBox vboxComandos = new VBox(10, labelComandos, labelLegendas);
        vboxComandos.setAlignment(Pos.TOP_LEFT);
        vboxComandos.setTranslateX(10);
        vboxComandos.setTranslateY(10);

        root.getChildren().add(vboxComandos);

        // Adicionando os quadrados coloridos abaixo dos comandos com texto
        Text labelLegendas1 = new Text("LEGENDAS");
        labelLegendas1.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        Rectangle navioAliadoSquare = new Rectangle(30, 30, Color.LIGHTBLUE);
        Rectangle tiroEmCheioSquare = new Rectangle(30, 30, Color.ORANGE);
        Rectangle tiroErradoSquare = new Rectangle(30, 30, Color.BLACK);

        Text textonavioAliado = new Text("Navio Aliado");
        Text textoTiroEmCheio = new Text("Tiro em Cheio");
        Text textoTiroErrado = new Text("Tiro Errado");

        // Ajustando o tamanho do texto
        textonavioAliado.setStyle("-fx-font-size: 18;");
        textoTiroEmCheio.setStyle("-fx-font-size: 18;");
        textoTiroErrado.setStyle("-fx-font-size: 18;");

        HBox navioAliadoBox = new HBox(10, navioAliadoSquare, textonavioAliado);
        HBox tiroEmCheioBox = new HBox(10, tiroEmCheioSquare, textoTiroEmCheio);
        HBox tiroErradoBox = new HBox(10, tiroErradoSquare, textoTiroErrado);

        VBox vboxSquares = new VBox(10, labelLegendas1, navioAliadoBox, tiroEmCheioBox, tiroErradoBox);
        vboxSquares.setAlignment(Pos.TOP_LEFT);
        vboxSquares.setTranslateX(10);
        vboxSquares.setTranslateY(120 + vboxComandos.getBoundsInParent().getHeight());

        // Adicionando as labels de legendas ao layout principal
        root.getChildren().add(vboxSquares);

        return root;
    }

    /**
     * Método que simula a rodada do inimigo, realizando ataques aleatórios.
     */
    private void rodadaInimigo() {
        while (rodadaInimigo) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            Celula cell = tabuleiroJogador.getCelula(x, y);
            if (cell.foiAtingido)
                continue;

            rodadaInimigo = cell.atirar();

            if (tabuleiroJogador.navios == 0) {
                System.out.println("VOCÊ FOI DERROTADO");
                System.exit(0);
            }
        }
    }

    /**
     * Método que inicia o jogo, colocando os navios inimigos no tabuleiro.
     */
    private void iniciarJogo() {
        int type = 5;

        while (type > 1) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if (tabuleiroInimigo.colocarNavio(new Navio(type, Math.random() < 0.5), x, y)) {
                type--;
            }
        }

        running = true;
    }

    /**
     * Método chamado ao iniciar a aplicação JavaFX.
     * 
     * @param primaryStage O palco principal da aplicação.
     * @throws Exception Exceção lançada em caso de erro.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Batalha Naval");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Método principal que inicia a aplicação.
     * 
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
