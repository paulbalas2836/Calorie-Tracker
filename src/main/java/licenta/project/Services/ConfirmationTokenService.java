package licenta.project.Services;

import licenta.project.Models.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken confirmationToken);

    Optional<ConfirmationToken> getToken(String token);

    void setConfirmedAt(String token);
}
