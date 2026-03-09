import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class JanelaDocumentos extends JInternalFrame {
    private JPanel painelConteudo;
    private JLabel lblCaminho;
    private Desktop mainDesktop; // Referência para atualizar o visor de pistas

    public JanelaDocumentos(Desktop desktop) {
        super("Documentos", true, true, true, true);
        this.mainDesktop = desktop;
        
        setSize(600, 450);
        setLocation(50, 50);
        setLayout(new BorderLayout());
        setBackground(new Color(192, 192, 192)); // Cinza Windows

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
        adicionarElemento("readme.txt", "arquivo", e -> abrirArquivoTexto("README", "Conteúdo do README aqui..."));
        adicionarElemento("acesso_negado.txt", "arquivo", e -> mostrarErroAcesso());
    }

    private void exibirDiretorioProjetos() {
        limparEAtualizar("/root/Projetos");
        adicionarElemento("Voltar", "voltar", e -> exibirDiretorioRoot());
        adicionarElemento("projeto_secreto.txt", "arquivo", e -> abrirArquivoTexto("Projeto Secreto", "Dados confidenciais..."));
        
        // Arquivo VERDE (Pista) - Imagem image_da4453
        adicionarElemento("notas.txt", "pista", e -> abrirPista("R3"));
    }

    private void exibirDiretorioPessoal() {
        limparEAtualizar("/root/Pessoal");
        adicionarElemento("Voltar", "voltar", e -> exibirDiretorioRoot());
        adicionarElemento("lembretes.txt", "arquivo", e -> abrirArquivoTexto("Lembretes", "Não esquecer de..."));
        adicionarElemento("aniversarios.txt", "arquivo", e -> abrirArquivoTexto("Aniversários", "João: 15/05..."));
        adicionarElemento("receita.txt", "arquivo", e -> abrirArquivoTexto("Receita", "Ingredientes: ..."));
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

        // Estilização baseada no tipo (Simulando os ícones das imagens)
        if (tipo.equals("pasta") || tipo.equals("voltar")) {
            btn.setForeground(new Color(184, 134, 11)); // Amarelo pasta
            btn.setText("📁 " + nome);
        } else if (tipo.equals("pista")) {
            btn.setForeground(new Color(0, 200, 0)); // Verde Pista [cite: 66]
            btn.setText("📄! " + nome);
        } else {
            btn.setForeground(Color.DARK_GRAY);
            btn.setText("📄 " + nome);
        }

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
        // COLOQUE O CONTEÚDO DOS ARQUIVOS AQUI [cite: 12]
        
        txtWindow.add(new JScrollPane(area));
        getDesktopPane().add(txtWindow);
        txtWindow.setVisible(true);
    }

    private void mostrarErroAcesso() {
        // Reprodução da image_da4399 (Janela Vermelha) [cite: 45, 46]
        JOptionPane.showMessageDialog(this, 
            "Arquivo corrompido ou sem informações relevantes", 
            "X Erro", 
            JOptionPane.ERROR_MESSAGE);
    }

    private void abrirPista(String fragmento) {
        // Reprodução da image_d9edfd (Janela Verde de Sucesso) [cite: 48, 49]
        JOptionPane.showMessageDialog(this, 
            "Pista encontrada: " + fragmento, 
            "✓ Sucesso", 
            JOptionPane.INFORMATION_MESSAGE);
        
        // Lógica para atualizar o visor de pistas no Desktop principal [cite: 44]
        // mainDesktop.revelarPista(0, fragmento); 
    }
}