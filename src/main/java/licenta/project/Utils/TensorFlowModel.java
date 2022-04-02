package licenta.project.Utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;

@Getter
public class TensorFlowModel {
    private final Session model;

    public TensorFlowModel(@Value("${architecture}") String architecture, @Value("${weights}") String weights){
        this.model = SavedModelBundle.load(architecture, weights).session();
    }

}
