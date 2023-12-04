/**
 * Classe que representa um navio no jogo Batalha Naval.
 * Um navio possui um tamanho (tipo), uma orientação (vertical ou horizontal) e uma quantidade de vida.
 */
package br.ufrn.imd.modelo;

import javafx.scene.Parent;

/**
 * A classe Navio representa um navio no contexto do jogo Batalha Naval.
 */
public class Navio extends Parent {
    /**
     * O tipo do navio, representando seu tamanho.
     */
    public int tipo;

    /**
     * Indica se o navio está na orientação vertical.
     */
    public boolean vertical = true;

    /**
     * Representa a quantidade de vida restante do navio.
     */
    private int vida;

    /**
     * Construtor da classe Navio.
     *
     * @param tipo     O tipo do navio, representando seu tamanho.
     * @param vertical Indica se o navio está na orientação vertical.
     */
    public Navio(int tipo, boolean vertical) {
        this.tipo = tipo;
        this.vertical = vertical;
        vida = tipo;
    }

    /**
     * Método chamado quando o navio é atacado, diminuindo sua vida.
     * Este método representa o efeito de um ataque no navio, reduzindo sua vida.
     */
    public void ataque() {
        vida--;
    }

    /**
     * Verifica se o navio ainda está vivo.
     *
     * @return true se o navio ainda tem vida, false caso contrário.
     */
    public boolean ehVivo() {
        return vida > 0;
    }
}
