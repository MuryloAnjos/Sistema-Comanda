package Controller;

import View.Inicio_GUI;
import View.Login_GUI;
import javax.swing.JOptionPane;

public class Verifica_DAO {

    public static void verificar() {

        String log = View.Login_GUI.user_txt.getText().toLowerCase();

        String sen = View.Login_GUI.senha_txt.getText().toLowerCase();

        if (log.equals("admin") && sen.equals("admin")) {
            JOptionPane.showMessageDialog(null, "Seja bem vindo");
            new Inicio_GUI().setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Login ou senha estão inválidos");
        }

    }

    public static void verifica() {

        func_DAO dao = new func_DAO();

        String log = View.Login_GUI.user_txt.getText();

        String sen = View.Login_GUI.senha_txt.getText();

        if(dao.check(log, sen)) {
            JOptionPane.showMessageDialog(null, "Seja bem vindo!");
            new Inicio_GUI().setVisible(true);
            
        } else {
            JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos.");
        }

    }
}


