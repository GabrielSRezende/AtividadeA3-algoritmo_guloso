import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class OtimizacaoLogistica {
    public static void main(String[] args) {
        // Exemplo de entrada
        int[] volumesContainers = {40, 50, 60, 20, 30, 80, 70};
        int capacidadeArmazem = 100; // Capacidade dos armazéns
        int capacidadeCaminhao = 150; // Capacidade dos caminhões

        // Resolver o problema
        int numeroArmazens = alocarContainersEmArmazens(volumesContainers, capacidadeArmazem);
        int numeroCaminhoes = alocarArmazensEmCaminhoes(volumesContainers, capacidadeArmazem, capacidadeCaminhao);

        System.out.println("Número mínimo de armazéns necessários: " + numeroArmazens);
        System.out.println("Número mínimo de caminhões necessários: " + numeroCaminhoes);
    }

    // Função para alocar containers em armazéns usando First-Fit Decreasing
    public static int alocarContainersEmArmazens(int[] volumes, int capacidade) {
        Integer[] volumesOrdenados = new Integer[volumes.length];
        for (int i = 0; i < volumes.length; i++) {
            volumesOrdenados[i] = volumes[i];
        }
        Arrays.sort(volumesOrdenados, Comparator.reverseOrder());

        int[] armazens = new int[volumes.length];
        int contadorArmazens = 0;

        for (int volume : volumesOrdenados) {
            boolean alocado = false;
            for (int i = 0; i < contadorArmazens; i++) {
                if (armazens[i] + volume <= capacidade) {
                    armazens[i] += volume;
                    alocado = true;
                    break;
                }
            }
            if (!alocado) {
                armazens[contadorArmazens] = volume;
                contadorArmazens++;
            }
        }

        return contadorArmazens;
    }

    // Função para alocar armazéns em caminhões
    public static int alocarArmazensEmCaminhoes(int[] volumesContainers, int capacidadeArmazem, int capacidadeCaminhao) {
        Integer[] volumesOrdenados = new Integer[volumesContainers.length];
        for (int i = 0; i < volumesContainers.length; i++) {
            volumesOrdenados[i] = volumesContainers[i];
        }
        Arrays.sort(volumesOrdenados, Comparator.reverseOrder());

        // Usar lista dinâmica para armazenar as cargas dos armazéns
        ArrayList<Integer> cargasArmazensLista = new ArrayList<>();
        int cargaAtual = 0;

        for (int volume : volumesOrdenados) {
            if (cargaAtual + volume <= capacidadeArmazem) {
                cargaAtual += volume;
            } else {
                cargasArmazensLista.add(cargaAtual);
                cargaAtual = volume;
            }
        }
        cargasArmazensLista.add(cargaAtual); // Adicionar último armazém

        // Converter para array e ordenar
        Integer[] cargasArmazens = cargasArmazensLista.toArray(new Integer[0]);
        Arrays.sort(cargasArmazens, Comparator.reverseOrder());

        int[] caminhoes = new int[cargasArmazens.length];
        int contadorCaminhoes = 0;

        for (int carga : cargasArmazens) {
            boolean alocado = false;
            for (int j = 0; j < contadorCaminhoes; j++) {
                if (caminhoes[j] + carga <= capacidadeCaminhao) {
                    caminhoes[j] += carga;
                    alocado = true;
                    break;
                }
            }
            if (!alocado) {
                caminhoes[contadorCaminhoes] = carga;
                contadorCaminhoes++;
            }
        }
        return contadorCaminhoes;
    }
}
