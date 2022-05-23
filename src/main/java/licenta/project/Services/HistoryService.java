package licenta.project.Services;

import licenta.project.Dto.HistoryDto.FoodHistoryDto;
import licenta.project.Dto.HistoryDto.HistoryDateDto;
import licenta.project.Dto.HistoryDto.SaveHistoryDto;
import licenta.project.Exceptions.AppException;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Map;

public interface HistoryService {
    FoodHistoryDto getDataFromPrediction(MultipartFile image, SaveHistoryDto saveHistoryDto) throws AppException;

    Map<String, Object> getHistory(String email, HistoryDateDto historyDateDto) throws ParseException;
}
