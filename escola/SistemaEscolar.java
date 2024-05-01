import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SistemaEscolar {
    private static Scanner scanner = new Scanner(System.in);
    private static Escola escola = new Escola();
    private static Planilha planilha = new Planilha();

    public static void main(String[] args) {
        System.out.println("### Bem-vindo ao Sistema Escolar ###");

        while (true) {
            exibirMenuPrincipal();
            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    cadastrarAluno();
                    break;
                case 2:
                    verificarListaAlunos();
                    break;
                case 3:
                    adicionarNota();
                    break;
                case 4:
                    trancarMatricula();
                    break;
                case 5:
                    destrancarMatricula();
                    break;
                case 6:
                    System.out.println("Saindo do Sistema Escolar. Até logo!");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\nSelecione uma opção:");
        System.out.println("1. Cadastrar aluno");
        System.out.println("2. Verificar lista de alunos e notas");
        System.out.println("3. Adicionar nota");
        System.out.println("4. Trancar matrícula");
        System.out.println("5. Destrancar matrícula");
        System.out.println("6. Sair");
    }

    private static int lerOpcao() {
        int opcao;
        while (true) {
            try {
                System.out.print("Opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer do scanner
                return opcao;
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida. Digite um número.");
                scanner.nextLine(); // Limpar o buffer do scanner
            }
        }
    }

    private static void cadastrarAluno() {
        System.out.println("\nDigite o nome do aluno:");
        String nome = scanner.nextLine();
        System.out.println("Digite a matrícula do aluno:");
        String matricula = scanner.nextLine();
        System.out.println("Digite a data de matrícula do aluno:");
        String dataMatricula = scanner.nextLine();

        Aluno aluno = new Aluno(nome, matricula);
        escola.registrarAluno(aluno);
        planilha.registrarAluno(aluno, dataMatricula);

        // Salva as informações do aluno na tabela "Novo Cliente"
        salvarInformacoesNoBancoDeDados(nome, matricula, 0.0, true, "");

        System.out.println("Aluno cadastrado com sucesso!");
    }

    private static void verificarListaAlunos() {
        planilha.imprimirPlanilha();
    }

    private static void adicionarNota() {
        System.out.println("\nDigite a matrícula do aluno:");
        String matricula = scanner.nextLine();
        Aluno aluno = escola.buscarAluno(matricula);

        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        System.out.println("Digite a disciplina:");
        String disciplina = scanner.nextLine();
        System.out.println("Digite a nota:");
        double nota;
        try {
            nota = scanner.nextDouble();
            scanner.nextLine(); // Limpar o buffer do scanner
        } catch (InputMismatchException e) {
            System.out.println("Nota inválida. Certifique-se de digitar um número válido.");
            return;
        }

        aluno.adicionarNota(disciplina, nota);
        planilha.adicionarNota(aluno, disciplina, nota);
        System.out.println("Nota adicionada com sucesso para o aluno " + aluno.getNome());

        // Salva as informações atualizadas do aluno na tabela "Novo Cliente"
        salvarInformacoesNoBancoDeDados(aluno.getNome(), aluno.getMatricula(), nota, aluno.isMatriculaAtiva(), disciplina);
    }

    private static void trancarMatricula() {
        System.out.println("\nDigite a matrícula do aluno:");
        String matricula = scanner.nextLine();
        Aluno aluno = escola.buscarAluno(matricula);

        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        aluno.trancarMatricula();
        System.out.println("Matrícula do aluno " + aluno.getNome() + " foi trancada.");

        // Salva as informações atualizadas do aluno na tabela "Novo Cliente"
        salvarInformacoesNoBancoDeDados(aluno.getNome(), aluno.getMatricula(), 0.0, false, "");
    }

    private static void destrancarMatricula() {
        System.out.println("\nDigite a matrícula do aluno:");
        String matricula = scanner.nextLine();
        Aluno aluno = escola.buscarAluno(matricula);

        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        aluno.destrancarMatricula();
        System.out.println("Matrícula do aluno " + aluno.getNome() + " foi destrancada.");

        // Salva as informações atualizadas do aluno na tabela "Novo Cliente"
        salvarInformacoesNoBancoDeDados(aluno.getNome(), aluno.getMatricula(), 0.0, true, "");
    }

    // Método para salvar as informações na tabela "Novo Cliente"
    private static void salvarInformacoesNoBancoDeDados(String nomeAluno, String matriculaAluno, double nota, boolean matriculaAtiva, String disciplina) {
        Connection conexao = null;
        try {
            // Carrega o driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Estabelece a conexão com o banco de dados
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root", "");

            // Verifica se o aluno já existe na tabela
            PreparedStatement stmtVerificar = conexao.prepareStatement("SELECT * FROM `Novo Cliente` WHERE matricula = ?");
            stmtVerificar.setString(1, matriculaAluno);
            ResultSet rs = stmtVerificar.executeQuery();

            if (rs.next()) {
                // Aluno já existe na tabela, então atualiza suas informações
                PreparedStatement stmtAtualizar = conexao.prepareStatement("UPDATE `Novo Cliente` SET nome = ?, nota = ?, matricula_ativa = ?, disciplina = ? WHERE matricula = ?");
                stmtAtualizar.setString(1, nomeAluno);
                stmtAtualizar.setDouble(2, nota);
                stmtAtualizar.setBoolean(3, matriculaAtiva);
                stmtAtualizar.setString(4, disciplina);
                stmtAtualizar.setString(5, matriculaAluno);
                stmtAtualizar.executeUpdate();
                System.out.println("Informações do aluno atualizadas com sucesso na tabela Novo Cliente.");
            } else {
                // Aluno não existe na tabela, então insere suas informações
                PreparedStatement stmtInserir = conexao.prepareStatement("INSERT INTO `Novo Cliente` (nome, matricula, nota, matricula_ativa, disciplina) VALUES (?, ?, ?, ?, ?)");
                stmtInserir.setString(1, nomeAluno);
                stmtInserir.setString(2, matriculaAluno);
                stmtInserir.setDouble(3, nota);
                stmtInserir.setBoolean(4, matriculaAtiva);
                stmtInserir.setString(5, disciplina);
                stmtInserir.executeUpdate();
                System.out.println("Informações do aluno inseridas com sucesso na tabela Novo Cliente.");
            }
        } catch (ClassNotFoundException ex) {
            // Captura exceção se o driver JDBC não for encontrado
            System.out.println("Driver do banco de dados não localizado: " + ex.getMessage());
        } catch (SQLException ex) {
            // Captura exceção se ocorrer um erro de SQL
            System.out.println("Ocorreu um erro ao acessar o banco: " + ex.getMessage());
        } finally {
            // Fecha a conexão com o banco de dados
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    // Captura exceção se ocorrer um erro ao fechar a conexão
                    System.out.println("Erro ao fechar a conexão: " + ex.getMessage());
                }
            }
        }
    }
}
