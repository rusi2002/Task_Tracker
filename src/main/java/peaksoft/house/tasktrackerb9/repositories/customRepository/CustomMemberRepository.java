package peaksoft.house.tasktrackerb9.repositories.customRepository;

import peaksoft.house.tasktrackerb9.dto.response.MemberResponse;

import java.util.List;

public interface CustomMemberRepository {

    List<MemberResponse> getAllMembersByCardId(Long cardId);
    List<MemberResponse> searchByEmail(Long workSpaceId, String email);
    List<MemberResponse> getAllMembersFromBoard(Long boardId);
}
