package licenta.project.Struct.Converter;

import licenta.project.Struct.Provider;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ProviderConverter implements AttributeConverter<Provider, String> {
    @Override
    public String convertToDatabaseColumn(Provider provider) {
        if(provider == null){
            return null;
        }
        return provider.getCode();
    }

    @Override
    public Provider convertToEntityAttribute(String s) {
        if(s == null){
            return null;
        }
        return Stream.of(Provider.values())
                .filter(p -> p.getCode().equals(s))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
