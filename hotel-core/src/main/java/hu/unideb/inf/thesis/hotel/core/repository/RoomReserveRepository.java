package hu.unideb.inf.thesis.hotel.core.repository;

import hu.unideb.inf.thesis.hotel.core.entitiy.RoomEntity;
import hu.unideb.inf.thesis.hotel.core.entitiy.RoomReserveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RoomReserveRepository extends JpaRepository<RoomReserveEntity, Long> {

    List<RoomReserveEntity> findByStartTime(Date startTime);

    List<RoomReserveEntity> findByEndTime(Date endTime);

    List<RoomReserveEntity> findByTotalPrice(int totalPrice);

    RoomReserveEntity findByStartTimeAndEndTimeAndTotalPriceAndRoomEntity(Date startTime, Date endTime,
                                                                          int totalPrice, RoomEntity roomEntity);

}
