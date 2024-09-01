package com.smart.service.auth;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smart.dto.ReservationResponseDto;
import com.smart.entity.Reservation;
import com.smart.entity.Room;
import com.smart.enums.ReservationStatus;
import com.smart.rep.ReservationRepository;
import com.smart.rep.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{
   private final ReservationRepository reservationRepository;
   private final RoomRepository roomRepository;
   private static final int SEARCH_PER_PAGE=4;
   
   public ReservationResponseDto  getallreservations( int pageNumber)
   {
	   Pageable pageable=PageRequest.of(pageNumber,SEARCH_PER_PAGE);
	   Page<Reservation> rpage = reservationRepository.findAll(pageable);
	  
	   ReservationResponseDto rdto=new ReservationResponseDto();
	   rdto.setReservationDtoList(rpage.stream().map(Reservation::getReservationDto).collect(Collectors.toList()));
	   
	   rdto.setPageNumber(rpage.getPageable().getPageNumber());
	   rdto.setTotalPages(rpage.getTotalPages());
	   
	   return rdto;
	   
   }
   @Override
   public boolean ChangeStatus(Long id, String status)
   {
	   Optional<Reservation> res=reservationRepository.findById(id);
	   
	   if(res.isPresent())
	   {
		   Reservation existigReservation=res.get();
		   if(Objects.equals(status, "Approve"))
		   {
			   existigReservation.setReservationStatus(ReservationStatus.APPROVED);
		   }
		   else {
			   existigReservation.setReservationStatus(ReservationStatus.REJECTED);
		}
		   reservationRepository.save(existigReservation);
		   
		   Room existingroom=existigReservation.getRoom();
		   if(Objects.equals(status, "Approve"))
		   {
		   existingroom.setAvailable(false);
		   }
		   roomRepository.save(existingroom);
		   return true;
	   }
	   
	   return false;
   }
	
}
