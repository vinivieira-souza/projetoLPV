import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class TelaLogin extends JFrame {
    private JTextArea terminal;
    private JPanel painelCentral;
    private JLabel lblCursor;
    
    private final Color VERDE_NEON = new Color(57, 255, 20);
    private final Color FUNDO_DARK = new Color(13, 17, 23); 
    private final Color BORDA_PAINEL = new Color(110, 118, 129);

    public TelaLogin() {
        setTitle("Detetive de Interface - Inicialização");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);
        setLayout(null);

        configurarTerminal();
        configurarPainelCentral();
        configurarRodape();
        
        iniciarAnimacao();
    }

    private void configurarTerminal() {
        terminal = new JTextArea();
        terminal.setBounds(350, 50, 500, 150);
        terminal.setBackground(Color.BLACK);
        terminal.setForeground(VERDE_NEON);
        terminal.setFont(new Font("Monospaced", Font.PLAIN, 16));
        terminal.setEditable(false);
        add(terminal);
    }

    private void configurarPainelCentral() {
        painelCentral = new JPanel();
        painelCentral.setBounds(370, 220, 460, 360);
        painelCentral.setBackground(FUNDO_DARK);
        painelCentral.setBorder(new LineBorder(BORDA_PAINEL, 2));
        painelCentral.setLayout(null);
        painelCentral.setVisible(false);

        // Header do Painel
        JPanel headerBox = new JPanel();
        headerBox.setBounds(40, 30, 380, 40);
        headerBox.setBackground(FUNDO_DARK);
        headerBox.setBorder(new LineBorder(VERDE_NEON, 1));
        headerBox.setLayout(new GridBagLayout());
        
        JLabel titulo = new JLabel("||  DETETIVE DE INTERFACE  ||");
        titulo.setForeground(VERDE_NEON);
        titulo.setFont(new Font("Monospaced", Font.BOLD, 14));
        headerBox.add(titulo);
        painelCentral.add(headerBox);

        // Divisor
        JSeparator sep = new JSeparator();
        sep.setBounds(40, 85, 380, 2);
        sep.setForeground(BORDA_PAINEL);
        painelCentral.add(sep);

        // Campo USUÁRIO
        JLabel lblUserTag = new JLabel("USUÁRIO:");
        lblUserTag.setForeground(VERDE_NEON);
        lblUserTag.setBounds(40, 115, 80, 30);
        lblUserTag.setFont(new Font("Monospaced", Font.BOLD, 14));
        painelCentral.add(lblUserTag);

        JPanel boxUser = createInputBox("DETETIVE", 120, 115);
        lblCursor = new JLabel("|");
        lblCursor.setForeground(VERDE_NEON);
        lblCursor.setBounds(75, 0, 20, 30);
        lblCursor.setFont(new Font("Monospaced", Font.BOLD, 14));
        boxUser.add(lblCursor);
        painelCentral.add(boxUser);

        // Campo MISSÃO
        JLabel lblMissaoTag = new JLabel("MISSÃO:");
        lblMissaoTag.setForeground(VERDE_NEON);
        lblMissaoTag.setBounds(40, 165, 80, 30);
        lblMissaoTag.setFont(new Font("Monospaced", Font.BOLD, 14));
        painelCentral.add(lblMissaoTag);

        JPanel boxMissao = createInputBox("DESCOBRIR SENHA", 120, 165);
        painelCentral.add(boxMissao);

        // Botão INICIAR INVESTIGAÇÃO
        JButton btnIniciar = new JButton("[ INICIAR INVESTIGAÇÃO ]");
        btnIniciar.setBounds(100, 230, 260, 45);
        btnIniciar.setBackground(new Color(25, 35, 50));
        btnIniciar.setForeground(VERDE_NEON);
        btnIniciar.setFont(new Font("Monospaced", Font.BOLD, 13));
        btnIniciar.setFocusPainted(false);
        btnIniciar.setBorder(new LineBorder(new Color(50, 70, 100), 1));
        btnIniciar.addActionListener(e -> {
            new Desktop().setVisible(true);
            dispose(); 
        });
         
        btnIniciar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnIniciar.setBorder(new LineBorder(VERDE_NEON, 1)); }
            public void mouseExited(MouseEvent e) { btnIniciar.setBorder(new LineBorder(new Color(50, 70, 100), 1)); }
        });

        painelCentral.add(btnIniciar);

        JLabel lblDica = new JLabel("Pressione para começar a missão", SwingConstants.CENTER);
        lblDica.setForeground(new Color(0, 100, 0)); 
        lblDica.setBounds(0, 285, 460, 20);
        lblDica.setFont(new Font("Monospaced", Font.PLAIN, 12));
        painelCentral.add(lblDica);

        add(painelCentral);
    }

    private JPanel createInputBox(String texto, int x, int y) {
        JPanel box = new JPanel(null);
        box.setBounds(x, y, 300, 30);
        box.setBackground(new Color(17, 24, 39));
        box.setBorder(new LineBorder(new Color(45, 55, 72), 1));
        
        JLabel lblTexto = new JLabel(texto);
        lblTexto.setForeground(new Color(100, 150, 150)); 
        lblTexto.setBounds(10, 0, 280, 30);
        lblTexto.setFont(new Font("Monospaced", Font.BOLD, 14));
        
        box.add(lblTexto);
        return box;
    }

    private void configurarRodape() {
        String f = "Monospaced";
        JLabel v = new JLabel("VERSÃO: 2.0.1995");
        v.setBounds(50, 620, 200, 20);
        v.setForeground(new Color(0, 50, 0));
        add(v);

        JLabel m = new JLabel("MEMÓRIA: 640K OK");
        m.setBounds(530, 620, 200, 20);
        m.setForeground(new Color(0, 50, 0));
        add(m);

        JLabel s = new JLabel("STATUS: PRONTO");
        s.setBounds(950, 620, 200, 20);
        s.setForeground(new Color(0, 50, 0));
        add(s);
    }

    private void iniciarAnimacao() {
        String[] linhas = {
            "INICIALIZAÇÃO DO SISTEMA V.2.0",
            "Inicializando memória... OK",
            "Verificando integridade dos arquivos...",
            "Montando sistema de arquivos...",
            "Iniciando interface gráfica...",
            "Pronto."
        };

        Timer timerTerminal = new Timer(15, new ActionListener() {
            int l = 0, c = 0;
            public void actionPerformed(ActionEvent e) {
                if (l < linhas.length) {
                    if (c < linhas[l].length()) {
                        terminal.append(String.valueOf(linhas[l].charAt(c++)));
                    } else {
                        terminal.append("\n");
                        l++; c = 0;
                    }
                } else {
                    ((Timer)e.getSource()).stop();
                    painelCentral.setVisible(true);
                    iniciarCursor();
                }
            }
        });
        timerTerminal.start();
    }

    private void iniciarCursor() {
        new Timer(500, e -> lblCursor.setVisible(!lblCursor.isVisible())).start();
    }
}