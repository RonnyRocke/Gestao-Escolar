import java.util.HashMap;
import java.util.Map;

public class Aluno {
    private String nome;
    private String matricula;
    private Map<String, Double> notas;
    private boolean matriculaAtiva;

    public Aluno(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
        this.notas = new HashMap<>();
        this.matriculaAtiva = true;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public boolean isMatriculaAtiva() {
        return matriculaAtiva;
    }

    public void adicionarNota(String disciplina, double nota) {
        notas.put(disciplina, nota);
    }

    public double getNota(String disciplina) {
        return notas.getOrDefault(disciplina, 0.0);
    }

    public void trancarMatricula() {
        matriculaAtiva = false;
    }

    public void destrancarMatricula() {
        matriculaAtiva = true;
    }

    public Map<String, Double> getNotas() {
        return notas;
    }
}
