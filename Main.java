import java.util.List;
import java.util.Scanner;

/**
 * Sistema de Cadastro de Veiculos (POO) - interacao por console.
 */
public class Main {

    private static final Scanner entrada = new Scanner(System.in);
    private static final CadastroVeiculos cadastro = new CadastroVeiculos();

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    cadastrarVeiculo();
                    break;
                case 2:
                    listarVeiculos();
                    break;
                case 3:
                    consultarVeiculo();
                    break;
                case 0:
                    System.out.println("\nEncerrando o sistema. Ate mais!");
                    break;
                default:
                    System.out.println("\nOpcao invalida! Tente novamente.");
            }
        } while (opcao != 0);

        entrada.close();
    }

    private static void exibirMenu() {
        System.out.println("\n======= Cadastro de Veiculos OO =======");
        System.out.println("1 - Cadastrar Veiculo");
        System.out.println("2 - Listar Veiculos");
        System.out.println("3 - Consultar Veiculo");
        System.out.println("0 - Sair");
        System.out.print("\nEscolha uma opcao: ");
    }

    private static int lerOpcao() {
        String texto = entrada.nextLine().trim();
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // ---------------- Opcao 1 ----------------
    private static void cadastrarVeiculo() {
        System.out.println("\n--- Cadastro de Veiculo ---");

        String marca = lerTextoObrigatorio("Marca: ");
        String modelo = lerTextoObrigatorio("Modelo: ");

        int ano = lerAnoValido();
        if (ano == -1) {
            System.out.println("Cadastro cancelado.");
            return;
        }

        String placa = lerPlacaDisponivel();
        if (placa == null) {
            System.out.println("Cadastro cancelado.");
            return;
        }

        Veiculo veiculo = new Veiculo(marca, modelo, ano, placa);
        if (cadastro.cadastrar(veiculo)) {
            System.out.println("\nVeiculo cadastrado com sucesso!");
            System.out.println(veiculo);
        } else {
            System.out.println("\nNao foi possivel cadastrar o veiculo.");
        }
    }

    private static String lerTextoObrigatorio(String rotulo) {
        String valor;
        do {
            System.out.print(rotulo);
            valor = entrada.nextLine().trim();
            if (valor.isEmpty()) {
                System.out.println("O campo nao pode ficar em branco.");
            }
        } while (valor.isEmpty());
        return valor;
    }

    /**
     * Le o ano ate que seja valido. Retorna -1 se o usuario digitar 0 para cancelar.
     */
    private static int lerAnoValido() {
        while (true) {
            System.out.print("Ano (" + cadastro.getAnoMinimo() + " a " + cadastro.getAnoMaximo()
                    + ", ou 0 para cancelar): ");
            String texto = entrada.nextLine().trim();

            int ano;
            try {
                ano = Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero inteiro valido.");
                continue;
            }

            if (ano == 0) {
                return -1;
            }
            if (!cadastro.anoValido(ano)) {
                System.out.println("Ano invalido! Informe um valor entre "
                        + cadastro.getAnoMinimo() + " e " + cadastro.getAnoMaximo() + ".");
                continue;
            }
            return ano;
        }
    }

    /**
     * Le a placa ate que seja unica. Retorna null se o usuario digitar 0 para cancelar.
     */
    private static String lerPlacaDisponivel() {
        while (true) {
            System.out.print("Placa (ou 0 para cancelar): ");
            String placa = entrada.nextLine().trim().toUpperCase();

            if (placa.equals("0")) {
                return null;
            }
            if (placa.isEmpty()) {
                System.out.println("A placa nao pode ficar em branco.");
                continue;
            }
            if (cadastro.existePlaca(placa)) {
                System.out.println("Ja existe um veiculo cadastrado com esta placa!");
                continue;
            }
            return placa;
        }
    }

    // ---------------- Opcao 2 ----------------
    private static void listarVeiculos() {
        System.out.println("\n--- Veiculos Cadastrados ---");

        if (cadastro.estaVazio()) {
            System.out.println("Nenhum veiculo cadastrado ate o momento.");
            return;
        }

        List<Veiculo> lista = cadastro.listar();
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ") " + lista.get(i));
        }
        System.out.println("\nTotal: " + cadastro.quantidade() + " veiculo(s).");
    }

    // ---------------- Opcao 3 ----------------
    private static void consultarVeiculo() {
        System.out.println("\n--- Consulta por Placa ---");
        System.out.print("Informe a placa: ");
        String placa = entrada.nextLine().trim();

        Veiculo veiculo = cadastro.buscarPorPlaca(placa);
        if (veiculo == null) {
            System.out.println("Nenhum veiculo encontrado com a placa informada.");
        } else {
            System.out.println("Veiculo encontrado:");
            System.out.println(veiculo);
        }
    }
}
