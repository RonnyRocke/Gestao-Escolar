public class Matricula {
    private boolean matriculaAtiva;

    public Matricula() {
        this.matriculaAtiva = true;
    }

    public boolean isMatriculaAtiva() {
        return matriculaAtiva;
    }

    public void trancarMatricula() {
        matriculaAtiva = false;
    }

    public void destrancarMatricula() {
        matriculaAtiva = true;
    }
}
