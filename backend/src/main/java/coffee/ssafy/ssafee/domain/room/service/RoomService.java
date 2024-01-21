package coffee.ssafy.ssafee.domain.room.service;

import coffee.ssafy.ssafee.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

}
