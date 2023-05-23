package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.ConfirmationTokenRequest;
import com.example.notemanagement.data.dtos.request.CreateAppUserRequest;
import com.example.notemanagement.data.dtos.request.LoginRequest;
import com.example.notemanagement.data.dtos.request.ResendTokenRequest;
import com.example.notemanagement.data.model.AppUser;
import com.example.notemanagement.data.model.ConfirmationToken;
import com.example.notemanagement.data.repository.AppUserRepository;
import jakarta.mail.MessagingException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class AppUserServiceImpl implements AppUserService{

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    //private final PasswordEncoder passwordEncoder;
    @Override
    public String registerUser(CreateAppUserRequest createAppUserRequest) throws MessagingException {
        boolean emailExist = appUserRepository.existsAppUsersByEmailAddressIgnoreCase(createAppUserRequest.getEmailAddress());
        if(emailExist) throw new IllegalStateException("email has been taken already, choose another email");
//        AppUser foundUser = appUserRepository.findAppUserByEmailAddressIgnoreCase(createAppUserRequest.getEmailAddress())
//                .get();

        AppUser appUser = new AppUser();
        appUser.setFirstname(createAppUserRequest.getFirstname());
        appUser.setLastname(createAppUserRequest.getLastname());
        appUser.setEmailAddress(createAppUserRequest.getEmailAddress());
        appUser.setPassword(hashPassword(createAppUserRequest.getPassword()));
        appUser.setPassword(hashPassword(createAppUserRequest.getConfirmPassword()));
        if (!createAppUserRequest.getPassword().equals(createAppUserRequest.getConfirmPassword()))
            throw new IllegalStateException("passwords do not match");
        appUserRepository.save(appUser);
        String token = generateToken();
        emailService.send(createAppUserRequest.getEmailAddress(), buildEmail(createAppUserRequest.getFirstname(),
        token));

        ConfirmationToken confirmationToken = new ConfirmationToken(
            token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5),
                appUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
//        if(!foundUser.isEnabled()){
//            String token1 = generateToken();
//            emailService.send(createAppUserRequest.getEmailAddress(), buildEmail(createAppUserRequest.getFirstname(),
//                    token1));
//
//            ConfirmationToken confirmationToken1 = new ConfirmationToken(
//                    token1,
//                    LocalDateTime.now(),
//                    LocalDateTime.now().plusMinutes(2),
//                    appUser
//            );
//            confirmationTokenService.saveConfirmationToken(confirmationToken1);
//        }
        return token;
    }

    @Override
    public String confirmToken(ConfirmationTokenRequest confirmationTokenRequest) {
        AppUser foundUser = appUserRepository.findAppUserByEmailAddressIgnoreCase(confirmationTokenRequest.getEmail())
                .orElseThrow(()-> new IllegalStateException("Wrong email provided"));
        ConfirmationToken foundToken = confirmationTokenService.getConfirmationToken(confirmationTokenRequest.getToken())
                .orElseThrow(()-> new IllegalStateException(" such token does not exist"));
        if(foundToken.getExpiredAt().isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Token has expired");
        }
        if (foundToken.getConfirmedAt() != null){
            throw new IllegalStateException("Token has been used");
        }
        confirmationTokenService.setConfirmedAt(confirmationTokenRequest.getToken());

        foundUser.setEnabled(true);
        appUserRepository.save(foundUser);
        return "you are successfully verified";
    }

    private String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public String resendToken (ResendTokenRequest resendTokenRequest) throws MessagingException {
        AppUser foundUser = appUserRepository.findAppUserByEmailAddressIgnoreCase(resendTokenRequest.getEmail())
                .orElseThrow(()-> new IllegalStateException("This email has not been used for registration"));
        if (foundUser.isEnabled()){ throw new IllegalStateException("You are already verified, proceed to login");}
        else {
            String token = generateToken();
            emailService.send(resendTokenRequest.getEmail(), buildEmail(foundUser.getFirstname(), token));

            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(5),
                    foundUser
            );
            confirmationTokenService.saveConfirmationToken(confirmationToken);
        }
        return "token has been resent successfully";
    }

    @Override
    public String login(LoginRequest loginRequest) {
        var foundUser = appUserRepository.findAppUserByEmailAddressIgnoreCase(loginRequest.getEmail())
                .orElseThrow(()-> new IllegalStateException("This email has not been registered"));
        if(!foundUser.isEnabled()) throw new IllegalStateException("You are have not verified your account");
        try {
            if(!BCrypt.checkpw(loginRequest.getPassword(), foundUser.getPassword()))
                throw new IllegalStateException("Incorrect password");
        } catch (IllegalStateException e){
            throw new RuntimeException(e.getMessage());
        }
        return "You are successfully logged in";
    }

    private String generateToken(){
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int randomNumber = secureRandom.nextInt(0, 9);
            stringBuilder.append(randomNumber);
        }
        return stringBuilder.toString();
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" + link + "</p></blockquote>\n Link will expire in 10 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
