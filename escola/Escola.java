import java.util.ArrayList;
import java.util.List;

public class Escola {
    private List<Aluno> alunos;

    public Escola() {
        this.alunos = new ArrayList<>();
    }

    public void registrarAluno(Aluno aluno) {
        alunos.add(aluno);
    }

    public Aluno buscarAluno(String matricula) {
        for (Aluno aluno : alunos) {
            if (aluno.getMatricula().equals(matricula)) {
                return aluno;
            }
        }
        return null;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }
}
