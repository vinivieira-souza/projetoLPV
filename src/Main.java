import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Inicia a aplicação na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            TelaLogin login = new TelaLogin();
            login.setVisible(true);
        });
    }
}