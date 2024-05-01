import java.util.HashMap;
import java.util.Map;

public class Boletim {
    private Map<String, Map<String, Double>> notas;
    private Map<String, Map<String, Boolean>> presencas;

    public Boletim() {
        this.notas = new HashMap<>();
        this.presencas = new HashMap<>();
    }

    public void adicionarNota(String matricula, String disciplina, double nota) {
        notas.computeIfAbsent(matricula, k -> new HashMap<>()).put(disciplina, nota);
    }

    public double getNota(String matricula, String disciplina) {
        Map<String, Double> alunoNotas = notas.get(matricula);
        if (alunoNotas != null) {
            return alunoNotas.getOrDefault(disciplina, 0.0);
        }
        return 0.0;
    }

    public void registrarPresenca(String matricula, String data, boolean presente) {
        presencas.computeIfAbsent(matricula, k -> new HashMap<>()).put(data, presente);
    }

    public boolean verificarPresenca(String matricula, String data) {
        Map<String, Boolean> alunoPresencas = presencas.get(matricula);
        if (alunoPresencas != null) {
            return alunoPresencas.getOrDefault(data, false);
        }
        return false;
    }
}
