package GUI;

import DAO.UsuarioDAO;
import Modelo.Usuario;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Tela_alunos {
    JFrame window = new JFrame("Cadastro de Alunos");
    JPanel centerPanel = new JPanel();
    JLabel titulo = new JLabel("Alunos");
    JTextField nomeField = new JTextField();
    JTextField cpfField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField telefoneField = new JTextField();
    JButton cadastrarBtn = new JButton("Cadastrar");
    JTextField buscarField = new JTextField();
    JButton buscarBtn = new JButton("Buscar", new ImageIcon(getClass().getResource("/Image/lupa24.png")));
    JLabel logo = new JLabel(new ImageIcon(getClass().getResource("/Image/academi.png")));

    // tabela para resultados
    DefaultTableModel tableModel;
    JTable tabela;
    JButton deletarBtn = new JButton("Deletar selecionado");
    JButton editarBtn = new JButton("Editar selecionado");
    JButton voltarBtn = new JButton("Voltar");

    UsuarioDAO usuarioDAO = new UsuarioDAO();

    // edição
    private Long editingId = null;
    private int editingRow = -1;

    public Tela_alunos() {
        window.setSize(690, 620);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setLocationRelativeTo(null);

        // Logo no topo (centralizado)
        logo.setBounds(195, 10, 300, 120);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        window.add(logo);

        // Painel central com formulário
        centerPanel.setLayout(null);
        centerPanel.setBounds(95, 140, 500, 250);
        centerPanel.setBackground(new Color(255, 255, 255, 255));
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

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(270, 80, 50, 25);
        centerPanel.add(emailLabel);
        emailField.setBounds(320, 80, 150, 25);
        centerPanel.add(emailField);

        JLabel telefoneLabel = new JLabel("Telefone:");
        telefoneLabel.setBounds(20, 115, 70, 25);
        centerPanel.add(telefoneLabel);
        telefoneField.setBounds(90, 115, 170, 25);
        centerPanel.add(telefoneField);

        cadastrarBtn.setBounds(170, 150, 160, 35);
        cadastrarBtn.setBackground(new Color(0, 153, 255));
        cadastrarBtn.setForeground(Color.WHITE);
        cadastrarBtn.setFont(new Font("Arial", Font.BOLD, 16));
        centerPanel.add(cadastrarBtn);

        // Busca
        JLabel buscarLabel = new JLabel("Buscar por nome:");
        buscarLabel.setBounds(20, 195, 120, 25);
        centerPanel.add(buscarLabel);
        buscarField.setBounds(140, 195, 230, 25);
        centerPanel.add(buscarField);

        buscarBtn.setBounds(380, 190, 110, 30);
        buscarBtn.setBackground(new Color(0, 153, 255));
        buscarBtn.setForeground(Color.WHITE);
        buscarBtn.setFont(new Font("Arial", Font.BOLD, 13));
        centerPanel.add(buscarBtn);

        // Tabela de resultados (abaixo do painel)
        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "Email"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(tableModel);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(95, 420, 500, 120);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados da busca"));
        window.add(scroll);

        // botões editar/deletar/voltar
        editarBtn.setBounds(240, 545, 170, 28);
        editarBtn.setBackground(new Color(255, 193, 7));
        editarBtn.setForeground(Color.BLACK);
        editarBtn.setFont(new Font("Arial", Font.BOLD, 12));
        window.add(editarBtn);

        deletarBtn.setBounds(420, 545, 170, 28);
        deletarBtn.setBackground(new Color(220, 53, 69));
        deletarBtn.setForeground(Color.WHITE);
        deletarBtn.setFont(new Font("Arial", Font.BOLD, 12));
        window.add(deletarBtn);

        voltarBtn.setBounds(95, 545, 120, 28);
        voltarBtn.setBackground(new Color(108,117,125));
        voltarBtn.setForeground(Color.WHITE);
        voltarBtn.setFont(new Font("Arial", Font.BOLD, 12));
        window.add(voltarBtn);

        // Ação cadastrar / salvar
        cadastrarBtn.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(window, "Informe o nome do aluno.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (editingId == null) {
                Usuario aluno = new Usuario();
                aluno.setNome(nome);
                aluno.setCpf(cpfField.getText().trim());
                aluno.setEmail(emailField.getText().trim());
                aluno.setTelefone(telefoneField.getText().trim());
                try {
                    usuarioDAO.adiciona(aluno);
                    // acrescenta na tabela
                    tableModel.addRow(new Object[]{aluno.getId(), aluno.getNome(), aluno.getCpf(), aluno.getTelefone(), aluno.getEmail()});
                    JOptionPane.showMessageDialog(window, "Aluno cadastrado com sucesso!");
                    clearForm();
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(window, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                Usuario aluno = new Usuario();
                aluno.setId(editingId);
                aluno.setNome(nome);
                aluno.setCpf(cpfField.getText().trim());
                aluno.setEmail(emailField.getText().trim());
                aluno.setTelefone(telefoneField.getText().trim());
                try {
                    boolean ok = usuarioDAO.atualizar(aluno);
                    if (ok && editingRow >= 0) {
                        // atualiza a linha na tabela
                        tableModel.setValueAt(aluno.getId(), editingRow, 0);
                        tableModel.setValueAt(aluno.getNome(), editingRow, 1);
                        tableModel.setValueAt(aluno.getCpf(), editingRow, 2);
                        tableModel.setValueAt(aluno.getTelefone(), editingRow, 3);
                        tableModel.setValueAt(aluno.getEmail(), editingRow, 4);
                        JOptionPane.showMessageDialog(window, "Aluno atualizado com sucesso!");
                        cancelEditing();
                    } else {
                        JOptionPane.showMessageDialog(window, "Nenhuma linha atualizada.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(window, "Erro ao atualizar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Editar selecionado -> carregar no formulário
        editarBtn.addActionListener(e -> {
            int sel = tabela.getSelectedRow();
            if (sel < 0) {
                JOptionPane.showMessageDialog(window, "Selecione um aluno para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            editingRow = sel;
            Object idObj = tableModel.getValueAt(sel, 0);
            editingId = idObj == null ? null : ((idObj instanceof Number) ? ((Number) idObj).longValue() : Long.parseLong(idObj.toString()));
            nomeField.setText(String.valueOf(tableModel.getValueAt(sel, 1)));
            cpfField.setText(String.valueOf(tableModel.getValueAt(sel, 2)));
            telefoneField.setText(String.valueOf(tableModel.getValueAt(sel, 3)));
            emailField.setText(String.valueOf(tableModel.getValueAt(sel, 4)));
            cadastrarBtn.setText("Salvar");
        });

        // Buscar -> preencher tabela com resultados do banco
        buscarBtn.addActionListener(e -> {
            String nomeBusca = buscarField.getText().trim();
            try {
                List<Usuario> resultados = usuarioDAO.buscarPorNome(nomeBusca);
                tableModel.setRowCount(0);
                for (Usuario u : resultados) {
                    tableModel.addRow(new Object[]{u.getId(), u.getNome(), u.getCpf(), u.getTelefone(), u.getEmail()});
                }
                if (resultados.isEmpty()) JOptionPane.showMessageDialog(window, "Nenhum aluno encontrado.");
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(window, "Erro na busca: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Deletar selecionado
        deletarBtn.addActionListener(e -> {
            int sel = tabela.getSelectedRow();
            if (sel < 0) {
                JOptionPane.showMessageDialog(window, "Selecione um aluno para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Object idObj = tableModel.getValueAt(sel, 0);
            Long id = idObj == null ? null : ((idObj instanceof Number) ? ((Number) idObj).longValue() : Long.parseLong(idObj.toString()));
            int op = JOptionPane.showConfirmDialog(window, "Deseja realmente deletar o aluno (ID: " + id + ")?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
            if (op != JOptionPane.YES_OPTION) return;
            try {
                boolean ok = usuarioDAO.deletar(id);
                if (ok) {
                    tableModel.removeRow(sel);
                    if (editingId != null && editingId.equals(id)) cancelEditing();
                    JOptionPane.showMessageDialog(window, "Aluno deletado com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(window, "Não foi possível deletar.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(window, "Erro ao deletar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // clique duplo na tabela mostra detalhes (opcional)
        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int r = tabela.getSelectedRow();
                    if (r >= 0) {
                        String msg = "ID: " + tableModel.getValueAt(r, 0)
                                + "\nNome: " + tableModel.getValueAt(r, 1)
                                + "\nCPF: " + tableModel.getValueAt(r, 2)
                                + "\nTelefone: " + tableModel.getValueAt(r, 3)
                                + "\nEmail: " + tableModel.getValueAt(r, 4);
                        JOptionPane.showMessageDialog(window, msg, "Detalhes do aluno", JOptionPane.INFORMATION_MESSAGE);
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

    private void cancelEditing() {
        editingId = null;
        editingRow = -1;
        cadastrarBtn.setText("Cadastrar");
        clearForm();
    }

    private void clearForm() {
        nomeField.setText("");
        cpfField.setText("");
        emailField.setText("");
        telefoneField.setText("");
    }
    public static void main(String[] args) {
        new Tela_alunos();
    }
}
