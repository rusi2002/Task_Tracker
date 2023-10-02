package peaksoft.house.tasktrackerb9.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.tasktrackerb9.dto.request.EstimationRequest;
import peaksoft.house.tasktrackerb9.dto.request.UpdateEstimationRequest;
import peaksoft.house.tasktrackerb9.dto.response.EstimationResponse;
import peaksoft.house.tasktrackerb9.dto.response.SimpleResponse;
import peaksoft.house.tasktrackerb9.services.EstimationService;

@RestController
@RequestMapping("/api/estimations")
@RequiredArgsConstructor
@Tag(name = "Estimation",description = "Api Estimation to management")
@CrossOrigin(origins = "*",maxAge = 3600)
public class EstimationApi {

    private final EstimationService service;

    @PostMapping
    @Operation(summary = "Create estimation",description = "Create estimation is the card id")
    public EstimationResponse createdEstimation(@RequestBody EstimationRequest estimationRequest){
        return service.createdEstimation(estimationRequest);
    }

    @PutMapping("/{estimationId}")
    @Operation(summary = "Update estimation",description = "Update estimation is the card id")
    public SimpleResponse updatedEstimation(@PathVariable Long estimationId, @RequestBody UpdateEstimationRequest estimationRequest){
        return service.updateEstimation(estimationId,estimationRequest);
    }
}
