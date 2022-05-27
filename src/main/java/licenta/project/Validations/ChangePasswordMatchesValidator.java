package licenta.project.Validations;

import licenta.project.Annotations.ChangePasswordMatches;
import licenta.project.Dto.AppUserDto.ChangePasswordDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ChangePasswordMatchesValidator implements ConstraintValidator<ChangePasswordMatches, Object> {
    @Override
    public void initialize(ChangePasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        ChangePasswordDto changePasswordDto = (ChangePasswordDto) obj;
        if (changePasswordDto.getNewPassword() == null || changePasswordDto.getConfirmNewPassword() == null)
            return false;
        return changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmNewPassword());
    }
}
