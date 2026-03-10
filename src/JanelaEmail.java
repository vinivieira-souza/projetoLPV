import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class JanelaEmail extends JInternalFrame {
    private JPanel painelLista;
    private JPanel painelLeitura;
    private Desktop mainDesktop;

    public JanelaEmail(Desktop desktop) {
        super("Cliente de E-mail", true, true, true, true);
        this.mainDesktop = desktop;
        
        setSize(700, 480);
        setLocation(100, 80);
        setLayout(new BorderLayout());

        configurarEstrutura();
        popularEmails();
    }

    private void configurarEstrutura() {
        // Painel Esquerdo: Lista de E-mails
        painelLista = new JPanel();
        painelLista.setLayout(new BoxLayout(painelLista, BoxLayout.Y_AXIS));
        painelLista.setBackground(Color.WHITE);
        
        JScrollPane scrollLista = new JScrollPane(painelLista);
        scrollLista.setPreferredSize(new Dimension(250, 0));
        scrollLista.setBorder(new TitledBorder(new LineBorder(Color.GRAY), "Caixa de Entrada (4)"));

        // Painel Direito: Conteúdo do E-mail
        painelLeitura = new JPanel(new BorderLayout());
        painelLeitura.setBackground(Color.WHITE);
        painelLeitura.setBorder(new LineBorder(Color.GRAY));
        
        JLabel placeholder = new JLabel("Selecione um e-mail para ler", SwingConstants.CENTER);
        placeholder.setForeground(Color.GRAY);
        painelLeitura.add(placeholder, BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollLista, painelLeitura);
        split.setDividerLocation(250);
        add(split, BorderLayout.CENTER);
    }

    private void popularEmails() {
        adicionarPreview("suporte@empresa.com", "Lembrete: Atualização de senha", "15/03/2024", false, e -> {
            exibirConteudoEmail("Lembrete: Atualização de senha", "suporte@empresa.com", "15/03/2024", 
                "Olá,\r\n" + //
                                        "\r\n" + //
                                        "Este é um lembrete para atualizar sua senha do sistema.\r\n" + //
                                        "\r\n" + //
                                        "Por favor, use uma combinação segura de letras e números.\r\n" + //
                                        "\r\n" + //
                                        "Atenciosamente,\r\n" + //
                                        "Equipe de TI");
        });

        adicionarPreview("admin@sistema.local", "Re: Código de acesso", "16/03/2024", true, e -> {
            exibirConteudoEmailPista();
        });

        adicionarPreview("seguranca@empresa.com", "ALERTA: Tentativa de acesso", "17/03/2024", false, e -> {
            exibirConteudoEmail("Tentativa de acesso suspeito", "seguranca@empresa.com", "17/03/2024", "ALERTA DE SEGURANÇA\r\n" + //
                                "\r\n" + //
                                "Detectamos uma tentativa de acesso não autorizado ao seu sistema.\r\n" + //
                                "\r\n" + //
                                "Origem: 192.168.1.100\r\n" + //
                                "Data: 17/03/2024 às 14:32\r\n" + //
                                "\r\n" + //
                                "Recomendamos verificar seus arquivos e a lixeira.\r\n" + //
                                "\r\n" + //
                                "Equipe de Segurança");
        });
        
        adicionarPreview("backup@sistema.local", "Backup concluído", "18/03/2024", false, e -> {
            exibirConteudoEmail("Relatório de Backup", "backup@sistema.local", "18/03/2024", "O backup do sistema foi concluído com sucesso.\r\n" + //
                                "\r\n" + //
                                "Arquivos salvos: 1.247\r\n" + //
                                "Tamanho total: 2.3 GB\r\n" + //
                                "\r\n" + //
                                "Próximo backup: 25/03/2024");
        });
    }

    private void adicionarPreview(String de, String assunto, String data, boolean temPista, ActionListener acao) {
        JButton btn = new JButton();
        btn.setLayout(new GridLayout(3, 1));
        btn.setMaximumSize(new Dimension(250, 80));
        btn.setBackground(Color.WHITE);
        btn.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        btn.setFocusPainted(false);

        JLabel lblDe = new JLabel(temPista ? "● " + de : de);
        lblDe.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblDe.setForeground(temPista ? new Color(0, 150, 0) : Color.BLACK);

        JLabel lblAssunto = new JLabel(assunto);
        lblAssunto.setFont(new Font("SansSerif", Font.PLAIN, 11));
        
        JLabel lblData = new JLabel(data);
        lblData.setFont(new Font("SansSerif", Font.ITALIC, 10));
        lblData.setForeground(Color.GRAY);

        btn.add(lblDe);
        btn.add(lblAssunto);
        btn.add(lblData);
        btn.addActionListener(acao);
        
        painelLista.add(btn);
    }

    private void exibirConteudoEmail(String assunto, String de, String data, String corpo) {
        painelLeitura.removeAll();
        
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(new Color(230, 230, 230));
        header.setBorder(new EmptyBorder(10, 10, 10, 10));
        header.add(new JLabel("De: " + de));
        header.add(new JLabel("Data: " + data));

        JTextArea areaTexto = new JTextArea(corpo);
        areaTexto.setEditable(false);
        areaTexto.setMargin(new Insets(20, 20, 20, 20));
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 13));

        painelLeitura.add(header, BorderLayout.NORTH);
        painelLeitura.add(new JScrollPane(areaTexto), BorderLayout.CENTER);
        
        painelLeitura.revalidate();
        painelLeitura.repaint();
    }

    private void exibirConteudoEmailPista() {
        painelLeitura.removeAll();
        
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.WHITE);

        JTextArea texto = new JTextArea("\n Conforme solicitado, aqui está a segunda parte do código:\n\n" +
                                       " 🔍 FRAGMENTO: TR\n\n" +
                                       " Lembre-se de manter isso em segredo.\n\n - Admin");
        texto.setFont(new Font("SansSerif", Font.PLAIN, 13));
        texto.setEditable(false);
        container.add(texto, BorderLayout.CENTER);

        JButton btnPista = new JButton("✓ PISTA ENCONTRADA!");
        btnPista.setBackground(new Color(200, 255, 200));
        btnPista.setForeground(new Color(0, 100, 0));
        btnPista.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Pista encontrada: TR", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            if(mainDesktop != null) mainDesktop.revelarPista(1, "TR");
        });
        
        container.add(btnPista, BorderLayout.SOUTH);
        painelLeitura.add(container, BorderLayout.CENTER);
        
        painelLeitura.revalidate();
        painelLeitura.repaint();
    }
}