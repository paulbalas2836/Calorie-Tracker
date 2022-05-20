package licenta.project.Services;

import licenta.project.Dto.FoodDto;
import licenta.project.Dto.HistoryDateDto;
import licenta.project.Dto.SaveHistoryDto;
import licenta.project.Exceptions.AppException;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Map;

public interface HistoryService {
    FoodDto getDataFromPrediction(MultipartFile image, SaveHistoryDto saveHistoryDto) throws AppException;

    Map<String, Object> getHistory(String email, HistoryDateDto historyDateDto) throws ParseException;
}
