public class AlunoOnline extends Aluno implements Avaliavel {

    public AlunoOnline(String nome, int totalAulas) {
        super(nome, totalAulas);
    }

    @Override
    public String verificarResultado() {
        if (calcularMedia() >= 7) {
            return "Aprovado (Online)";
        } else {
            return "Reprovado (Online)";
        }
    }
}
