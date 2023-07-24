package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.CardRequest;
import peaksoft.dto.response.CardInnerPageResponse;
import peaksoft.dto.response.CardResponse;
import peaksoft.service.CardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardApi {

    private final CardService cardService;

    @GetMapping
    public List<CardResponse> getAllCards(){
        return null;
    }

    @PostMapping
    public CardInnerPageResponse saveCard(@RequestBody CardRequest cardRequest){
        return cardService.saveCard(cardRequest);
    }
}
