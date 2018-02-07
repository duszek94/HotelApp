package com.kaceper.service;

import com.kaceper.dto.RoomDto;
import com.kaceper.model.Room;

import java.util.Date;
import java.util.List;

public interface RoomService {
    List<RoomDto> getAllRooms();
    List<RoomDto> getTest();
    Date longToDate(long l);
    long stringToLong(String s);
    boolean isOverLaped(Date start1,Date end1,Date start2,Date end2) throws NullPointerException;
    List<RoomDto> getFreeRooms(String roomType, long beginDate, long endDate);
    boolean deleteRoom(Integer id);
    Room addRoom(RoomDto dto);
}
