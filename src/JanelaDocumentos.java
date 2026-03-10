import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class JanelaDocumentos extends JInternalFrame {
    private JPanel painelConteudo;
    private JLabel lblCaminho;
    private Desktop mainDesktop;

    public JanelaDocumentos(Desktop desktop) {
        super("Documentos", true, true, true, true);
        this.mainDesktop = desktop;

        setSize(600, 450);
        setLocation(50, 50);
        setLayout(new BorderLayout());
        setBackground(new Color(192, 192, 192));

        configurarBarraCaminho();

        painelConteudo = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 20));
        painelConteudo.setBackground(Color.WHITE);
        add(new JScrollPane(painelConteudo), BorderLayout.CENTER);

        exibirDiretorioRoot();
    }

    private void configurarBarraCaminho() {
        JPanel barraCaminho = new JPanel(new FlowLayout(FlowLayout.LEFT));
        barraCaminho.setBackground(new Color(211, 211, 211));
        barraCaminho.setBorder(new BevelBorder(BevelBorder.LOWERED));

        lblCaminho = new JLabel("📁 Caminho: /root");
        lblCaminho.setFont(new Font("Monospaced", Font.PLAIN, 12));
        barraCaminho.add(lblCaminho);

        add(barraCaminho, BorderLayout.NORTH);
    }

    // --- ESTADOS DE NAVEGAÇÃO ---

    private void exibirDiretorioRoot() {
        limparEAtualizar("/root");
        adicionarElemento("Projetos", "pasta", e -> exibirDiretorioProjetos());
        adicionarElemento("Pessoal", "pasta", e -> exibirDiretorioPessoal());
        adicionarElemento("readme.txt", "arquivo", e -> abrirArquivoTexto("README", "BEM-VINDO AO SISTEMA\r\n" + //
                "\r\n" + //
                "Este é o diretório de documentos do administrador.\r\n" + //
                "Explore as pastas e arquivos para encontrar pistas."));
        adicionarElemento("acesso_negado.txt", "arquivo", e -> mostrarErroAcesso());
    }

    private void exibirDiretorioProjetos() {
        limparEAtualizar("/root/Projetos");
        adicionarElemento("Voltar", "voltar", e -> exibirDiretorioRoot());
        adicionarElemento("projeto_secreto.txt", "arquivo",
                e -> abrirArquivoTexto("Projeto Secreto", "PROJETO CONFIDENCIAL\r\n" + //
                        "\r\n" + //
                        "Código de acesso necessário.\r\n" + //
                        "\r\n" + //
                        "Formato: XX-XX-XX\r\n" + //
                        "\r\n" + //
                        "Primeira parte encontrada nos e-mails."));

        adicionarElemento("notas.txt", "pista", e -> abrirPista("R3"));
    }

    private void exibirDiretorioPessoal() {
        limparEAtualizar("/root/Pessoal");
        adicionarElemento("Voltar", "voltar", e -> exibirDiretorioRoot());
        adicionarElemento("lembretes.txt", "arquivo", e -> abrirArquivoTexto("Lembretes", "LEMBRETES PESSOAIS\r\n" + //
                "\r\n" + //
                "- Trocar senha a cada 30 dias\r\n" + //
                "- Não anotar senhas em papel\r\n" + //
                "- Usar combinação de letras e números\r\n" + //
                "- Verificar lixeira regularmente"));
        adicionarElemento("aniversarios.txt", "arquivo",
                e -> abrirArquivoTexto("Aniversários", "DATAS IMPORTANTES\r\n" + //
                        "\r\n" + //
                        "Maria: 15/03\r\n" + //
                        "João: 22/07\r\n" + //
                        "Sistema criado em: 1995\r\n" + //
                        "\r\n" + //
                        "💡 Dica: Anos são importantes!"));
        adicionarElemento("receita.txt", "arquivo", e -> abrirArquivoTexto("Receita", "RECEITA DE BOLO\r\n" + //
                "\r\n" + //
                "Ingredientes:\r\n" + //
                "- 3 cenouras\r\n" + //
                "- 4 ovos\r\n" + //
                "- Óleo\r\n" + //
                "- Açúcar\r\n" + //
                "\r\n" + //
                "[ARQUIVO IRRELEVANTE]"));
    }

    // --- COMPONENTES VISUAIS ---

    private void adicionarElemento(String nome, String tipo, ActionListener acao) {
        JButton btn = new JButton(nome);
        btn.setPreferredSize(new Dimension(100, 100));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 11));

        java.net.URL iconUrl = null;

        if (tipo.equals("pasta") || tipo.equals("voltar")) {
            btn.setForeground(new Color(184, 134, 11));
            iconUrl = getClass().getResource("/icones/pasta.png");
        } else if (tipo.equals("pista")) {
            btn.setForeground(new Color(0, 200, 0));
            iconUrl = getClass().getResource("/icones/arquivo.png");
        } else {
            btn.setForeground(Color.DARK_GRAY);
            iconUrl = getClass().getResource("/icones/arquivo.png");
        }

        if (iconUrl != null) {
            btn.setIcon(new ImageIcon(iconUrl));
        }

        btn.setText(nome);

        btn.addActionListener(acao);
        painelConteudo.add(btn);
    }

    private void limparEAtualizar(String caminho) {
        lblCaminho.setText("📁 Caminho: " + caminho);
        painelConteudo.removeAll();
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }

    // --- LOGICA DE INTERAÇÃO (FEEDBACKS) ---

    private void abrirArquivoTexto(String titulo, String conteudo) {
        JInternalFrame txtWindow = new JInternalFrame(titulo + ".txt", true, true, true, true);
        txtWindow.setSize(400, 300);
        txtWindow.setLocation(50, 50);

        JTextArea area = new JTextArea(conteudo);
        area.setMargin(new java.awt.Insets(10, 10, 10, 10));
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));

        txtWindow.add(new JScrollPane(area));
        getDesktopPane().add(txtWindow);
        txtWindow.setVisible(true);
    }

    private void mostrarErroAcesso() {
        JOptionPane.showMessageDialog(this,
                "Arquivo corrompido",
                "X Erro",
                JOptionPane.ERROR_MESSAGE);
    }

    private void abrirPista(String fragmento) {
        JOptionPane.showMessageDialog(this,
                "Pista encontrada: " + fragmento,
                "✓ Sucesso",
                JOptionPane.INFORMATION_MESSAGE);

        if (mainDesktop != null) {
            mainDesktop.revelarPista(0, fragmento);
        }
    }
}