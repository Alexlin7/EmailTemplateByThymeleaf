package com.example.emailtemplate;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class EmailTemplateApplication implements CommandLineRunner {


    private final SpringTemplateEngine emailTemplateEngine;

    public static void main(String[] args) {
        SpringApplication.run(EmailTemplateApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        final Context ctx = new Context();
        Invite invite = Invite.builder()
                .candidateName("Alexlin7")
                .jobTitle("水下瓷器技師")
                .interviewDateTime(LocalDateTime.of(2025, 6, 20, 14, 30)) // 面試時間
                .meetLink("https://meet.google.com/abc-defg-hij") // Google Meet 連結
                .meetId("abc-defg-hij") // 會議 ID
                .meetPassword("123456") // 會議密碼 (可選)
                .invitationCode("AQUA7788") // 人才網邀請碼
                .careersUrl("https://careers.underwaterceramics.com") // 公司人才網址
                .interviewSteps(List.of(
                        "第一階段：專業技能評估 (45 分鐘)",
                        "第二階段：與用人主管面談 (30 分鐘)",
                        "第三階段：HR 文化適應性面談 (20 分鐘)"
                )) // 面試流程
                .hrContactName("陳小姐") // HR 聯絡人
                .hrPhoneNumber("02-8765-4321 #123") // HR 電話
                .hrEmail("hr@underwaterceramics.com") // HR Email
                .build();

        ctx.setVariable("invite", invite);

        String emailContent = this.emailTemplateEngine.process("email/interview-round-mail-template", ctx);

        log.info("email template content: {}", emailContent);
    }
}


@Builder
record Invite(String jobTitle, String candidateName, LocalDateTime interviewDateTime, String meetLink, String meetId,
              String meetPassword, String invitationCode, String careersUrl, List<String> interviewSteps,
              String hrContactName, String hrPhoneNumber, String hrEmail) {
}
