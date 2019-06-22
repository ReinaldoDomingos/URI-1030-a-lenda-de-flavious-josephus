import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    /**
     * Metodo principal a qual executara o código
     * - Será feita a leitura de quantos casos de testes executados
     * - Para cada cado de teste será lido dois inteiros n e k
     * n representa o numero de homens presos na caverna
     * k representa o salto que será a variação que irá ser percorrido
     * a cada escolha do proxima a se suicidar
     */

    public static void main(String[] args) throws IOException {
        Scanner leia = new Scanner(System.in);
        int nc = leia.nextInt();
        ArrayList<String> res = new ArrayList<>();
        ListaCircular circular = new ListaCircular<>();
        for (int i = 0; i < nc; i++) {
            int n = leia.nextInt();
            int k = leia.nextInt();
            for (int j = 0; j < n; j++) {
                circular.inserir(j + 1);
            }
            No ptr = circular.head;
            int pos = 0, t = circular.length, aux = 0;
            while (circular.length > 1) {
                if (pos % k == 0 && pos > 0) {
                    int key = (int) ptr.getKey();
                    circular.remover(key);
                }
                ptr = ptr.getNext();
                if (ptr.getKey() == null)
                    ptr = ptr.getNext();
                pos++;
                aux++;
            }
            res.add("Case " + (i + 1) + ": " + circular.head.getNext());
            circular = new ListaCircular<>();
        }
        for (String s : res) {
            System.out.println(s);
        }
    }

    /**
     * @param <T> Tipo que do objeto No armazenará
     *            No representa  um item de uma lista encadeada a qual possui uma chave (key)
     *            e um ponteiro para o proximo item (next)
     */
    static class No<T> {
        private T key;
        private No next;

        /**
         * Construtor da classe
         *
         * @param key -  valor/chave que o No contem
         */
        public No(T key) {
            this.key = key;
            this.next = null;
        }

        /**
         * @return Retorna valor/chave que o No contem
         */
        public T getKey() {
            return key;
        }

        /**
         * Coloca o valor key no atributo key
         *
         * @param key - valor/chave que o No contem
         */
        public void setKey(T key) {
            this.key = key;
        }

        /**
         * @return - Retorna o ponteiro para o Proximo No
         */
        public No getNext() {
            return next;
        }

        /**
         * Coloca o valor next para o atributo next
         *
         * @param next - Ponteiro para o Proximo No
         */
        public void setNext(No next) {
            this.next = next;
        }

        /**
         * Formata a impressão padrão para o No
         *
         * @return - Retorna String contendo o valor da key
         */
        @Override
        public String toString() {
            return String.valueOf(key);
        }
    }

    /**
     * @param <T> Tipo que o objeto da ListaCircular armazenará
     */
    static class ListaCircular<T extends Comparable<T>> {

        No<T> head = new No<>(null);
        int length = 0;

        /**
         * Construtor da classe Lista Circular
         */
        public ListaCircular() {
            head.setNext(head);
        }

        /**
         * Verifica se um No esta vazio
         *
         * @param ptr - Um Objeto do tipo No
         * @return - Retorna um booolean
         */
        private boolean isNull(No<T> ptr) {
            return (ptr == null);
        }

        /**
         * Insere o valor em um novo No e adiciona na lista
         *
         * @param valor - valor a ser atribuido ao novo No
         */
        public void inserir(T valor) {
            head.setKey(valor);
            No<T> novo = new No<>(valor);
            No<T> ptr = head.getNext();
            No<T> ant;
            if (ptr == head) {
                head.setNext(novo);
                novo.setNext(head);
                length++;
            } else {
                ant = buscaAnterior(valor);
                head.setKey(null);
                novo.setNext(ant.getNext());
                ant.setNext(novo);
                length++;
            }
        }

        /**
         * Busca um No com um valor valor e retorna o No anterior ao encontrado com o valor
         *
         * @param valor - Valor do No a ser buscado
         * @return - Retorna No anterior ao encontrado
         */
        private No buscaAnterior(T valor) {
            No<T> ant = head;
            No<T> ptr = this.head.getNext();
            while (ptr.getKey().compareTo(valor) < 0) {
                ant = ptr;
                ptr = ptr.getNext();
            }
            return ant;
        }

        /**
         * Remove um No com o valor valor da lista caso encontre
         *
         * @param valor - Valor do No a ser removido
         * @return - Retorna o No removido
         */
        public No<T> remover(T valor) {
            No<T> antRemovido = buscaAnterior(valor);
            No<T> noRemovido = null;
            if (!isNull(antRemovido)) {
                noRemovido = antRemovido.getNext();
                if (valor.compareTo(noRemovido.getKey()) == 0) {
                    No<T> proxRemovido = antRemovido.getNext().getNext();
                    antRemovido.setNext(proxRemovido);
                }
                length--;
            } else if (valor.compareTo(this.head.getKey()) == 0) {
                noRemovido = this.head;
                this.head = this.head.getNext();
                length--;
            } else {
                return null;
            }
            return noRemovido;
        }

        /**
         * Imprime os No que contem a lista
         */
        public void imprimeIterativo() {
            No<T> ptr = this.head.getNext();
            while (ptr != head) {
                if (ptr.getKey() == null) {
                    ptr = ptr.getNext();
                } else {
                    System.out.print(ptr.getKey() + " ");
                    ptr = ptr.getNext();
                }
            }
            System.out.println("\n");
        }

    }
}
