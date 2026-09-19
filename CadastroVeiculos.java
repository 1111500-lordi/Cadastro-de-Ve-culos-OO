import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Banco de dados em memoria e regras de negocio do cadastro de veiculos.
 */
public class CadastroVeiculos {

    private static final int ANO_MINIMO = 1900;

    private final List<Veiculo> veiculos = new ArrayList<>();

    public int getAnoMaximo() {
        return LocalDate.now().getYear() + 1;
    }

    public int getAnoMinimo() {
        return ANO_MINIMO;
    }

    /**
     * Verifica se ja existe um veiculo cadastrado com a placa informada.
     */
    public boolean existePlaca(String placa) {
        return buscarPorPlaca(placa) != null;
    }

    /**
     * Valida se o ano esta dentro do intervalo permitido.
     */
    public boolean anoValido(int ano) {
        return ano >= ANO_MINIMO && ano <= getAnoMaximo();
    }

    /**
     * Cadastra o veiculo. Retorna false se a placa ja existir ou o ano for invalido.
     */
    public boolean cadastrar(Veiculo veiculo) {
        if (veiculo == null || existePlaca(veiculo.getPlaca()) || !anoValido(veiculo.getAno())) {
            return false;
        }
        veiculos.add(veiculo);
        return true;
    }

    /**
     * Retorna o veiculo com a placa informada ou null caso nao exista.
     */
    public Veiculo buscarPorPlaca(String placa) {
        if (placa == null) {
            return null;
        }
        String alvo = placa.trim().toUpperCase();
        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(alvo)) {
                return v;
            }
        }
        return null;
    }

    public List<Veiculo> listar() {
        return new ArrayList<>(veiculos);
    }

    public boolean estaVazio() {
        return veiculos.isEmpty();
    }

    public int quantidade() {
        return veiculos.size();
    }
}
