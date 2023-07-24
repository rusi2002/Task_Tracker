package peaksoft.service;

import com.google.firebase.auth.FirebaseAuthException;
import jakarta.mail.MessagingException;
import peaksoft.dto.request.SignInRequest;
import peaksoft.dto.request.SignUpRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.dto.response.ResetPasswordResponse;
import peaksoft.dto.response.SimpleResponse;

public interface AuthenticationService {

    AuthenticationResponse signUp(SignUpRequest signUpRequest);

    AuthenticationResponse signIn(SignInRequest signInRequest);
    ResetPasswordResponse resetPassword(Long userId, String newPassword, String repeatPassword);
    SimpleResponse forgotPassword(String email, String link) throws MessagingException;
    AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException;

//  AuthenticationResponse signInWithGoogle(GoogleSignInRequest googleSignInRequest);

}