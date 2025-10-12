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
    JLabel wallpaper = new JLabel(new ImageIcon(getClass().getResource("/Image/sla1.jpg")));

    Tela_login() {
        window.setSize(738, 654);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        wallpaper.setBounds(0, 0, 738, 854);
        wallpaper.setVisible(true);

        panel.setLayout(null);
        panel.setVisible(true);
        panel.setBounds(400, 0, 278, 454);
        panel.setBackground(new Color(255, 255, 255, 255));
        //panel.setOpaque(false);
        window.add(panel);

        userLabel.setBounds(50, 0, 55, 25);
        userLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userLabel.setOpaque(true);
        userLabel.setBackground(java.awt.Color.WHITE);
        panel.add(userLabel);

        userText.setBounds(0, 0, 165, 25);
        panel.add(userText);

        passwordLabel.setBounds(220, 340, 42, 25);
        passwordLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        passwordLabel.setOpaque(true); 
        //passwordLabel.setBackground(java.awt.Color.WHITE);
        panel.add(passwordLabel);

        passwordText.setBounds(0, 0, 165, 25);
        panel.add(passwordText);

        loginButton.setBounds(0, 0, 80, 25);
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
            //new Tela_inicial();
        } else {
            JOptionPane.showMessageDialog(window, "Usuário ou senha incorretos.");
        }
    }
    public static void main(String[] args) {
        new Tela_login();
    }
}
