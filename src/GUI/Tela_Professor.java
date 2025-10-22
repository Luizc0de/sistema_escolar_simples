/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import DAO.ProfessorDAO;
import Modelo.Professor;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Tela_Professor {
    JFrame window = new JFrame("Gerenciamento de Professores");
    JPanel centerPanel = new JPanel();

    JLabel titulo = new JLabel("Professores");
    JTextField nomeField = new JTextField();
    JTextField cpfField = new JTextField();
    JTextField materiaField = new JTextField();
    JTextField telefoneField = new JTextField();
    JTextField emailField = new JTextField();

    JButton cadastrarBtn = new JButton("Cadastrar");
    JTextField buscarField = new JTextField();
    JButton buscarBtn = new JButton("Buscar", new ImageIcon(getClass().getResource("/Image/lupa24.png")));
    JLabel logo = new JLabel(new ImageIcon(getClass().getResource("/Image/academi.png")));

    DefaultTableModel tableModel;
    JTable tabela;
    JButton editarBtn = new JButton("Editar selecionado");
    JButton deletarBtn = new JButton("Deletar selecionado");
    JButton voltarBtn = new JButton("Voltar");

    ProfessorDAO professorDAO = new ProfessorDAO();

    private Long editingId = null;
    private int editingRow = -1;

    public Tela_Professor() {
        window.setSize(720, 660);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(null);
        window.setLocationRelativeTo(null);

        // logo
        logo.setBounds(210, 10, 300, 120);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        window.add(logo);

        // painel central
        centerPanel.setLayout(null);
        centerPanel.setBounds(110, 140, 500, 300);
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

        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setBounds(20, 80, 60, 25);
        centerPanel.add(cpfLabel);
        cpfField.setBounds(90, 80, 170, 25);
        centerPanel.add(cpfField);

        JLabel materiaLabel = new JLabel("Matéria:");
        materiaLabel.setBounds(270, 80, 60, 25);
        centerPanel.add(materiaLabel);
        materiaField.setBounds(330, 80, 140, 25);
        centerPanel.add(materiaField);

        JLabel telefoneLabel = new JLabel("Telefone:");
        telefoneLabel.setBounds(20, 115, 70, 25);
        centerPanel.add(telefoneLabel);
        telefoneField.setBounds(90, 115, 170, 25);
        centerPanel.add(telefoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(270, 115, 50, 25);
        centerPanel.add(emailLabel);
        emailField.setBounds(320, 115, 150, 25);
        centerPanel.add(emailField);

        cadastrarBtn.setBounds(170, 155, 160, 35);
        cadastrarBtn.setBackground(new Color(0, 153, 255));
        cadastrarBtn.setForeground(Color.WHITE);
        cadastrarBtn.setFont(new Font("Arial", Font.BOLD, 16));
        centerPanel.add(cadastrarBtn);

        // busca
        JLabel buscarLabel = new JLabel("Buscar por nome:");
        buscarLabel.setBounds(20, 205, 120, 25);
        centerPanel.add(buscarLabel);
        buscarField.setBounds(140, 205, 230, 25);
        centerPanel.add(buscarField);

        buscarBtn.setBounds(380, 200, 110, 30);
        buscarBtn.setBackground(new Color(0, 153, 255));
        buscarBtn.setForeground(Color.WHITE);
        buscarBtn.setFont(new Font("Arial", Font.BOLD, 13));
        centerPanel.add(buscarBtn);

        // tabela
        String[] colunas = {"ID", "Nome", "CPF", "Matéria", "Telefone", "Email"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(tableModel);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(110, 460, 500, 140);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados"));
        window.add(scroll);

        // botões editar/deletar/voltar
        editarBtn.setBounds(250, 610, 170, 28);
        editarBtn.setBackground(new Color(255, 193, 7));
        editarBtn.setForeground(Color.BLACK);
        editarBtn.setFont(new Font("Arial", Font.BOLD, 12));
        window.add(editarBtn);

        deletarBtn.setBounds(430, 610, 170, 28);
        deletarBtn.setBackground(new Color(220, 53, 69));
        deletarBtn.setForeground(Color.WHITE);
        deletarBtn.setFont(new Font("Arial", Font.BOLD, 12));
        window.add(deletarBtn);

        voltarBtn.setBounds(110, 610, 110, 28);
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
                                + "\nCPF: " + tableModel.getValueAt(r, 2)
                                + "\nMatéria: " + tableModel.getValueAt(r, 3)
                                + "\nTelefone: " + tableModel.getValueAt(r, 4)
                                + "\nEmail: " + tableModel.getValueAt(r, 5);
                        JOptionPane.showMessageDialog(window, msg, "Detalhes do professor", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(window, "Informe o nome do professor.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (editingId == null) {
            Professor p = new Professor();
            p.setNome(nome);
            p.setCpf(cpfField.getText().trim());
            p.setMateria(materiaField.getText().trim());
            p.setTelefone(telefoneField.getText().trim());
            p.setEmail(emailField.getText().trim());
            try {
                professorDAO.adiciona(p);
                tableModel.addRow(new Object[]{p.getId(), p.getNome(), p.getCpf(), p.getMateria(), p.getTelefone(), p.getEmail()});
                JOptionPane.showMessageDialog(window, "Professor cadastrado com sucesso!");
                clearForm();
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(window, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            Professor p = new Professor();
            p.setId(editingId);
            p.setNome(nome);
            p.setCpf(cpfField.getText().trim());
            p.setMateria(materiaField.getText().trim());
            p.setTelefone(telefoneField.getText().trim());
            p.setEmail(emailField.getText().trim());
            try {
                boolean ok = professorDAO.atualizar(p);
                if (ok && editingRow >= 0) {
                    tableModel.setValueAt(p.getId(), editingRow, 0);
                    tableModel.setValueAt(p.getNome(), editingRow, 1);
                    tableModel.setValueAt(p.getCpf(), editingRow, 2);
                    tableModel.setValueAt(p.getMateria(), editingRow, 3);
                    tableModel.setValueAt(p.getTelefone(), editingRow, 4);
                    tableModel.setValueAt(p.getEmail(), editingRow, 5);
                    JOptionPane.showMessageDialog(window, "Professor atualizado com sucesso!");
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
            List<Professor> resultados = professorDAO.buscarPorNome(nomeBusca);
            tableModel.setRowCount(0);
            for (Professor p : resultados) {
                tableModel.addRow(new Object[]{p.getId(), p.getNome(), p.getCpf(), p.getMateria(), p.getTelefone(), p.getEmail()});
            }
            if (resultados.isEmpty()) JOptionPane.showMessageDialog(window, "Nenhum professor encontrado.");
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(window, "Erro na busca: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEditar() {
        int sel = tabela.getSelectedRow();
        if (sel < 0) {
            JOptionPane.showMessageDialog(window, "Selecione um professor para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        editingRow = sel;
        Object idObj = tableModel.getValueAt(sel, 0);
        editingId = idObj == null ? null : ((idObj instanceof Number) ? ((Number) idObj).longValue() : Long.parseLong(idObj.toString()));
        nomeField.setText(String.valueOf(tableModel.getValueAt(sel, 1)));
        cpfField.setText(String.valueOf(tableModel.getValueAt(sel, 2)));
        materiaField.setText(String.valueOf(tableModel.getValueAt(sel, 3)));
        telefoneField.setText(String.valueOf(tableModel.getValueAt(sel, 4)));
        emailField.setText(String.valueOf(tableModel.getValueAt(sel, 5)));
        cadastrarBtn.setText("Salvar");
    }

    private void onDeletar() {
        int sel = tabela.getSelectedRow();
        if (sel < 0) {
            JOptionPane.showMessageDialog(window, "Selecione um professor para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object idObj = tableModel.getValueAt(sel, 0);
        Long id = idObj == null ? null : ((idObj instanceof Number) ? ((Number) idObj).longValue() : Long.parseLong(idObj.toString()));
        int op = JOptionPane.showConfirmDialog(window, "Deseja realmente deletar o professor (ID: " + id + ")?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (op != JOptionPane.YES_OPTION) return;
        try {
            boolean ok = professorDAO.deletar(id);
            if (ok) {
                tableModel.removeRow(sel);
                if (editingId != null && editingId.equals(id)) cancelEditing();
                JOptionPane.showMessageDialog(window, "Professor deletado com sucesso.");
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
        cpfField.setText("");
        materiaField.setText("");
        telefoneField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Tela_Professor());
    }
}
