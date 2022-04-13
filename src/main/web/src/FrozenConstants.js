const BACKEND_URL = "http://localhost:8080/"
const PRODUCT_MAP = new Map([
    [0, "Apple"], [1, "Banana"], [2, "Kiwi"], [3, "Orange"]
])
const TENSORFLOW_MODEL = "http://127.0.0.1:8081/model.json"
const HISTORY_IMAGES = "http://127.0.0.1:8082/"

export default Object.freeze({
    BACKEND_URL: BACKEND_URL,
    PRODUCT_MAP: PRODUCT_MAP,
    TENSORFLOW_MODEL: TENSORFLOW_MODEL,
    HISTORY_IMAGES: HISTORY_IMAGES
})