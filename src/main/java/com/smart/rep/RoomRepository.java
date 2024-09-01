package com.smart.rep;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{
   Page<Room> findRoomByAvailable(boolean available,Pageable pageable);
   
}
