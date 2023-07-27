package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.CardRequest;
import peaksoft.dto.request.UpdateCardRequest;
import peaksoft.dto.response.CardInnerPageResponse;
import peaksoft.dto.response.CardResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.CardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardApi {

    private final CardService cardService;

    @GetMapping("/get/{cardId}")
    public CardInnerPageResponse getAlls(@PathVariable Long cardId){
        return cardService.get(cardId);
    }

    @GetMapping("/{columnId}")
    public List<CardResponse> getAllCards(@PathVariable Long columnId){
        return cardService.getAllCards(columnId);
    }

    @GetMapping("/getCardsByColumnId/{columnId}")
    public List<CardResponse> getCardsByColumnId(@PathVariable Long columnId){
        return cardService.getAllCardsByColumnId(columnId);
    }

    @PostMapping
    public CardInnerPageResponse saveCard(@RequestBody CardRequest cardRequest){
        return cardService.saveCard(cardRequest);
    }
    @GetMapping("/getById/{cardId}")
    public CardInnerPageResponse getCardById(@PathVariable Long cardId){
        return cardService.getCardById(cardId);
    }

    @PutMapping
    public CardInnerPageResponse updateCard(@RequestBody UpdateCardRequest updateCardRequest){
        return cardService.updateCardById(updateCardRequest);
    }

    @DeleteMapping("/{cardId}")
    public SimpleResponse deleteCard(@PathVariable Long cardId){
        return cardService.deleteCard(cardId);
    }


}