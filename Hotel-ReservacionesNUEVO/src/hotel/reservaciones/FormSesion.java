package hotel.reservaciones;

import org.apache.commons.codec.digest.DigestUtils; // Importar la librería Apache Commons Codec
import java.sql.Connection; // Librería para la conexión a la base de datos
import java.sql.DriverManager; // Librería para el DriverManager
import java.sql.PreparedStatement; // Librería para ejecutar consultas SQL
import java.sql.ResultSet; // Librería para almacenar resultados de la consulta SQL
import java.sql.SQLException; // Manejo de excepciones SQL
import javax.swing.JOptionPane; // Mostrar mensajes en caso de error

public class FormSesion extends javax.swing.JFrame {

    public FormSesion() {
        initComponents();
    }

    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFUsuario = new javax.swing.JTextField();
        jPasswordInicio = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnIniciarSesion = new javax.swing.JButton();
        btnRegis = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); 
        jLabel1.setText("RESERVACION DE HOTEL");

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); 
        jLabel2.setText("INICIO DE SESION");

        jLabel3.setText("Usuario");
        jLabel4.setText("Contraseña");

        btnIniciarSesion.setBackground(new java.awt.Color(199, 197, 210));
        btnIniciarSesion.setText("INICIAR SESION");
        btnIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarSesionActionPerformed(evt);
            }
        });

        btnRegis.setText("¿Aun no tienes cuenta? Registrate.");

        // Diseño del layout simplificado
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(177, 177, 177)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(btnIniciarSesion))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFUsuario)
                            .addComponent(jPasswordInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(jLabel3))))
                .addContainerGap(136, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(122, Short.MAX_VALUE)
                .addComponent(btnRegis)
                .addGap(107, 107, 107))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(27, 27, 27)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnIniciarSesion)
                .addGap(18, 18, 18)
                .addComponent(btnRegis)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }

    private void btnIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // Obtener los valores del formulario
        String usuario = jTextFUsuario.getText();
        String contrasena = new String(jPasswordInicio.getPassword());

        // Aplicar el hash MD5 a la contraseña ingresada por el usuario
        String contrasenaHasheada = hashContrasena(contrasena);

        // Validar la contraseña hasheada con la almacenada en la base de datos
        if (verificarContrasena(usuario, contrasenaHasheada)) {
            JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para aplicar el hash MD5 a una cadena de texto
    private String hashContrasena(String contrasena) {
        return DigestUtils.md5Hex(contrasena); // Convierte la contraseña a su hash MD5
    }

    // Método para verificar la contraseña ingresada con la almacenada en la base de datos
    private boolean verificarContrasena(String usuario, String contrasenaHasheada) {
        Connection conexion = null;
        PreparedStatement consulta = null;
        ResultSet resultado = null;
        boolean esValido = false;

        try {
            // Configurar la conexión
            conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "SYSTEM", "123456");

            // Consulta SQL para obtener la contraseña del usuario ingresado
            String sql = "SELECT contrasena FROM usuarios WHERE nombre = ?";
            consulta = conexion.prepareStatement(sql);
            consulta.setString(1, usuario);

            // Ejecutar la consulta y obtener el resultado
            resultado = consulta.executeQuery();

            if (resultado.next()) {
                // Obtener la contraseña almacenada en la base de datos
                String contrasenaAlmacenada = resultado.getString("contrasena");

                // Comparar la contraseña almacenada con la ingresada (ambas hasheadas)
                esValido = contrasenaAlmacenada.equals(contrasenaHasheada);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al verificar el usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Cerrar la conexión
            try {
                if (resultado != null) resultado.close();
                if (consulta != null) consulta.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return esValido;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormSesion().setVisible(true);
            }
        });
    }

    // Variables declaration                   
    private javax.swing.JButton btnIniciarSesion;
    private javax.swing.JButton btnRegis;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField jPasswordInicio;
    private javax.swing.JTextField jTextFUsuario;
}