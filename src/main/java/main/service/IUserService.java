package main.service;

import main.controller.ResponseDTO.LoginResponseDTO;
import main.domain.User;
import main.domain.Patient;
import main.domain.User;
import main.domain.VerificationToken;

public interface IUserService {

    LoginResponseDTO login(String email, String password) throws Exception;
    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String token);

    void enableUser(User user);
}
