import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.datatransfer.*;

public class JanelaDesafio extends JInternalFrame {
    private DefaultListModel<String> model;
    private JList<String> listaArquivos;
    private Desktop mainDesktop;
    private JPanel painelSucesso;

    public JanelaDesafio(Desktop desktop) {
        super("Puzzle: Ordenar Arquivos", true, true, true, true);
        this.mainDesktop = desktop;

        setSize(650, 550);
        setLocation(150, 50);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(192, 192, 192));

        configurarInstrucoes();
        configurarListaComDragAndDrop();
        configurarPainelSucesso();
    }

    private void configurarInstrucoes() {
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(new Color(255, 253, 208));
        topo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(218, 165, 32), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titulo = new JLabel("🧩 DESAFIO: Ordenar Arquivos");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        JTextArea desc = new JTextArea("Arraste os arquivos para organizá-los por data (do mais antigo para o mais recente). Quando estiver correto, você receberá um fragmento da senha!");
        desc.setWrapStyleWord(true);
        desc.setLineWrap(true);
        desc.setEditable(false);
        desc.setOpaque(false);
        desc.setFont(new Font("SansSerif", Font.PLAIN, 12));

        topo.add(titulo, BorderLayout.NORTH);
        topo.add(desc, BorderLayout.CENTER);
        add(topo, BorderLayout.NORTH);
    }

    private void configurarListaComDragAndDrop() {
        model = new DefaultListModel<>();
        model.addElement("relatorio_parte3.txt | Data: 17/03/2024");
        model.addElement("relatorio_parte1.txt | Data: 15/03/2024");
        model.addElement("relatorio_parte4.txt | Data: 18/03/2024");
        model.addElement("relatorio_parte2.txt | Data: 16/03/2024");

        listaArquivos = new JList<>(model);
        listaArquivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaArquivos.setDragEnabled(true);
        listaArquivos.setDropMode(DropMode.INSERT);
        
        listaArquivos.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(200, 200, 200), 1),
                    new EmptyBorder(15, 15, 15, 15)
                ));
                label.setBackground(isSelected ? new Color(230, 240, 255) : Color.WHITE);
                label.setOpaque(true);
                label.setIcon(UIManager.getIcon("FileView.fileIcon")); // Ícone de arquivo
                return label;
            }
        });

        // Lógica de Arrastar e Soltar (Drag & Drop)
        listaArquivos.setTransferHandler(new TransferHandler() {
            private int indexRemovido = -1;
            private int indexAdicionado = -1;

            @Override
            public int getSourceActions(JComponent c) { return MOVE; }

            @Override
            protected Transferable createTransferable(JComponent c) {
                indexRemovido = listaArquivos.getSelectedIndex();
                return new StringSelection(listaArquivos.getSelectedValue());
            }

            @Override
            public boolean canImport(TransferSupport support) { 
                return support.isDataFlavorSupported(DataFlavor.stringFlavor); 
            }

            @Override
            public boolean importData(TransferSupport support) {
                try {
                    String data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                    JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
                    
                    indexAdicionado = dl.getIndex();
                    model.add(indexAdicionado, data);
                    return true;
                } catch (Exception e) { return false; }
            }

            @Override
            protected void exportDone(JComponent c, Transferable t, int action) {
                if (action == MOVE && indexRemovido != -1) {
                    if (indexAdicionado <= indexRemovido) {
                        indexRemovido++;
                    }
                    
                    model.remove(indexRemovido);
                    verificarOrdenacao();
                }
                
                // Limpa as variáveis para o próximo arrasto
                indexRemovido = -1;
                indexAdicionado = -1;
            }
        });

        JScrollPane scroll = new JScrollPane(listaArquivos);
        scroll.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(scroll, BorderLayout.CENTER);
    }

    private void configurarPainelSucesso() {
        painelSucesso = new JPanel(new GridLayout(2, 1));
        painelSucesso.setBackground(new Color(57, 255, 20));
        painelSucesso.setVisible(false);

        JLabel msg = new JLabel("✓ PUZZLE RESOLVIDO!", SwingConstants.CENTER);
        msg.setFont(new Font("SansSerif", Font.BOLD, 14));
        msg.setForeground(Color.WHITE);
        
        JLabel frag = new JLabel("Fragmento encontrado: DF", SwingConstants.CENTER);
        frag.setForeground(Color.WHITE);

        painelSucesso.add(msg);
        painelSucesso.add(frag);
        add(painelSucesso, BorderLayout.SOUTH);
    }

    private void verificarOrdenacao() {
        if (model.getSize() < 4) return;
        
        boolean correto = model.get(0).contains("parte1") &&
                         model.get(1).contains("parte2") &&
                         model.get(2).contains("parte3") &&
                         model.get(3).contains("parte4");

        if (correto) {
            painelSucesso.setVisible(true);
            listaArquivos.setEnabled(false);
            revalidate();
            
            JOptionPane.showMessageDialog(this, "Pista encontrada: DF", "✓ Sucesso", JOptionPane.INFORMATION_MESSAGE);
            if(mainDesktop != null) mainDesktop.revelarPista(3, "DF");
        }
    }
}