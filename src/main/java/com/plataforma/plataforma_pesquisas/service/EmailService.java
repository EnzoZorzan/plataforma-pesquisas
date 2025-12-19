/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.service;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author enzo.lima
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @PostConstruct
    public void debug() {
        System.out.println("MAIL USER = "
                + ((JavaMailSenderImpl) mailSender).getUsername()
        );
    }

    public void enviarEmail(String destino, String assunto, String corpoHtml) {

        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper
                    = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(
                    ((JavaMailSenderImpl) mailSender).getUsername()
            );

            helper.setTo(destino);
            helper.setSubject(assunto);
            helper.setText(corpoHtml, true);

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar email", e);
        }
    }

    public String templateRecuperacao(String nome, String link) {
        return """
        <div style="font-family: Arial; max-width: 500px;">
            <h2>Recuperação de senha</h2>
            <p>Olá, %s</p>
            <p>Clique no botão abaixo para redefinir sua senha:</p>
            <a href="%s"
               style="padding: 12px 20px;
                      background: #4f46e5;
                      color: #fff;
                      text-decoration: none;
                      border-radius: 6px;">
                Redefinir senha
            </a>
            <p style="margin-top: 20px; font-size: 12px;">
                Este link expira em 2 horas.
            </p>
        </div>
    """.formatted(nome, link);
    }
}
