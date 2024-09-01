package com.smart.service.auth;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smart.dto.ReservationDto;
import com.smart.dto.ReservationResponseDto;
import com.smart.entity.Reservation;
import com.smart.entity.Room;
import com.smart.entity.User;
import com.smart.enums.ReservationStatus;
import com.smart.rep.ReservationRepository;
import com.smart.rep.RoomRepository;
import com.smart.rep.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

	private final RoomRepository roomRepository;
	private final UserRepository userRepository;
	private final ReservationRepository reservationRepository;
	private static final int SEARCH_PER_PAGE=4;
	public boolean bookRoom(ReservationDto reservationDto)
	{
		System.out.println("reached here");
		Optional<User> user=userRepository.findById(reservationDto.getUserId());
		Optional<Room> room=roomRepository.findById(reservationDto.getRoomId());
		
		if(user.isPresent() && room.isPresent())
		{
			Reservation r=new Reservation();
			r.setReservationStatus(ReservationStatus.PENDING);
			r.setCheckInDate(reservationDto.getCheckInDate());
			r.setCheckOutDate(reservationDto.getCheckOutDate());
			r.setRoom(room.get());
			r.setUser(user.get());
			Long days=ChronoUnit.DAYS.between(reservationDto.getCheckInDate(), reservationDto.getCheckOutDate());
			r.setPrice(days*room.get().getPrice());
			reservationRepository.save(r);
			return true;
		}
		
		return false;
		
	}
	@Override
	public ReservationResponseDto getAllReservationByUserID(Long userId,int pageNumber)
	{
		   Pageable pageable=PageRequest.of(pageNumber,SEARCH_PER_PAGE);
		   Page<Reservation> rpage = reservationRepository.findAllByUserId(pageable,userId);
		  
		   ReservationResponseDto rdto=new ReservationResponseDto();
		   rdto.setReservationDtoList(rpage.stream().map(Reservation::getReservationDto).collect(Collectors.toList()));
		   
		   rdto.setPageNumber(rpage.getPageable().getPageNumber());
		   rdto.setTotalPages(rpage.getTotalPages());
		   
		   return rdto;
	}
}
