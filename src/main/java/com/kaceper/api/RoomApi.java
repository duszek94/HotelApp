package com.kaceper.api;


import com.kaceper.dto.RoomDto;
import com.kaceper.dto.input.ListDto;
import com.kaceper.model.Room;
import com.kaceper.service.RoomService;
import com.kaceper.service.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room")
public class RoomApi {


    @Autowired
    RoomService roomService;

    @GetMapping("/all")
    public ListDto<RoomDto> getAllRooms() {
        return ListDto.make( () -> roomService.getAllRooms()
        );
    }

    @GetMapping("/status")
    public ListDto<RoomDto> getTest() {
        return ListDto.make( () -> roomService.getTest() );
    }

    @GetMapping("/status/{roomType}/{beginDate}/{endDate}")
    public ListDto<RoomDto> getFreeRooms(
            @PathVariable("roomType") String roomType,
            @PathVariable("beginDate") long beginDate,
            @PathVariable("endDate") long endDate
    ) {

        return ListDto.make( () -> roomService.getFreeRooms(roomType, beginDate, endDate) );
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public String addRoom(
            @RequestBody RoomDto dto){
        String msg = "";

        Room r = roomService.addRoom(dto);
        if(r != null){
            msg = "Dodano pokój";
        }else{
            msg = "Wystąpił błąd podczas dodawania pokoju";
        }

        return msg;
    }

    @PostMapping(value = "/delete", consumes = "application/json")
    public String deleteRooms(
            @RequestBody List< Integer > ids){
        String msg = "";

        for(int id : ids){
            System.out.println(id);
            if(roomService.deleteRoom(id)){
                msg = "Usunięto dane";
            }
            else msg = "Wystąpił błąd podczas usuwania danych";
        }

        return msg;
    }

}
