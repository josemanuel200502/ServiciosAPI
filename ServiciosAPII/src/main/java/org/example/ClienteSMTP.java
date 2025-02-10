package org.example;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;


public class ClienteSMTP {

    public static void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        // Configurar las propiedades del servidor SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Servidor SMTP (cambiar según el proveedor)
        props.put("mail.smtp.port", "587"); // Puerto SMTP
        props.put("mail.smtp.auth", "true"); // Requiere autenticación
        props.put("mail.smtp.starttls.enable", "true"); // Habilitar TLS

        // Credenciales del correo (debes reemplazarlas con las tuyas)
        final String usuario = "pnp729752@gmail.com";
        final String clave = "xjdk vjhv ecxw fdgp";

        // Autenticación
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, clave);
            }
        });

        try {
            // Crear el mensaje
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(usuario));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensaje.setSubject(asunto);
            mensaje.setText(cuerpo);

            // Enviar el correo
            Transport.send(mensaje);
            System.out.println("Correo enviado correctamente a " + destinatario);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
