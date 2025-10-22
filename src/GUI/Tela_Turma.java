/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import DAO.TurmaDAO;
import Modelo.Turma;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Tela_Turma {
    JFrame window = new JFrame("Gerenciamento de Turmas");
    JPanel centerPanel = new JPanel();

    JLabel titulo = new JLabel("Turmas");
    JTextField nomeField = new JTextField();
    JTextField salaField = new JTextField();

    JButton cadastrarBtn = new JButton("Cadastrar");
    JTextField buscarField = new JTextField();
    JButton buscarBtn = new JButton("Buscar", new ImageIcon(getClass().getResource("/Image/lupa24.png")));
    JLabel logo = new JLabel(new ImageIcon(getClass().getResource("/Image/academi.png")));

    DefaultTableModel tableModel;
    JTable tabela;
    JButton editarBtn = new JButton("Editar selecionado");
    JButton deletarBtn = new JButton("Deletar selecionado");
    JButton voltarBtn = new JButton("Voltar");

    TurmaDAO turmaDAO = new TurmaDAO();

    private Long editingId = null;
    private int editingRow = -1;

    public Tela_Turma() {
        window.setSize(680, 620);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(null);
        window.setLocationRelativeTo(null);

        // logo
        logo.setBounds(190, 10, 300, 120);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        window.add(logo);

        // painel central
        centerPanel.setLayout(null);
        centerPanel.setBounds(90, 140, 500, 220);
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        window.add(centerPanel);

        titulo.setBounds(0, 5, 500, 30);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        centerPanel.add(titulo);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setBounds(20, 45, 60, 25);
        centerPanel.add(nomeLabel);
        nomeField.setBounds(90, 45, 380, 25);
        centerPanel.add(nomeField);

        JLabel salaLabel = new JLabel("Sala:");
        salaLabel.setBounds(20, 80, 60, 25);
        centerPanel.add(salaLabel);
        salaField.setBounds(90, 80, 170, 25);
        centerPanel.add(salaField);

        cadastrarBtn.setBounds(170, 120, 160, 35);
        cadastrarBtn.setBackground(new Color(0, 153, 255));
        cadastrarBtn.setForeground(Color.WHITE);
        cadastrarBtn.setFont(new Font("Arial", Font.BOLD, 16));
        centerPanel.add(cadastrarBtn);

        // busca
        JLabel buscarLabel = new JLabel("Buscar por nome:");
        buscarLabel.setBounds(20, 165, 120, 25);
        centerPanel.add(buscarLabel);
        buscarField.setBounds(140, 165, 230, 25);
        centerPanel.add(buscarField);

        buscarBtn.setBounds(380, 160, 110, 30);
        buscarBtn.setBackground(new Color(0, 153, 255));
        buscarBtn.setForeground(Color.WHITE);
        buscarBtn.setFont(new Font("Arial", Font.BOLD, 13));
        centerPanel.add(buscarBtn);

        // tabela
        String[] colunas = {"ID", "Nome", "Sala"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(tableModel);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(90, 390, 500, 160);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados"));
        window.add(scroll);

        // botões editar/deletar/voltar
        editarBtn.setBounds(220, 560, 160, 28);
        editarBtn.setBackground(new Color(255, 193, 7));
        editarBtn.setForeground(Color.BLACK);
        editarBtn.setFont(new Font("Arial", Font.BOLD, 12));
        window.add(editarBtn);

        deletarBtn.setBounds(400, 560, 160, 28);
        deletarBtn.setBackground(new Color(220, 53, 69));
        deletarBtn.setForeground(Color.WHITE);
        deletarBtn.setFont(new Font("Arial", Font.BOLD, 12));
        window.add(deletarBtn);

        voltarBtn.setBounds(90, 560, 120, 28);
        voltarBtn.setBackground(new Color(108,117,125));
        voltarBtn.setForeground(Color.WHITE);
        voltarBtn.setFont(new Font("Arial", Font.BOLD, 12));
        window.add(voltarBtn);

        // ações
        cadastrarBtn.addActionListener(e -> onSalvar());
        buscarBtn.addActionListener(e -> onBuscar());
        editarBtn.addActionListener(e -> onEditar());
        deletarBtn.addActionListener(e -> onDeletar());

        // duplo clique mostra detalhes
        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int r = tabela.getSelectedRow();
                    if (r >= 0) {
                        String msg = "ID: " + tableModel.getValueAt(r, 0)
                                + "\nNome: " + tableModel.getValueAt(r, 1)
                                + "\nSala: " + tableModel.getValueAt(r, 2);
                        JOptionPane.showMessageDialog(window, msg, "Detalhes da turma", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        // ação do botão voltar
        voltarBtn.addActionListener(e -> {
            new Tela_Inicial();
            window.dispose();
        });

        window.setVisible(true);
    }

    private void onSalvar() {
        String nome = nomeField.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(window, "Informe o nome da turma.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (editingId == null) {
            Turma t = new Turma();
            t.setNome(nome);
            t.setSala(salaField.getText().trim());
            try {
                turmaDAO.adiciona(t);
                tableModel.addRow(new Object[]{t.getId(), t.getNome(), t.getSala()});
                JOptionPane.showMessageDialog(window, "Turma cadastrada com sucesso!");
                clearForm();
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(window, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            Turma t = new Turma();
            t.setId(editingId);
            t.setNome(nome);
            t.setSala(salaField.getText().trim());
            try {
                boolean ok = turmaDAO.atualizar(t);
                if (ok && editingRow >= 0) {
                    tableModel.setValueAt(t.getId(), editingRow, 0);
                    tableModel.setValueAt(t.getNome(), editingRow, 1);
                    tableModel.setValueAt(t.getSala(), editingRow, 2);
                    JOptionPane.showMessageDialog(window, "Turma atualizada com sucesso!");
                    cancelEditing();
                } else {
                    JOptionPane.showMessageDialog(window, "Nenhuma linha atualizada.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(window, "Erro ao atualizar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onBuscar() {
        String nomeBusca = buscarField.getText().trim();
        try {
            List<Turma> resultados = turmaDAO.buscarPorNome(nomeBusca);
            tableModel.setRowCount(0);
            for (Turma t : resultados) {
                tableModel.addRow(new Object[]{t.getId(), t.getNome(), t.getSala()});
            }
            if (resultados.isEmpty()) JOptionPane.showMessageDialog(window, "Nenhuma turma encontrada.");
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(window, "Erro na busca: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEditar() {
        int sel = tabela.getSelectedRow();
        if (sel < 0) {
            JOptionPane.showMessageDialog(window, "Selecione uma turma para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        editingRow = sel;
        Object idObj = tableModel.getValueAt(sel, 0);
        editingId = idObj == null ? null : ((idObj instanceof Number) ? ((Number) idObj).longValue() : Long.parseLong(idObj.toString()));
        nomeField.setText(String.valueOf(tableModel.getValueAt(sel, 1)));
        salaField.setText(String.valueOf(tableModel.getValueAt(sel, 2)));
        cadastrarBtn.setText("Salvar");
    }

    private void onDeletar() {
        int sel = tabela.getSelectedRow();
        if (sel < 0) {
            JOptionPane.showMessageDialog(window, "Selecione uma turma para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object idObj = tableModel.getValueAt(sel, 0);
        Long id = idObj == null ? null : ((idObj instanceof Number) ? ((Number) idObj).longValue() : Long.parseLong(idObj.toString()));
        int op = JOptionPane.showConfirmDialog(window, "Deseja realmente deletar a turma (ID: " + id + ")?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (op != JOptionPane.YES_OPTION) return;
        try {
            boolean ok = turmaDAO.deletar(id);
            if (ok) {
                tableModel.removeRow(sel);
                if (editingId != null && editingId.equals(id)) cancelEditing();
                JOptionPane.showMessageDialog(window, "Turma deletada com sucesso.");
            } else {
                JOptionPane.showMessageDialog(window, "Não foi possível deletar.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(window, "Erro ao deletar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelEditing() {
        editingId = null;
        editingRow = -1;
        cadastrarBtn.setText("Cadastrar");
        clearForm();
    }

    private void clearForm() {
        nomeField.setText("");
        salaField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Tela_Turma());
    }
}
