package licenta.project.Struct.Converter;

import licenta.project.Struct.Roles;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class RolesConverter implements AttributeConverter<Roles, String> {

    @Override
    public String convertToDatabaseColumn(Roles roles) {
        if(roles == null){
            return null;
        }
        return roles.getCode();
    }

    @Override
    public Roles convertToEntityAttribute(String s) {
        if(s == null){
            return null;
        }
        return Stream.of(Roles.values())
                .filter(r -> r.getCode().equals(s))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
