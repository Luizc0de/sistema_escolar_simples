package GUI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
import java.awt.Color;
import javax.swing.*;
/**
 *
 * @author Yasmin
 */
public class Tela_login {
    JFrame window = new JFrame("Login");
    JLabel userLabel = new JLabel("Usuário:");
    JTextField userText = new JTextField(20);
    JLabel passwordLabel = new JLabel("Senha:");
    JPasswordField passwordText = new JPasswordField(20);
    JButton loginButton = new JButton("Login");
    JPanel panel = new JPanel();
    JLabel Bn = new JLabel();
    JLabel tx = new JLabel(new ImageIcon(getClass().getResource("/Image/academi.png")));
    JLabel wallpaper = new JLabel(new ImageIcon(getClass().getResource("/Image/sla1.jpg")));

    Tela_login() {
        window.setSize(690, 600);//738, 654 inicial
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        //window.setResizable(false);
        window.setLocationRelativeTo(null);
        wallpaper.setBounds(0, 0, 738, 854);//738, 854 inicial
        wallpaper.setVisible(true);

        panel.setLayout(null);
        panel.setVisible(true);
        panel.setBounds(400, 0, 278, 600);
        panel.setBackground(new Color(255, 255, 255, 255));
        //panel.setOpaque(false);
        window.add(panel);

        Bn.setBounds(15, 100, 250, 130);
        //Bn.setBackground(Color.BLUE);
        //Bn.setOpaque(true);
        Bn.setText("Bem Vindo");
        Bn.setHorizontalAlignment(SwingConstants.CENTER);
        Bn.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 28));
        panel.add(Bn);

        userLabel.setBounds(20, 250, 55, 25);
        userLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userLabel.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        //userLabel.setOpaque(true);
        //userLabel.setBackground(java.awt.Color.WHITE);
        panel.add(userLabel);

        userText.setBounds(90, 250, 165, 25);
        panel.add(userText);
        
        passwordLabel.setBounds(20, 330, 49, 25);
        passwordLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        passwordLabel.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        //passwordLabel.setOpaque(true); 
        //passwordLabel.setBackground(java.awt.Color.WHITE);
        panel.add(passwordLabel);
       
        passwordText.setBounds(90, 330, 165, 25);
        panel.add(passwordText);

        tx.setBounds(-30, -100, 500, 800);
        window.add(tx);

        loginButton.setBounds(60, 400, 160, 45);
        loginButton.setBackground(new Color(0, 153, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        panel.add(loginButton);
        loginButton.addActionListener(e -> {
            autenticar();
        });

        window.add(wallpaper);
        window.setVisible(true);
       
    }
    private void autenticar() {
        String usuario = userText.getText();
        String senha = new String(passwordText.getPassword());
        if (usuario.equals("admin") && senha.equals("1234")) {
            window.dispose();
            new Tela_Inicial();
        } else {
            JOptionPane.showMessageDialog(window, "Usuário ou senha incorretos.");
        }
    }
    public static void main(String[] args) {
        new Tela_login();
    }
}
