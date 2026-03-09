import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class Desktop extends JFrame {
    private JDesktopPane desktop;
    private final Color VERDE_PETROLEO = new Color(0, 128, 128); // Fundo do print [cite: 38]
    private final Color CINZA_WINDOWS = new Color(192, 192, 192);
    private JLabel[] lblPistas = new JLabel[5];
    private final Color VERDE_DESATIVADO = new Color(0, 50, 0); // Verde bem escuro e opaco

    public Desktop() {
        setTitle("Detetive de Interface");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Área principal (Desktop)
        desktop = new JDesktopPane();
        desktop.setBackground(VERDE_PETROLEO);
        // use live drag so the window moves in real time instead of an outline
        desktop.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
        setContentPane(desktop);

        configurarBarraTarefas();
        configurarIconesLaterais();
        configurarVisorPistas();

        // Abre a janela de Bem-vindo centralizada [cite: 55]
        SwingUtilities.invokeLater(() -> abrirJanelaBemVindo());
    }

    private void configurarVisorPistas() {
        // Painel externo (A borda cinza 3D)
        JPanel visorExterno = new JPanel(null);
        visorExterno.setBounds(1050, 20, 200, 420);
        visorExterno.setBackground(CINZA_WINDOWS);
        visorExterno.setBorder(new BevelBorder(BevelBorder.RAISED));

        // Cabeçalho Azul com Ícone de Cadeado [cite: 57]
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBounds(5, 5, 190, 30);
        header.setBackground(new Color(0, 0, 128)); // Azul escuro clássico
        JLabel lblTitulo = new JLabel("🔒 Visor de Pistas");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 12));
        header.add(lblTitulo);
        visorExterno.add(header);

        // Barra de progresso (Simulada conforme o print)
        JLabel lblProg = new JLabel("Progresso: 0/5");
        lblProg.setBounds(10, 40, 180, 20);
        visorExterno.add(lblProg);

        JPanel barraFundo = new JPanel(null);
        barraFundo.setBounds(10, 60, 180, 20);
        barraFundo.setBackground(new Color(40, 44, 52));
        visorExterno.add(barraFundo);

        // Área de Fragmentos (Fundo Preto)
        JPanel areaFragmentos = new JPanel();
        areaFragmentos.setBounds(10, 90, 180, 240); // Aumentado para caber a lista
        areaFragmentos.setBackground(Color.BLACK);
        areaFragmentos.setBorder(new LineBorder(Color.GRAY));
        areaFragmentos.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10)); // Alinhamento à esquerda

        JLabel lblFragTitulo = new JLabel("FRAGMENTOS COLETADOS:");
        lblFragTitulo.setForeground(new Color(57, 255, 20)); // Verde Neon [cite: 30]
        lblFragTitulo.setFont(new Font("Monospaced", Font.BOLD, 10));
        lblFragTitulo.setPreferredSize(new Dimension(170, 20));
        areaFragmentos.add(lblFragTitulo);

        // Loop para criar as 5 pistas com estado inicial "???" [cite: 61]
        for (int i = 0; i < 5; i++) {
            lblPistas[i] = new JLabel("▢ > ???");
            lblPistas[i].setForeground(VERDE_DESATIVADO); // Baixa opacidade inicial
            lblPistas[i].setFont(new Font("Monospaced", Font.BOLD, 11));
            lblPistas[i].setPreferredSize(new Dimension(170, 25));
            areaFragmentos.add(lblPistas[i]);
        }

        visorExterno.add(areaFragmentos);

        // Caixa de Senha Descoberta (Inferior)
        JPanel boxSenha = new JPanel(null);
        boxSenha.setBounds(10, 340, 180, 60);
        boxSenha.setBackground(CINZA_WINDOWS);
        boxSenha.setBorder(new TitledBorder(new LineBorder(Color.GRAY), "SENHA DESCOBERTA"));

        JLabel lblSenha = new JLabel("??-??-??-??-??", SwingConstants.CENTER);
        lblSenha.setBounds(5, 15, 170, 40);
        lblSenha.setFont(new Font("Monospaced", Font.BOLD, 14));
        boxSenha.add(lblSenha);

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

            // abre janela adequada conforme nome
            if ("Bem-vindo".equals(nome)) {
                btn.addActionListener(e -> abrirJanelaBemVindo());
            }
            if ("Documentos".equals(nome)) {
                // Referenciando a nova classe JanelaDocumentos
                btn.addActionListener(e -> {
                    JanelaDocumentos janelaDoc = new JanelaDocumentos(this); // Passa o Desktop atual
                    desktop.add(janelaDoc); // Adiciona ao painel de camadas [cite: 19]
                    janelaDoc.setVisible(true);

                    try {
                        janelaDoc.setSelected(true); // Traz para a frente (foco)
                    } catch (java.beans.PropertyVetoException ex) {
                        ex.printStackTrace();
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
        // janela com suporte completo: redimensionar, minimizar (iconificar), maximizar
        // e fechar
        JInternalFrame janela = new JInternalFrame("Bem-vindo, Detetive", true, true, true, true);
        janela.setBounds(150, 50, 850, 550);
        janela.setLayout(new BorderLayout());
        janela.setBackground(Color.WHITE);
        janela.setResizable(true);
        janela.setIconifiable(true);
        janela.setMaximizable(true);
        janela.setClosable(true);

        // Painel de conteúdo customizado
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(Color.WHITE);
        conteudo.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Título central com ícone (Simulado) [cite: 48]
        JLabel lblBoasVindas = new JLabel("Bem-vindo, Detetive!", SwingConstants.CENTER);
        lblBoasVindas.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblBoasVindas.setFont(new Font("SansSerif", Font.BOLD, 24));
        conteudo.add(lblBoasVindas);
        conteudo.add(Box.createRigidArea(new Dimension(0, 20)));

        // Seção Missão (Fundo Azul Claro) [cite: 8]
        conteudo.add(criarCardInfo("🎯 MISSÃO:",
                "Descubra a senha do administrador explorando arquivos, e-mails e a lixeira do sistema.",
                new Color(240, 248, 255), new Color(100, 149, 237)));

        // Seção Como Jogar (Fundo Amarelo Claro) [cite: 11]
        conteudo.add(criarCardInfo("🔍 COMO JOGAR:",
                "• Clique nos ícones do desktop para abrir programas\n• Explore pastas e leia documentos\n• Verifique seus e-mails com atenção\n• Não esqueça de olhar na lixeira!\n• Resolva puzzles para descobrir fragmentos",
                new Color(255, 253, 208), new Color(240, 230, 140)));

        // Seção Dicas (Fundo Verde Claro) [cite: 66]
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

    /**
     * cria e exibe uma janela interna genérica com título e conteúdo simples
     */
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

        // removido o botão "Iniciar" conforme solicitado
        JLabel lblMissao = new JLabel("🔍 Missão: Encontre a senha do administrador explorando os arquivos");
        lblMissao.setBounds(100, 5, 600, 30);
        lblMissao.setForeground(new Color(50, 50, 50));
        barra.add(lblMissao);

        JLabel lblHora = new JLabel("14:45", SwingConstants.CENTER);
        lblHora.setBounds(1180, 5, 80, 30);
        lblHora.setBorder(new BevelBorder(BevelBorder.LOWERED));
        barra.add(lblHora);

        desktop.add(barra);
    }
}