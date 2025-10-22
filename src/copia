package GUI;

import DAO.UsuarioDAO;
import Modelo.Usuario;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

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
    JButton buscarBtn = new JButton("Buscar");
    JLabel logo = new JLabel(new ImageIcon(getClass().getResource("/Image/academi.png")));

    // Lista visual de resultados
    DefaultListModel<Usuario> listModel = new DefaultListModel<>();
    JList<Usuario> resultadoList = new JList<>(listModel);
    JButton deletarBtn = new JButton("Deletar selecionado");
    JButton editarBtn = new JButton("Editar selecionado");

    UsuarioDAO usuarioDAO = new UsuarioDAO();

    // controle de edição
    private Long editingId = null;
    private int editingIndex = -1;

    public Tela_alunos() {
        window.setSize(690, 620);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setLocationRelativeTo(null);

        // Logo no topo (centralizado)
        logo.setBounds(195, 10, 300, 120); // centraliza aproximadamente (690 largura)
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        window.add(logo);

        // Painel central com funcionalidades (formulário e botões)
        centerPanel.setLayout(null);
        centerPanel.setBounds(95, 140, 500, 250); // centralizado horizontalmente
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

        // Área de busca no próprio painel central (campo e botão)
        JLabel buscarLabel = new JLabel("Buscar por nome:");
        buscarLabel.setBounds(20, 195, 120, 25);
        centerPanel.add(buscarLabel);
        buscarField.setBounds(140, 195, 230, 25);
        centerPanel.add(buscarField);

        buscarBtn.setBounds(380, 190, 100, 30);
        buscarBtn.setBackground(new Color(0, 153, 255));
        buscarBtn.setForeground(Color.WHITE);
        buscarBtn.setFont(new Font("Arial", Font.BOLD, 14));
        centerPanel.add(buscarBtn);

        // Renderer mostra ID, Nome, CPF, Telefone e Email (HTML)
        resultadoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultadoList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Usuario u = (Usuario) value;
                String text = "<html>"
                        + "<b>ID:</b> " + (u.getId() == null ? "-" : u.getId()) + " &nbsp;&nbsp; <b>Nome:</b> " + esc(u.getNome())
                        + "<br><b>CPF:</b> " + esc(u.getCpf()) + " &nbsp;&nbsp; <b>Telefone:</b> " + esc(u.getTelefone())
                        + "<br><b>Email:</b> " + esc(u.getEmail())
                        + "</html>";
                JLabel lbl = (JLabel) super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
                lbl.setVerticalAlignment(SwingConstants.TOP);
                return lbl;
            }
        });

        // Duplo clique abre detalhes em dialog
        resultadoList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Usuario u = resultadoList.getSelectedValue();
                    if (u != null) {
                        String msg = "ID: " + (u.getId() == null ? "-" : u.getId())
                                + "\nNome: " + nullToEmpty(u.getNome())
                                + "\nCPF: " + nullToEmpty(u.getCpf())
                                + "\nTelefone: " + nullToEmpty(u.getTelefone())
                                + "\nEmail: " + nullToEmpty(u.getEmail());
                        JOptionPane.showMessageDialog(window, msg, "Detalhes do aluno", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        JScrollPane resultadoScroll = new JScrollPane(resultadoList);
        resultadoScroll.setBounds(95, 420, 500, 120);
        resultadoScroll.setBorder(BorderFactory.createTitledBorder("Resultados da busca (duplo-clique para detalhes)"));
        window.add(resultadoScroll);

        // botões editar e deletar
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

        // Ação de cadastro / salvar (adicionar ou atualizar)
        cadastrarBtn.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(window, "Informe o nome do aluno.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (editingId == null) {
                // criar novo
                Usuario aluno = new Usuario();
                aluno.setNome(nome);
                aluno.setCpf(cpfField.getText().trim());
                aluno.setEmail(emailField.getText().trim());
                aluno.setTelefone(telefoneField.getText().trim());
                try {
                    usuarioDAO.adiciona(aluno); // grava no banco e popula id no objeto
                    JOptionPane.showMessageDialog(window, "Aluno cadastrado com sucesso!");
                    clearForm();
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(window, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // atualizar existente
                Usuario aluno = new Usuario();
                aluno.setId(editingId);
                aluno.setNome(nome);
                aluno.setCpf(cpfField.getText().trim());
                aluno.setEmail(emailField.getText().trim());
                aluno.setTelefone(telefoneField.getText().trim());
                try {
                    boolean ok = usuarioDAO.atualizar(aluno);
                    if (ok) {
                        // atualizar item na lista visual se estiver presente
                        if (editingIndex >= 0 && editingIndex < listModel.size()) {
                            listModel.set(editingIndex, aluno);
                        } else {
                            // se não estiver na lista, apenas recarrega busca
                        }
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

        // Ação editar (carrega campos para edição)
        editarBtn.addActionListener(e -> {
            Usuario sel = resultadoList.getSelectedValue();
            if (sel == null) {
                JOptionPane.showMessageDialog(window, "Selecione um aluno para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            editingId = sel.getId();
            editingIndex = resultadoList.getSelectedIndex();
            nomeField.setText(nullToEmpty(sel.getNome()));
            cpfField.setText(nullToEmpty(sel.getCpf()));
            emailField.setText(nullToEmpty(sel.getEmail()));
            telefoneField.setText(nullToEmpty(sel.getTelefone()));
            cadastrarBtn.setText("Salvar");
        });

        // Ação de busca (usa banco via UsuarioDAO.buscarPorNome)
        buscarBtn.addActionListener(e -> {
            String nomeBusca = buscarField.getText().trim();
            try {
                List<Usuario> resultados = usuarioDAO.buscarPorNome(nomeBusca);
                listModel.clear();
                for (Usuario u : resultados) {
                    listModel.addElement(u);
                }
                if (resultados.isEmpty()) {
                    JOptionPane.showMessageDialog(window, "Nenhum aluno encontrado.");
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(window, "Erro na busca: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ação deletar
        deletarBtn.addActionListener(e -> {
            Usuario selecionado = resultadoList.getSelectedValue();
            if (selecionado == null) {
                JOptionPane.showMessageDialog(window, "Selecione um aluno para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int op = JOptionPane.showConfirmDialog(window,
                    "Deseja realmente deletar o aluno:\n" + selecionado.getNome() + " (ID: " + selecionado.getId() + ")?",
                    "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
            if (op != JOptionPane.YES_OPTION) return;

            try {
                boolean ok = usuarioDAO.deletar(selecionado.getId());
                if (ok) {
                    listModel.removeElement(selecionado);
                    // se estava editando o mesmo registro, cancelar edição
                    if (editingId != null && editingId.equals(selecionado.getId())) cancelEditing();
                    JOptionPane.showMessageDialog(window, "Aluno deletado com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(window, "Não foi possível deletar (id não encontrado).", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(window, "Erro ao deletar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // resetar edição ao clicar fora da lista
        resultadoList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // nada por enquanto
            }
        });

        window.setVisible(true);
    }

    private void cancelEditing() {
        editingId = null;
        editingIndex = -1;
        cadastrarBtn.setText("Cadastrar");
        clearForm();
    }

    private void clearForm() {
        nomeField.setText("");
        cpfField.setText("");
        emailField.setText("");
        telefoneField.setText("");
    }

    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private String nullToEmpty(String s) {
        return s == null ? "" : s;
    }

    
}
