package peaksoft.api;

import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.SignInRequest;
import peaksoft.dto.request.SignUpRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.dto.response.ResetPasswordResponse;
import peaksoft.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Api",description = "API - Handles user authentication and access control")
@CrossOrigin(origins = "*",maxAge = 3600)
public class AuthenticationApi {

    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    @Operation(summary = "SignUp",description = "Register new users")
    public AuthenticationResponse signUp(@RequestBody SignUpRequest signUpRequest){
        return authenticationService.signUp(signUpRequest);
    }

    @PostMapping("/signIn")
    @Operation(summary = "SignIn",description = "Only registered users can login")
    public  AuthenticationResponse signIn(@RequestBody SignInRequest signInRequest){
        return authenticationService.signIn(signInRequest);
    }

    @PostMapping("/forgotPassword")
    public void forgotPassword(@RequestParam String email, @RequestParam String link) throws MessagingException {
        authenticationService.forgotPassword(email, link);
    }
    @Operation(summary = "Reset password", description = "Allows you to reset the user's password")
    @PostMapping("/reset/password/{userId}")
    public ResetPasswordResponse resetPassword(@PathVariable Long userId,@RequestParam String newPassword,@RequestParam String repeatPassword) {
        return authenticationService.resetPassword(userId, newPassword, repeatPassword);
    }
    @Operation(summary = "Google authentication", description = "Any user can authenticate with Google")
    @PostMapping("/google")
    public AuthenticationResponse authWithGoogleAccount(@RequestParam String tokenId) throws FirebaseAuthException {
        return authenticationService.authWithGoogle(tokenId);
    }
}