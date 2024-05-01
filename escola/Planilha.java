import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Planilha {
    private List<Aluno> alunos;
    private Map<Aluno, Map<String, Double>> notas;
    private Map<Aluno, String> datasMatricula;

    public Planilha() {
        this.alunos = new ArrayList<>();
        this.notas = new HashMap<>();
        this.datasMatricula = new HashMap<>();
    }

    public void registrarAluno(Aluno aluno, String dataMatricula) {
        alunos.add(aluno);
        notas.put(aluno, new HashMap<>()); // Inicializa um mapa vazio para as notas do aluno
        datasMatricula.put(aluno, dataMatricula);
    }

    public void adicionarNota(Aluno aluno, String disciplina, double nota) {
        Map<String, Double> notasAluno = notas.get(aluno); // Obtém o mapa de notas do aluno
        if (notasAluno != null) {
            // Se o mapa de notas já existir para o aluno, simplesmente adicionamos a nova nota à disciplina correspondente
            notasAluno.put(disciplina, nota);
        } else {
            // Se o mapa de notas ainda não existir para o aluno, criamos um novo mapa e adicionamos a nota à disciplina correspondente
            notasAluno = new HashMap<>();
            notasAluno.put(disciplina, nota);
            notas.put(aluno, notasAluno);
        }
    }

    public double getNota(Aluno aluno, String disciplina) {
        Map<String, Double> notasAluno = notas.get(aluno);
        if (notasAluno != null && notasAluno.containsKey(disciplina)) {
            return notasAluno.get(disciplina);
        }
        return 0.0;
    }

    public String getDataMatricula(Aluno aluno) {
        return datasMatricula.get(aluno);
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void imprimirPlanilha() {
        System.out.println("#-------------------------------------------------#");
        System.out.println("|                PLANILHA DE ALUNOS               |");
        System.out.println("#-------------------------------------------------#");
        System.out.printf("| %-14s| %-11s| %-20s|\n", "Nome", "Matrícula", "Matrícula Ativa");
        System.out.println("#-------------------------------------------------#");
        for (Aluno aluno : alunos) {
            System.out.printf("| %-14s| %-11s| %-20s|\n", aluno.getNome(), aluno.getMatricula(), aluno.isMatriculaAtiva() ? "Sim" : "Não");
            System.out.println("#---------------|------------|----------------------#");
            System.out.printf("| %-14s| %-11s|\n", "Disciplinas", "Nota");
            System.out.println("#---------------|------------|----------------------#");
            Map<String, Double> notasAluno = notas.get(aluno);
            if (notasAluno != null) {
                for (Map.Entry<String, Double> entry : notasAluno.entrySet()) {
                    System.out.printf("| %-14s| %-11s|\n", entry.getKey(), entry.getValue());
                }
            }
            System.out.println("#-------------------------------------------------#");
        }
        System.out.println("| Fim da Planilha                                 |");
        System.out.println("#-------------------------------------------------#");
    }
}
