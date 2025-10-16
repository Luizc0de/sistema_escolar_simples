       /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.awt.Color;
import javax.swing.*;

public class Tela_Inicial {
    JFrame window = new JFrame("Administração da Escola");
    JLabel titulo = new JLabel("Painel Administrativo");
    JButton btnAlunos = new JButton("Gerenciar Alunos");
    JButton btnProfessores = new JButton("Gerenciar Professores");
    JButton btnTurmas = new JButton("Gerenciar Turmas");
    JButton btnSair = new JButton("Sair");
    JPanel panel = new JPanel();
    JLabel logo = new JLabel(new ImageIcon(getClass().getResource("/Image/academi.png")));
    JLabel wallpaper = new JLabel(new ImageIcon(getClass().getResource("/Image/sla1.jpg")));

    public Tela_Inicial() {
        window.setSize(690, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setLocationRelativeTo(null);

        wallpaper.setBounds(0, 0, 738, 854);
        wallpaper.setVisible(true);

        panel.setLayout(null);
        panel.setVisible(true);
        panel.setBounds(400, 0, 278, 600);
        panel.setBackground(new Color(255, 255, 255, 255));
        window.add(panel);

        titulo.setBounds(15, 100, 250, 40);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 22));
        panel.add(titulo);

        btnAlunos.setBounds(40, 180, 200, 40);
        btnAlunos.setBackground(new Color(0, 153, 255));
        btnAlunos.setForeground(Color.WHITE);
        btnAlunos.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        panel.add(btnAlunos);

        btnProfessores.setBounds(40, 240, 200, 40);
        btnProfessores.setBackground(new Color(0, 153, 255));
        btnProfessores.setForeground(Color.WHITE);
        btnProfessores.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        panel.add(btnProfessores);

        btnTurmas.setBounds(40, 300, 200, 40);
        btnTurmas.setBackground(new Color(0, 153, 255));
        btnTurmas.setForeground(Color.WHITE);
        btnTurmas.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        panel.add(btnTurmas);

        btnSair.setBounds(40, 400, 200, 40);
        btnSair.setBackground(new Color(220, 53, 69));
        btnSair.setForeground(Color.WHITE);
        btnSair.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        panel.add(btnSair);

        btnSair.addActionListener(e -> window.dispose());

        logo.setBounds(-30, -100, 500, 800);
        window.add(logo);

        window.add(wallpaper);
        window.setVisible(true);
    }

   
}
