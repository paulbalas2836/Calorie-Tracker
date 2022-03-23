package licenta.project.Validations;

import licenta.project.Dto.RegisterDto;
import licenta.project.Annotations.RegisterPasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<RegisterPasswordMatches, Object> {
    @Override
    public void initialize(RegisterPasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        RegisterDto registerDto = (RegisterDto) obj;
        if (registerDto.getPassword() == null || registerDto.getMatchingPassword() == null)
            return false;
        return registerDto.getPassword().equals(registerDto.getMatchingPassword());
    }
}
