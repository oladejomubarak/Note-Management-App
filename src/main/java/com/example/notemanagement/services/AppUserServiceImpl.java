package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.*;
import com.example.notemanagement.data.model.AppUser;
import com.example.notemanagement.data.model.ConfirmationToken;
import com.example.notemanagement.data.repository.AppUserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService{


    private final AppUserRepository appUserRepository;


    private final EmailService emailService;

    private final ConfirmationTokenService confirmationTokenService;


    private final ConfirmationTokenRepository confirmationTokenRepository;

    //private final PasswordEncoder passwordEncoder;
    @Override
    public String registerUser(CreateAppUserRequest createAppUserRequest) throws MessagingException {
        boolean emailExist = appUserRepository.existsByEmailAddressIgnoreCase(
                createAppUserRequest.getEmailAddress());
        if(emailExist) {
            throw new IllegalStateException("email taken, choose another email");
        } else {
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
            return token;
        }
    }

    @Override
    public String confirmToken(ConfirmationTokenRequest confirmationTokenRequest) {
        AppUser foundUser = findUserByEmailIgnoreCase(confirmationTokenRequest.getEmail());
        ConfirmationToken foundToken = confirmationTokenService.getConfirmationToken(confirmationTokenRequest.getToken())
                .orElseThrow(()-> new IllegalStateException("such token does not exist"));
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
        AppUser foundUser = findUserByEmailIgnoreCase(resendTokenRequest.getEmail());
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
        AppUser foundUser = findUserByEmailIgnoreCase(loginRequest.getEmail());

        if(Objects.equals(foundUser.isEnabled(), false)){
            throw new IllegalStateException("You have not verified your account");
        }

        if(!BCrypt.checkpw(loginRequest.getPassword(), foundUser.getPassword())){
            throw new IllegalStateException("Incorrect password");
        }
        return "You are successfully logged in";
    }

    @Override
    public String deleteAppUser(String email) {
        var foundUser = findUserByEmailIgnoreCase(email);
        appUserRepository.delete(foundUser);
        return "user deleted successfully";
    }

    @Override
    public String changePassword(ChangePasswordRequest changePasswordRequest) {
        AppUser foundUser = findUserByEmailIgnoreCase(changePasswordRequest.getEmail());
        if(!BCrypt.checkpw(changePasswordRequest.getOldPassword(), foundUser.getPassword()))
            throw new RuntimeException("wrong old password");
        if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword()))
            throw new RuntimeException("Passwords do not match");
        foundUser.setPassword(hashPassword(changePasswordRequest.getNewPassword()));
        appUserRepository.save(foundUser);
        return "Password changed successfully";
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws MessagingException {
        AppUser foundUser = findUserByEmailIgnoreCase(forgotPasswordRequest.getEmail());
        String token = generateToken();
        ConfirmationToken confirmationToken = new ConfirmationToken(
            token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5),
                foundUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        emailService.send(foundUser.getEmailAddress(), buildForgotPasswordEmail(foundUser.getFirstname(), token));
        return token;
    }

    @Override
    public String resetPassword(ResetPasswordRequest resetPasswordRequest) {
        AppUser foundUser = findUserByEmailIgnoreCase(resetPasswordRequest.getEmail());
        ConfirmationToken foundToken = confirmationTokenService.getConfirmationToken(resetPasswordRequest.getToken())
                .orElseThrow(()-> new IllegalStateException("such token does not exist"));
        if(foundToken.getExpiredAt().isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Token has expired");
        }
        if (foundToken.getConfirmedAt() != null){
            throw new IllegalStateException("Token has been used");
        }
        confirmationTokenService.setConfirmedAt(resetPasswordRequest.getToken());
        if(!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmNewPassword())) {throw
                new IllegalStateException("passwords do not match");
        }
        foundUser.setPassword(hashPassword(resetPasswordRequest.getNewPassword()));
        appUserRepository.save(foundUser);
        return "Password reset successfully";
    }

    @Override
    public AppUser findUserByEmailIgnoreCase(String email) {
        return appUserRepository.findByEmailAddressIgnoreCase(email).orElseThrow(()-> new IllegalStateException(
                "No user found with such email"));
    }

    @Override
    public String deleteUserByEmail(String email) {
        AppUser foundUser = findUserByEmailIgnoreCase(email);
        appUserRepository.delete(foundUser);
        return "user deleted";
    }

    @Override
    public String deleteAllTokens() {
        confirmationTokenRepository.deleteAll();
        return "all tokens deleted";
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

    private String buildEmail(String name, String token) {
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
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please copy the below token to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" + token + "</p></blockquote>\n The token will expire in 5 minutes time. <p>See you soon</p>" +
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
    private String buildForgotPasswordEmail (String lastName, String token){
        return "Here's the link to reset your password"
                + "                                      "
                + "                                        "
                + "<p>Hello \"" + lastName + "\",</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + token + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p> " +
                "<p>Token expires in 5 minutes</p>";
    }
}
