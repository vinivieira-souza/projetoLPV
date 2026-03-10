import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class JanelaVitoria extends JInternalFrame {
    private Desktop mainDesktop;

    public JanelaVitoria(Desktop desktop) {
        // Título, resizable(false), closable(false) -> Obriga a clicar no botão de restart
        super("🎉 MISSÃO CONCLUÍDA!", false, false, false, false);
        this.mainDesktop = desktop;

        setSize(550, 480);
        // Centralizado manualmente na tela de 1280x720
        setLocation((1280 - 550) / 2, (720 - 480) / 2); 
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));

        configurarConteudo();
    }

    private void configurarConteudo() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(new Color(192, 192, 192)); // Cinza do Fundo
        content.setBorder(new EmptyBorder(20, 30, 20, 30));

        // 1. Ícone do Cadeado Aberto (Usando Unicode para simplificar)
        JLabel lblIcon = new JLabel("🔓", SwingConstants.CENTER);
        lblIcon.setFont(new Font("SansSerif", Font.PLAIN, 70));
        lblIcon.setForeground(new Color(255, 204, 0)); // Amarelo/Ouro
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 2. Títulos
        JLabel lblTitulo = new JLabel("ACESSO CONCEDIDO!", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblTitulo.setForeground(new Color(0, 0, 128)); // Azul Escuro
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Parabéns, Detetive! Você encontrou todos os fragmentos da senha.", SwingConstants.CENTER);
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSub.setForeground(new Color(60, 60, 60));
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 3. Caixa Preta com a Senha Final
        JPanel boxPreta = new JPanel();
        boxPreta.setLayout(new BoxLayout(boxPreta, BoxLayout.Y_AXIS));
        boxPreta.setBackground(Color.BLACK);
        boxPreta.setBorder(new LineBorder(Color.DARK_GRAY, 2));
        boxPreta.setMaximumSize(new Dimension(500, 100));

        JLabel lblBoxTitle = new JLabel("SENHA DO ADMINISTRADOR:");
        lblBoxTitle.setForeground(new Color(57, 255, 20)); // Verde Neon
        lblBoxTitle.setFont(new Font("Monospaced", Font.PLAIN, 12));
        lblBoxTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblBoxTitle.setBorder(new EmptyBorder(10, 0, 5, 0));

        JLabel lblSenha = new JLabel(" DF-5_-O9-TR-R3 ");
        lblSenha.setForeground(new Color(57, 255, 20));
        lblSenha.setFont(new Font("Monospaced", Font.BOLD, 28));
        lblSenha.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSenha.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(0, 0, 15, 0),
            new LineBorder(new Color(57, 255, 20), 1) // Borda verde interna
        ));

        boxPreta.add(lblBoxTitle);
        boxPreta.add(lblSenha);

        // 4. Painel de Estatísticas Brancas
        JPanel painelStats = new JPanel(new GridLayout(1, 3, 15, 0));
        painelStats.setBackground(new Color(192, 192, 192));
        painelStats.setMaximumSize(new Dimension(500, 60));

        painelStats.add(criarCardStat("5/5", "Pistas Encontradas"));
        painelStats.add(criarCardStat("100%", "Conclusão"));
        painelStats.add(criarCardStat("🏆", "Detetive Mestre"));

        // 5. Botão Jogar Novamente
        JButton btnJogar = new JButton("JOGAR NOVAMENTE");
        btnJogar.setFont(new Font("SansSerif", Font.PLAIN, 16));
        btnJogar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnJogar.setBackground(new Color(211, 211, 211));
        btnJogar.setFocusPainted(false);
        btnJogar.setMaximumSize(new Dimension(250, 40));
        btnJogar.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.GRAY, 1),
            BorderFactory.createBevelBorder(BevelBorder.RAISED) // Efeito 3D retro
        ));
        
        // Ação de Reiniciar (Chama o método no Desktop)
        btnJogar.addActionListener(e -> mainDesktop.reiniciarJogo());

        // Montagem Final (Adicionando espaços entre os elementos)
        content.add(Box.createRigidArea(new Dimension(0, 10)));
        content.add(lblIcon);
        content.add(Box.createRigidArea(new Dimension(0, 10)));
        content.add(lblTitulo);
        content.add(Box.createRigidArea(new Dimension(0, 10)));
        content.add(lblSub);
        content.add(Box.createRigidArea(new Dimension(0, 25)));
        content.add(boxPreta);
        content.add(Box.createRigidArea(new Dimension(0, 25)));
        content.add(painelStats);
        content.add(Box.createRigidArea(new Dimension(0, 25)));
        content.add(btnJogar);

        add(content, BorderLayout.CENTER);
    }

    private JPanel criarCardStat(String valor, String label) {
        JPanel p = new JPanel(new GridLayout(2, 1));
        p.setBackground(Color.WHITE);
        p.setBorder(new LineBorder(Color.GRAY, 1));

        JLabel lblVal = new JLabel(valor, SwingConstants.CENTER);
        lblVal.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblVal.setForeground(new Color(0, 0, 128)); // Azul

        JLabel lblText = new JLabel(label, SwingConstants.CENTER);
        lblText.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblText.setForeground(Color.DARK_GRAY);

        p.add(lblVal);
        p.add(lblText);
        return p;
    }
}