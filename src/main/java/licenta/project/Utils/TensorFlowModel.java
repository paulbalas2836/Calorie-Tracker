package licenta.project.Utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.ndarray.Shape;
import org.tensorflow.ndarray.buffer.ByteDataBuffer;
import org.tensorflow.types.TFloat16;
import org.tensorflow.types.TFloat32;

import java.nio.FloatBuffer;
import java.util.Arrays;

@Getter
@Service
@NoArgsConstructor
public class TensorFlowModel {
    private static final String DEFAULT_TAG = "serve" ;
    private static final Integer FLOAT_SIZE_ALLOCATION = 4096;
    private static final String ARCHITECTURE="D:\\ImageRecognitionLicenta\\Apple_Kiwi_Banana_Orange_AI\\Model\\Apple_Kiwi_Banana_Orange_AI_Model.pb";

    public Session getModel(){
        return SavedModelBundle.load(ARCHITECTURE, DEFAULT_TAG ).session();
    }

    public TFloat32 transformBytesIntoFloatBuffer(byte[] imageBytes){
        Integer a =  imageBytes.length;
        FloatBuffer fb = FloatBuffer.allocate(FLOAT_SIZE_ALLOCATION);
        for(byte b : imageBytes){
            fb.put((float)(b & 0xFF)/255.0f);
        }
        fb.rewind();
        return Tensor.of(TFloat32.class, Shape.of(64,64,3), (ByteDataBuffer) fb);
    }

    public Tensor probabilities(){
        float[] prob_array = new float[4];
        Arrays.fill(prob_array, 1f);

        return null;
    }
}
