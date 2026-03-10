import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class JanelaLixeira extends JInternalFrame {
    private JPanel painelLista;
    private JPanel painelVisualizacao;
    private Desktop mainDesktop;
    private final Color CINZA_WINDOWS = new Color(192, 192, 192);

    public JanelaLixeira(Desktop desktop) {
        super("Lixeira", true, true, true, true);
        this.mainDesktop = desktop;
        
        setSize(700, 480);
        setLocation(120, 100);
        setLayout(new BorderLayout());

        configurarInterface();
        popularLixeira();
    }

    private void configurarInterface() {
        // Cabeçalho com botão Esvaziar (image_510f35)
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(CINZA_WINDOWS);
        topo.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        JLabel lblTitulo = new JLabel("Lixeira (3 itens)");
        JButton btnEsvaziar = new JButton("Esvaziar Lixeira");
        btnEsvaziar.addActionListener(e -> resetarVisualizacao()); // Volta ao estado inicial
        
        topo.add(lblTitulo, BorderLayout.WEST);
        topo.add(btnEsvaziar, BorderLayout.EAST);
        add(topo, BorderLayout.NORTH);

        // Divisão principal
        painelLista = new JPanel();
        painelLista.setLayout(new BoxLayout(painelLista, BoxLayout.Y_AXIS));
        painelLista.setBackground(Color.WHITE);
        
        JScrollPane scrollLista = new JScrollPane(painelLista);
        scrollLista.setPreferredSize(new Dimension(250, 0));

        painelVisualizacao = new JPanel(new BorderLayout());
        painelVisualizacao.setBackground(CINZA_WINDOWS);
        
        resetarVisualizacao();

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollLista, painelVisualizacao);
        split.setDividerLocation(250);
        add(split, BorderLayout.CENTER);
    }

    private void resetarVisualizacao() {
        painelVisualizacao.removeAll();
        painelVisualizacao.setBackground(Color.WHITE);
        
        // Placeholder (image_510f35)
        JLabel placeholder = new JLabel("Selecione um item para visualizar", SwingConstants.CENTER);
        placeholder.setForeground(Color.GRAY);
        // Aqui você adicionaria o ícone de arquivo grande depois
        painelVisualizacao.add(placeholder, BorderLayout.CENTER);
        
        painelVisualizacao.revalidate();
        painelVisualizacao.repaint();
    }

    private void popularLixeira() {
        // Arquivo Comum (image_510f12)
        adicionarItemLixeira("senha_antiga.txt", "10/03/2024", false, e -> {
            mostrarDetalhesArquivo("senha_antiga.txt", "10/03/2024", 
                "Senhas anteriores:\n\nVersão 1: ABC123\nVersão 2: XYZ789\nVersão 3: ******\n\n[ARQUIVO DELETADO POR SEGURANÇA]", false);
        });

        // Arquivo Pista (image_510e74)
        adicionarItemLixeira("fragmento_codigo.txt", "12/03/2024", true, e -> {
            mostrarDetalhesArquivo("fragmento_codigo.txt", "12/03/2024", 
                "🔍 FRAGMENTO RECUPERADO!\n\nTerceira parte do código: O9\n\nEste arquivo foi deletado acidentalmente.\nBom trabalho ao recuperá-lo!", true);
        });

        adicionarItemLixeira("rascunho.txt", "14/03/2024", false, e -> {
            mostrarDetalhesArquivo("rascunho.txt", "14/03/2024", "Rascunho de ideias...\r\n" + //
                                "\r\n" + //
                                "Projeto novo\r\n" + //
                                "Reunião amanhã\r\n" + //
                                "Comprar café\r\n" + //
                                "\r\n" + //
                                "[SEM INFORMAÇÕES RELEVANTES]", false);
        });
    }

    private void adicionarItemLixeira(String nome, String data, boolean ehPista, ActionListener acao) {
        JButton btn = new JButton();
        btn.setLayout(new FlowLayout(FlowLayout.LEFT));
        btn.setMaximumSize(new Dimension(250, 60));
        
        // Configurações visuais dinâmicas (Fundo e Borda)
        Color corFundo = ehPista ? new Color(240, 255, 240) : Color.WHITE;
        Color corBorda = ehPista ? new Color(50, 205, 50) : Color.LIGHT_GRAY;
        Color corTexto = ehPista ? new Color(0, 150, 0) : Color.BLACK;
        
        btn.setBackground(corFundo);
        btn.setBorder(new LineBorder(corBorda, ehPista ? 2 : 1)); 

        JLabel lblIcone = new JLabel("📄");
        
        JPanel texto = new JPanel(new GridLayout(2, 1));
        texto.setBackground(corFundo);
        
        JLabel lblNome = new JLabel(nome);
        lblNome.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblNome.setForeground(corTexto);
        
        JLabel lblData = new JLabel("Deletado: " + data);
        lblData.setFont(new Font("SansSerif", Font.PLAIN, 10));

        texto.add(lblNome);
        texto.add(lblData);
        btn.add(lblIcone);
        btn.add(texto);
        btn.addActionListener(acao);
        
        painelLista.add(btn);
    }

    private void mostrarDetalhesArquivo(String nome, String data, String conteudo, boolean mostrarBotaoRestaurar) {
        painelVisualizacao.removeAll();
        painelVisualizacao.setBackground(CINZA_WINDOWS);

        // Header do detalhe
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(CINZA_WINDOWS);
        header.setBorder(new EmptyBorder(10, 10, 10, 10));
        header.add(new JLabel("Nome: " + nome));
        header.add(new JLabel("Deletado em: " + data));

        JTextArea areaTexto = new JTextArea(conteudo);
        areaTexto.setEditable(false);
        areaTexto.setMargin(new Insets(15, 15, 15, 15));
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));

        painelVisualizacao.add(header, BorderLayout.NORTH);
        painelVisualizacao.add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        if (mostrarBotaoRestaurar) {
            JButton btnRestaurar = new JButton("🔄 Restaurar Arquivo");
            btnRestaurar.setPreferredSize(new Dimension(0, 40));
            btnRestaurar.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Pista encontrada: O9", "✓ Sucesso", JOptionPane.INFORMATION_MESSAGE);
                if(mainDesktop != null) mainDesktop.revelarPista(2, "O9"); // Atualiza índice 2 no Visor [cite: 44]
                btnRestaurar.setText("✓ Restaurado!");
                btnRestaurar.setEnabled(false);
            });
            painelVisualizacao.add(btnRestaurar, BorderLayout.SOUTH);
        }

        painelVisualizacao.revalidate();
        painelVisualizacao.repaint();
    }
}