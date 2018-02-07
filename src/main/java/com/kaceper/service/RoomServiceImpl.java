package com.kaceper.service;


import com.kaceper.dto.ReservationDto;
import com.kaceper.dto.RoomDto;
import com.kaceper.model.Reservation;
import com.kaceper.model.Room;
import com.kaceper.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("roomService")
@Transactional
public class RoomServiceImpl implements RoomService {

    @Autowired
    EntityManager em;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository){ this.roomRepository = roomRepository; }

    public List<RoomDto> getAllRooms(){
        List<RoomDto> list = new ArrayList<>();
        for(Room r : roomRepository.findAll()){
            list.add(RoomDto.make(r));
        }

        return list;
    }

    public List<RoomDto> getTest(){
        List<RoomDto> list = em.createQuery("select r from Room r " +
                "where r.roomType = 'apDL' ", Room.class)
                .getResultList()
                .stream()
                .map(RoomDto::make)
                .collect(Collectors.toList());

        return list;
    }

    public Date longToDate(long l){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Date d = null;
        try {
            d = formatter.parse(formatter.format(new Date(l)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public long stringToLong(String s){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Date date = null;
        try {
            date = formatter.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timestamp = date.getTime();

        return timestamp;
    }

    public boolean isOverLaped(Date start1,Date end1,Date start2,Date end2) throws NullPointerException{
        if ((start1.before(start2) && end1.after(start2)) ||
                (start1.before(end2) && end1.after(end2)) ||
                (start1.before(start2) && end1.after(end2)) ||
                (start1.before(end2) && start2.before(end1))) {
            return true;
        } else {
            return false;
        }
    }

    public List<RoomDto> getFreeRooms(String roomType, long beginDate, long endDate) {
        List<RoomDto> result = new ArrayList<>();
        Date bDate = longToDate(beginDate);
        Date eDate = longToDate(endDate);

        List< Room > list = em.createQuery("select distinct r " +
                "from Room r left outer join r.reservationList rs on " +
                "r.roomId = rs.roomId " +
                "where r.roomType = :roomType ", Room.class)
                .setParameter("roomType", roomType)
                .getResultList();

        if(!list.isEmpty()){
            int i = 1;

            for(Room r : list){
                RoomDto dto = new RoomDto();
                List<Reservation> reservations = r.getReservationList();
                List<ReservationDto> reservationDtos = r.getReservationDtoList(reservations);
                List<ReservationDto> correctReservationDtos = new ArrayList<>();

                dto.setRoomType(r.getRoomType());
                dto.setRoomId(r.getRoomId());
                dto.setPrice(r.getPrice());

                for(ReservationDto rDto : reservationDtos){
                    Date checkFrom = longToDate(stringToLong(rDto.getDateFrom()));
                    Date checkTo = longToDate(stringToLong(rDto.getDateTo()));

                    if(isOverLaped(checkFrom, checkTo, bDate, eDate)){
                        dto.setIsFree(false);
                        continue;
                    }else{
                        correctReservationDtos.add(rDto);
                    }
                }

                if(reservationDtos.size() == correctReservationDtos.size()){
                    dto.setIsFree(true);
                }

                dto.setReservations(correctReservationDtos);

                result.add(dto);
                i++;
            }
        }

        return result;
    }

    public boolean deleteRoom(Integer id){
        List<Reservation> reservations = em.createQuery("select r from Reservation r " +
                "where r.roomId.roomId = :id", Reservation.class)
                .setParameter("id", id)
                .getResultList();

        for(Reservation r : reservations){
            em.remove(r);
        }

        Room r = em.createQuery("select r from Room r " +
                "where r.roomId = :id", Room.class)
                .setParameter("id", id)
                .getSingleResult();

        em.remove(r);

        return true;
    }

    public Room addRoom(RoomDto dto){
        Room r = new Room();

        r.setPrice(dto.getPrice());
        r.setRoomNumber(dto.getRoomNumber());
        r.setRoomType(dto.getRoomType());

        roomRepository.save(r);
        return r;
    }
}
