package com.kaceper.repository;


import com.kaceper.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {


}
