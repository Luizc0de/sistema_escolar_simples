package GUI;

import Modelo.Usuario;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Tela_alunos {
    JFrame window = new JFrame("Cadastro de Alunos");
    JPanel panel = new JPanel();
    JLabel titulo = new JLabel("Alunos");
    JTextField nomeField = new JTextField();
    JTextField cpfField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField telefoneField = new JTextField();
    JButton cadastrarBtn = new JButton("Cadastrar");
    JTextField buscarField = new JTextField();
    JButton buscarBtn = new JButton("Buscar");
    JTextArea resultadoArea = new JTextArea();
    JLabel logo = new JLabel(new ImageIcon(getClass().getResource("/Image/academi.png")));

    List<Usuario> alunos = new ArrayList<>();

    public Tela_alunos() {
        window.setSize(690, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setLocationRelativeTo(null);

        panel.setLayout(null);
        panel.setBounds(400, 0, 278, 600);
        panel.setBackground(new Color(255, 255, 255, 255));
        window.add(panel);

        titulo.setBounds(15, 30, 250, 40);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(titulo);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setBounds(20, 90, 60, 25);
        panel.add(nomeLabel);
        nomeField.setBounds(90, 90, 165, 25);
        panel.add(nomeField);

        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setBounds(20, 130, 60, 25);
        panel.add(cpfLabel);
        cpfField.setBounds(90, 130, 165, 25);
        panel.add(cpfField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 170, 60, 25);
        panel.add(emailLabel);
        emailField.setBounds(90, 170, 165, 25);
        panel.add(emailField);

        JLabel telefoneLabel = new JLabel("Telefone:");
        telefoneLabel.setBounds(20, 210, 60, 25);
        panel.add(telefoneLabel);
        telefoneField.setBounds(90, 210, 165, 25);
        panel.add(telefoneField);

        cadastrarBtn.setBounds(60, 250, 160, 35);
        cadastrarBtn.setBackground(new Color(0, 153, 255));
        cadastrarBtn.setForeground(Color.WHITE);
        cadastrarBtn.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(cadastrarBtn);

        JLabel buscarLabel = new JLabel("Buscar por nome:");
        buscarLabel.setBounds(20, 310, 120, 25);
        panel.add(buscarLabel);
        buscarField.setBounds(140, 310, 115, 25);
        panel.add(buscarField);

        buscarBtn.setBounds(60, 350, 160, 35);
        buscarBtn.setBackground(new Color(0, 153, 255));
        buscarBtn.setForeground(Color.WHITE);
        buscarBtn.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(buscarBtn);

        resultadoArea.setBounds(20, 400, 235, 120);
        resultadoArea.setEditable(false);
        panel.add(resultadoArea);

        logo.setBounds(-30, -100, 500, 800);
        window.add(logo);

        // Ação de cadastro
        cadastrarBtn.addActionListener(e -> {
            Usuario aluno = new Usuario();
            aluno.setNome(nomeField.getText());
            aluno.setCpf(cpfField.getText());
            aluno.setEmail(emailField.getText());
            aluno.setTelefone(telefoneField.getText());
            alunos.add(aluno);
            JOptionPane.showMessageDialog(window, "Aluno cadastrado com sucesso!");
            nomeField.setText("");
            cpfField.setText("");
            emailField.setText("");
            telefoneField.setText("");
        });

        // Ação de busca
        buscarBtn.addActionListener(e -> {
            String nomeBusca = buscarField.getText().trim().toLowerCase();
            StringBuilder resultado = new StringBuilder();
            for (Usuario aluno : alunos) {
                if (aluno.getNome() != null && aluno.getNome().toLowerCase().contains(nomeBusca)) {
                    resultado.append("Nome: ").append(aluno.getNome()).append("\n");
                    resultado.append("CPF: ").append(aluno.getCpf()).append("\n");
                    resultado.append("Email: ").append(aluno.getEmail()).append("\n");
                    resultado.append("Telefone: ").append(aluno.getTelefone()).append("\n\n");
                }
            }
            resultadoArea.setText(resultado.length() > 0 ? resultado.toString() : "Nenhum aluno encontrado.");
        });

        window.setVisible(true);
    }

    public static void main(String[] args) {
        new Tela_alunos();
    }
}
