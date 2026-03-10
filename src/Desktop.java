import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class Desktop extends JFrame {
    private JDesktopPane desktop;
    private final Color VERDE_PETROLEO = new Color(0, 128, 128);
    private final Color CINZA_WINDOWS = new Color(192, 192, 192);
    private JLabel[] lblPistas = new JLabel[5];
    private final Color VERDE_DESATIVADO = new Color(0, 50, 0);
    private int pistasContador = 0;
    private JLabel lblProgressoTexto;
    private JPanel barraVerde;
    private final int LARGURA_MAX_BARRA = 180;
    private JLabel lblMissaoRodape;
    private JLabel lblSenhaFinalVisor;

    public Desktop() {
        setTitle("Detetive de Interface");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktop = new JDesktopPane();
        desktop.setBackground(VERDE_PETROLEO);
        desktop.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
        setContentPane(desktop);

        configurarBarraTarefas();
        configurarIconesLaterais();
        configurarVisorPistas();

        SwingUtilities.invokeLater(() -> abrirJanelaBemVindo());
    }

    private void configurarVisorPistas() {
        JPanel visorExterno = new JPanel(null);
        visorExterno.setBounds(1050, 20, 200, 420);
        visorExterno.setBackground(CINZA_WINDOWS);
        visorExterno.setBorder(new BevelBorder(BevelBorder.RAISED));

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBounds(5, 5, 190, 30);
        header.setBackground(new Color(0, 0, 128));
        JLabel lblTitulo = new JLabel("🔒 Visor de Pistas");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 12));
        header.add(lblTitulo);
        visorExterno.add(header);

        lblProgressoTexto = new JLabel("Progresso: 0/5");
        lblProgressoTexto.setBounds(10, 40, 180, 20);
        visorExterno.add(lblProgressoTexto);

        JPanel barraFundo = new JPanel(null);
        barraFundo.setBounds(10, 60, LARGURA_MAX_BARRA, 20);
        barraFundo.setBackground(new Color(40, 44, 52));
        barraFundo.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        barraVerde = new JPanel();
        barraVerde.setBounds(0, 0, 0, 20);
        barraVerde.setBackground(new Color(57, 255, 20));

        barraFundo.add(barraVerde);
        visorExterno.add(barraFundo);

        JPanel areaFragmentos = new JPanel();
        areaFragmentos.setBounds(10, 90, 180, 240);
        areaFragmentos.setBackground(Color.BLACK);
        areaFragmentos.setBorder(new LineBorder(Color.GRAY));
        areaFragmentos.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));

        JLabel lblFragTitulo = new JLabel("FRAGMENTOS COLETADOS:");
        lblFragTitulo.setForeground(new Color(57, 255, 20));
        lblFragTitulo.setFont(new Font("Monospaced", Font.BOLD, 10));
        lblFragTitulo.setPreferredSize(new Dimension(170, 20));
        areaFragmentos.add(lblFragTitulo);

        for (int i = 0; i < 5; i++) {
            lblPistas[i] = new JLabel("▢ > ???");
            lblPistas[i].setForeground(VERDE_DESATIVADO);
            lblPistas[i].setFont(new Font("Monospaced", Font.BOLD, 11));
            lblPistas[i].setPreferredSize(new Dimension(170, 25));
            areaFragmentos.add(lblPistas[i]);
        }

        visorExterno.add(areaFragmentos);

        JPanel boxSenha = new JPanel(null);
        boxSenha.setBounds(10, 340, 180, 60);
        boxSenha.setBackground(CINZA_WINDOWS);
        boxSenha.setBorder(new TitledBorder(new LineBorder(Color.GRAY), "SENHA DESCOBERTA"));

        lblSenhaFinalVisor = new JLabel("??-??-??-??-??", SwingConstants.CENTER);
        lblSenhaFinalVisor.setBounds(5, 15, 170, 40);
        lblSenhaFinalVisor.setFont(new Font("Monospaced", Font.BOLD, 14));
        boxSenha.add(lblSenhaFinalVisor);

        visorExterno.add(boxSenha);
        desktop.add(visorExterno);
    }

    private void configurarIconesLaterais() {
        String[] nomes = { "Bem-vindo", "Documentos", "E-mail", "Lixeira", "Desafio", "Logs do Sistema" };
        int yIncial = 20;

        for (String nome : nomes) {
            JButton btn = new JButton(nome);
            btn.setBounds(10, yIncial, 100, 80);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setForeground(Color.WHITE);
            btn.setVerticalTextPosition(SwingConstants.BOTTOM);
            btn.setHorizontalTextPosition(SwingConstants.CENTER);
            btn.setFont(new Font("SansSerif", Font.PLAIN, 11));

            // Aqui você adicionaria o ícone depois: btn.setIcon(new
            // ImageIcon("caminho.png"));

            if ("Bem-vindo".equals(nome)) {
                btn.addActionListener(e -> abrirJanelaBemVindo());
            }
            if ("Documentos".equals(nome)) {
                btn.addActionListener(e -> {
                    JanelaDocumentos janelaDoc = new JanelaDocumentos(this);
                    desktop.add(janelaDoc);
                    janelaDoc.setVisible(true);

                    try {
                        janelaDoc.setSelected(true);
                    } catch (java.beans.PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                });
            }
            if ("E-mail".equals(nome)) {
                btn.addActionListener(e -> {
                    JanelaEmail janelaEmail = new JanelaEmail(this);
                    desktop.add(janelaEmail);
                    janelaEmail.setVisible(true);

                    try {
                        janelaEmail.setSelected(true);
                    } catch (java.beans.PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                });
            }
            if ("Lixeira".equals(nome)) {
                btn.addActionListener(e -> {
                    JanelaLixeira janelaLix = new JanelaLixeira(this);
                    desktop.add(janelaLix);
                    janelaLix.setVisible(true);

                    try {
                        janelaLix.setSelected(true);
                    } catch (java.beans.PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                });
            }
            if ("Desafio".equals(nome)) {
                btn.addActionListener(e -> {
                    JanelaDesafio janelaDes = new JanelaDesafio(this);
                    desktop.add(janelaDes);
                    janelaDes.setVisible(true);

                    try {
                        janelaDes.setSelected(true);
                    } catch (java.beans.PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                });
            }
            if ("Logs do Sistema".equals(nome)) {
                btn.addActionListener(e -> {
                    JanelaAnalise janelaLogs = new JanelaAnalise(this);
                    desktop.add(janelaLogs);
                    janelaLogs.setVisible(true);
                    try {
                        janelaLogs.setSelected(true);
                    } catch (Exception ex) {
                    }
                });
            } else {
                btn.addActionListener(e -> abrirJanelaGenerica(nome));
            }

            desktop.add(btn);
            yIncial += 90;
        }
    }

    private void abrirJanelaBemVindo() {
        JInternalFrame janela = new JInternalFrame("Bem-vindo, Detetive", true, true, true, true);
        janela.setBounds(150, 50, 850, 550);
        janela.setLayout(new BorderLayout());
        janela.setBackground(Color.WHITE);
        janela.setResizable(true);
        janela.setIconifiable(true);
        janela.setMaximizable(true);
        janela.setClosable(true);

        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(Color.WHITE);
        conteudo.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel lblBoasVindas = new JLabel("Bem-vindo, Detetive!", SwingConstants.CENTER);
        lblBoasVindas.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblBoasVindas.setFont(new Font("SansSerif", Font.BOLD, 24));
        conteudo.add(lblBoasVindas);
        conteudo.add(Box.createRigidArea(new Dimension(0, 20)));

        conteudo.add(criarCardInfo("🎯 MISSÃO:",
                "Descubra a senha do administrador explorando arquivos, e-mails e a lixeira do sistema.",
                new Color(240, 248, 255), new Color(100, 149, 237)));

        conteudo.add(criarCardInfo("🔍 COMO JOGAR:",
                "• Clique nos ícones do desktop para abrir programas\n• Explore pastas e leia documentos\n• Verifique seus e-mails com atenção\n• Não esqueça de olhar na lixeira!\n• Resolva puzzles para descobrir fragmentos",
                new Color(255, 253, 208), new Color(240, 230, 140)));

        conteudo.add(criarCardInfo("💡 DICAS:",
                "• Arquivos com ponto verde (!) contêm pistas\n• Acompanhe seu progresso no painel lateral direito\n• A senha final tem 5 fragmentos\n• Preste atenção em datas e números",
                new Color(240, 255, 240), new Color(144, 238, 144)));

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        janela.add(scroll, BorderLayout.CENTER);

        janela.setVisible(true);
        desktop.add(janela);
        try {
            janela.setSelected(true);
        } catch (Exception e) {
        }
    }

    private void abrirJanelaGenerica(String titulo) {
        JInternalFrame janela = new JInternalFrame(titulo, true, true, true, true);
        janela.setBounds(200, 100, 600, 400);
        janela.setLayout(new BorderLayout());
        janela.setBackground(Color.WHITE);
        janela.setResizable(true);
        janela.setIconifiable(true);
        janela.setMaximizable(true);
        janela.setClosable(true);

        JLabel conteudo = new JLabel("Conteúdo de " + titulo, SwingConstants.CENTER);
        janela.add(conteudo, BorderLayout.CENTER);

        janela.setVisible(true);
        desktop.add(janela);
        try {
            janela.setSelected(true);
        } catch (Exception e) {
        }
    }

    private JPanel criarCardInfo(String titulo, String texto, Color fundo, Color borda) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(fundo);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, borda),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        p.setMaximumSize(new Dimension(800, 150));

        JLabel lblT = new JLabel(titulo);
        lblT.setFont(new Font("SansSerif", Font.BOLD, 14));
        p.add(lblT, BorderLayout.NORTH);

        JTextArea txt = new JTextArea(texto);
        txt.setEditable(false);
        txt.setBackground(fundo);
        txt.setFont(new Font("SansSerif", Font.PLAIN, 12));
        p.add(txt, BorderLayout.CENTER);

        return p;
    }

    private void configurarBarraTarefas() {
        JPanel barra = new JPanel(null);
        barra.setBounds(0, 650, 1280, 40);
        barra.setBackground(CINZA_WINDOWS);
        barra.setBorder(new BevelBorder(BevelBorder.RAISED));

        lblMissaoRodape = new JLabel("🔍 Missão: Encontre a senha do administrador explorando os arquivos");
        lblMissaoRodape.setBounds(100, 5, 600, 30);
        lblMissaoRodape.setForeground(new Color(50, 50, 50));
        barra.add(lblMissaoRodape);

        JLabel lblHora = new JLabel("14:45", SwingConstants.CENTER);
        lblHora.setBounds(1180, 5, 80, 30);
        lblHora.setBorder(new BevelBorder(BevelBorder.LOWERED));
        barra.add(lblHora);

        desktop.add(barra);
    }

    public void revelarPista(int indice, String valor) {
        if (indice >= 0 && indice < lblPistas.length) {
            lblPistas[indice].setText("▣ > " + valor);
            lblPistas[indice].setForeground(new Color(57, 255, 20));

            pistasContador++;
            lblProgressoTexto.setText("Progresso: " + pistasContador + "/5");

            int novaLargura = (LARGURA_MAX_BARRA / 5) * pistasContador;

            barraVerde.setSize(novaLargura, 20);

            barraVerde.revalidate();
            barraVerde.repaint();

            if (pistasContador == 5) {
                finalizarJogo();
            }
        }
    }

    private void finalizarJogo() {
        lblSenhaFinalVisor.setText("DF-5_-O9-TR-R3");
        lblSenhaFinalVisor.setForeground(new Color(57, 255, 20));

        lblMissaoRodape.setText("✓ Missão concluída com sucesso!");
        lblMissaoRodape.setForeground(new Color(0, 150, 0));

        for (JInternalFrame frame : desktop.getAllFrames()) {
            frame.dispose();
        }

        JanelaVitoria vitoria = new JanelaVitoria(this);
        desktop.add(vitoria);
        vitoria.setVisible(true);
    }

    public void reiniciarJogo() {
        this.dispose();
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}