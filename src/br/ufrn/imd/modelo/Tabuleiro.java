/**
 * Classe que representa o tabuleiro no jogo Batalha Naval.
 */
package br.ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A classe Tabuleiro representa o tabuleiro utilizado no jogo Batalha Naval.
 * Este tabuleiro contém células que podem conter navios e podem ser atacadas.
 */
public class Tabuleiro extends Parent {

    private VBox cols = new VBox();
    private boolean inimigo = false;
    public int navios = 4;

    /**
     * Construtor da classe Tabuleiro.
     *
     * @param enemy   Indica se o tabuleiro é do inimigo.
     * @param handler Manipulador de eventos de clique nas células do tabuleiro.
     */
    public Tabuleiro(boolean enemy, EventHandler<? super MouseEvent> handler) {
        this.inimigo = enemy;

        // Loop para criar as linhas (linhas de células) do tabuleiro
        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
            // Loop para criar as células em cada linha do tabuleiro
            for (int x = 0; x < 10; x++) {
                Celula c = new Celula(x, y, this);
                c.setOnMouseClicked(handler);
                row.getChildren().add(c);
            }
            // Adiciona a linha completa ao VBox (colunas do tabuleiro)
            cols.getChildren().add(row);
        }

        // Adiciona as colunas do tabuleiro ao nó raiz
        getChildren().add(cols);
    }

    /**
     * Coloca um navio no tabuleiro.
     *
     * @param navio O navio a ser colocado.
     * @param x     A coordenada x da célula de início.
     * @param y     A coordenada y da célula de início.
     * @return true se o navio foi colocado com sucesso, false caso contrário.
     */
    public boolean colocarNavio(Navio navio, int x, int y) {
        if (podeColocarNavio(navio, x, y)) {
            int length = navio.tipo;

            // Verifica se o navio está na orientação vertical
            if (navio.vertical) {
                // Loop para colocar o navio nas células consecutivas na vertical
                for (int i = y; i < y + length; i++) {
                    Celula cell = getCelula(x, i);
                    cell.navio = navio;
                    // Define a aparência visual da célula (somente se não for tabuleiro inimigo)
                    if (!inimigo) {
                        cell.setFill(Color.LIGHTBLUE);
                        cell.setStroke(Color.BLACK);
                    }
                }
            } else {
                // Loop para colocar o navio nas células consecutivas na horizontal
                for (int i = x; i < x + length; i++) {
                    Celula cell = getCelula(i, y);
                    cell.navio = navio;
                    // Define a aparência visual da célula (somente se não for tabuleiro inimigo)
                    if (!inimigo) {
                        cell.setFill(Color.LIGHTBLUE);
                        cell.setStroke(Color.BLACK);
                    }
                }
            }

            return true;
        }

        return false;
    }

    /**
     * Obtém a célula do tabuleiro nas coordenadas especificadas.
     *
     * @param x A coordenada x da célula.
     * @param y A coordenada y da célula.
     * @return A célula no local especificado.
     */
    public Celula getCelula(int x, int y) {
        return (Celula) ((HBox) cols.getChildren().get(y)).getChildren().get(x);
    }

    /**
     * Obtém os vizinhos da célula nas coordenadas especificadas.
     *
     * @param x A coordenada x da célula.
     * @param y A coordenada y da célula.
     * @return Um array de células vizinhas.
     */
    private Celula[] getVizinhos(int x, int y) {
        Point2D[] points = new Point2D[]{
                new Point2D(x - 1, y),
                new Point2D(x + 1, y),
                new Point2D(x, y - 1),
                new Point2D(x, y + 1)
        };

        List<Celula> neighbors = new ArrayList<Celula>();

        for (Point2D p : points) {
            if (isValidPoint(p)) {
                neighbors.add(getCelula((int) p.getX(), (int) p.getY()));
            }
        }

        return neighbors.toArray(new Celula[0]);
    }

    /**
     * Verifica se é possível colocar um navio nas coordenadas especificadas.
     *
     * @param navio O navio a ser colocado.
     * @param x     A coordenada x da célula de início.
     * @param y     A coordenada y da célula de início.
     * @return true se o navio pode ser colocado, false caso contrário.
     */
    private boolean podeColocarNavio(Navio navio, int x, int y) {
        int length = navio.tipo;

        // Verifica se o navio está na orientação vertical
        if (navio.vertical) {
            // Loop para verificar se as células estão livres para colocar o navio na vertical
            for (int i = y; i < y + length; i++) {
                if (!isValidPoint(x, i))
                    return false;

                Celula celula = getCelula(x, i);
                if (celula.navio != null)
                    return false;

                // Verifica os vizinhos para evitar sobreposição de navios
                for (Celula vizinho : getVizinhos(x, i)) {
                    if (!isValidPoint(x, i))
                        return false;

                    if (vizinho.navio != null)
                        return false;
                }
            }
        } else {
            // Loop para verificar se as células estão livres para colocar o navio na horizontal
            for (int i = x; i < x + length; i++) {
                if (!isValidPoint(i, y))
                    return false;

                Celula celula = getCelula(i, y);
                if (celula.navio != null)
                    return false;

                // Verifica os vizinhos para evitar sobreposição de navios
                for (Celula vizinho : getVizinhos(i, y)) {
                    if (!isValidPoint(i, y))
                        return false;

                    if (vizinho.navio != null)
                        return false;
                }
            }
        }

        return true;
    }

    /**
     * Verifica se um ponto (coordenadas x, y) está dentro dos limites do tabuleiro.
     *
     * @param point O ponto a ser verificado.
     * @return true se o ponto está dentro dos limites do tabuleiro, false caso contrário.
     */
    public boolean isValidPoint(Point2D point) {
        return isValidPoint(point.getX(), point.getY());
    }

    /**
     * Verifica se as coordenadas especificadas estão dentro dos limites do tabuleiro.
     *
     * @param x A coordenada x.
     * @param y A coordenada y.
     * @return true se as coordenadas estão dentro dos limites do tabuleiro, false caso contrário.
     */
    private boolean isValidPoint(double x, double y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    /**
     * A classe interna Celula representa uma célula no tabuleiro do jogo Batalha Naval.
     */
    public class Celula extends Rectangle {
        public int x, y;
        public Navio navio = null;
        public boolean foiAtingido = false;

        private Tabuleiro tabuleiro;

        /**
         * Construtor da classe Celula.
         *
         * @param x          A coordenada x da célula.
         * @param y          A coordenada y da célula.
         * @param tabuleiro  O tabuleiro ao qual a célula pertence.
         */
        public Celula(int x, int y, Tabuleiro tabuleiro) {
            super(30, 30);
            this.x = x;
            this.y = y;
            this.tabuleiro = tabuleiro;
            setFill(Color.web("#5153ed"));
            setStroke(Color.LIGHTGRAY);
        }

        /**
         * Realiza um ataque na célula, alterando sua aparência e verificando se um navio foi atingido.
         *
         * @return true se um navio foi atingido, false caso contrário.
         */
        public boolean atirar() {
            foiAtingido = true;
            setFill(Color.BLACK);

            if (navio != null) {
                navio.ataque();
                setFill(Color.ORANGE);
                if (!navio.ehVivo()) {
                    tabuleiro.navios--;
                }
                return true;
            }

            return false;
        }
    }
}
