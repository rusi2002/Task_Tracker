package peaksoft.house.tasktrackerb9.services;

import peaksoft.house.tasktrackerb9.dto.request.ColumnRequest;
import peaksoft.house.tasktrackerb9.dto.response.ColumnResponse;
import peaksoft.house.tasktrackerb9.dto.response.SimpleResponse;

import java.util.List;

public interface ColumnService {

    SimpleResponse createColumn(ColumnRequest columnRequest);

    List<ColumnResponse> getAllColumns(Long boardId);

    ColumnResponse update(Long columnId,ColumnRequest columnRequest);

    SimpleResponse removeColumn(Long columnId);

    SimpleResponse sendToArchive(Long columnId);
}