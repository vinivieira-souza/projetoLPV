import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class JanelaAnalise extends JInternalFrame {
    private JPanel painelAcao;
    private JButton btnAnalise;
    private Desktop mainDesktop;

    public JanelaAnalise(Desktop desktop) {
        super("sistema_log.txt", true, true, true, true);
        this.mainDesktop = desktop;

        setSize(550, 400);
        setLocation(200, 100);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(211, 211, 211)); // Fundo cinza claro

        // Borda amarela externa (simulando a janela ativa do seu print)
        setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2));

        configurarAreaDeTexto();
        configurarBotaoAnalise();
    }

    private void configurarAreaDeTexto() {
        String logs = "LOGS DO SISTEMA\n" +
                      "===================\n\n" +
                      "[2024-03-15] Acesso negado ao usuário \"visitante\"\n" +
                      "[2024-03-16] Tentativa de login do administrador\n" +
                      "[2024-03-17] Arquivo movido para lixeira\n" +
                      "[2024-03-18] Sistema iniciado com sucesso\n\n" +
                      "[SYSTEM_DUMP_HEX_START]\n" +
                      "00 45 12 88 FF 3A 5_ 00\n" +
                      "[SYSTEM_DUMP_HEX_END]\n\n" +
                      "> AVISO: Padrões irregulares detectados no bloco de memória 0x45.";

        JTextArea areaLogs = new JTextArea(logs);
        areaLogs.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaLogs.setEditable(false);
        areaLogs.setMargin(new Insets(15, 15, 15, 15));
        
        JScrollPane scroll = new JScrollPane(areaLogs);
        scroll.setBorder(new EmptyBorder(10, 10, 10, 10)); // Espaçamento entre texto e borda da janela
        scroll.setBackground(new Color(211, 211, 211));
        
        add(scroll, BorderLayout.CENTER);
    }

    private void configurarBotaoAnalise() {
        painelAcao = new JPanel(new BorderLayout());
        painelAcao.setBackground(new Color(211, 211, 211));
        painelAcao.setBorder(new EmptyBorder(0, 10, 15, 10));

        btnAnalise = new JButton("🔍 Executar Análise de Log");
        btnAnalise.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnAnalise.setBackground(Color.WHITE);
        btnAnalise.setFocusPainted(false);
        btnAnalise.setPreferredSize(new Dimension(0, 40));
        btnAnalise.setBorder(new LineBorder(Color.GRAY, 1));

        btnAnalise.addActionListener(e -> iniciarAnalise());

        painelAcao.add(btnAnalise, BorderLayout.CENTER);
        add(painelAcao, BorderLayout.SOUTH);
    }

    private void iniciarAnalise() {
        // Feedback visual imediato: Muda o botão para estado de carregamento
        btnAnalise.setText("⏳ Analisando blocos de memória...");
        btnAnalise.setEnabled(false);
        btnAnalise.setBackground(new Color(240, 240, 240));

        // Timer de 2.5 segundos para simular o processamento
        Timer timer = new Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarSucesso();
            }
        });
        timer.setRepeats(false); // Executa apenas uma vez
        timer.start();
    }

    private void mostrarSucesso() {
        // Remove o botão de análise
        painelAcao.removeAll();

        // Cria o card verde de sucesso fiel à image_4385dc
        JPanel cardSucesso = new JPanel(new GridLayout(2, 1));
        cardSucesso.setBackground(new Color(240, 255, 240)); // Fundo verde bem claro
        cardSucesso.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(40, 200, 40), 2), // Borda verde forte
            new EmptyBorder(5, 15, 5, 15) // Padding interno
        ));
        cardSucesso.setPreferredSize(new Dimension(0, 50));

        JLabel lblTitulo = new JLabel("✓ ANÁLISE CONCLUÍDA: PISTA ENCONTRADA!");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblTitulo.setForeground(new Color(0, 128, 0));

        JLabel lblSub = new JLabel("O fragmento foi adicionado ao seu painel.");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblSub.setForeground(new Color(0, 100, 0));

        cardSucesso.add(lblTitulo);
        cardSucesso.add(lblSub);

        painelAcao.add(cardSucesso, BorderLayout.CENTER);
        painelAcao.revalidate();
        painelAcao.repaint();

        // Janela popup verde e atualização do visor
        JOptionPane.showMessageDialog(this, "Pista encontrada: 5_", "✓ Sucesso", JOptionPane.INFORMATION_MESSAGE);
        if (mainDesktop != null) {
            mainDesktop.revelarPista(4, "5_"); // Atualiza o último índice (4) no Visor
        }
    }
}