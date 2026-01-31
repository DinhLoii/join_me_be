package com.join.me.modules.room.scheduler;

import com.join.me.modules.room.service.api.RoomService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoomExpiryCheckScheduler {

    RoomService roomService;

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void checkAndUpdateExpiredRooms() {
        try {
            roomService.checkAndUpdateExpiredRooms();
            log.info("Completed checking and updating expired rooms");
        } catch (Exception e) {
            log.error("Error checking and updating expired rooms", e);
        }
    }
}

