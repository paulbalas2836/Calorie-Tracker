import * as tf from '@tensorflow/tfjs';
import * as constants from "./FrozenConstants";
const model = (async function (){
    await tf.loadLayersModel("http://127.0.0.1:8081/model.json")
})

export default model