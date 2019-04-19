package com.bharath.flightservices.integration;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bharath.flightservices.dto.CreateReservationRequest;
import com.bharath.flightservices.dto.UpdateReservationRequest;
import com.bharath.flightservices.entities.Flight;
import com.bharath.flightservices.entities.Passenger;
import com.bharath.flightservices.entities.Reservation;
import com.bharath.flightservices.repos.FlightRepository;
import com.bharath.flightservices.repos.PassengerRepository;
import com.bharath.flightservices.repos.ReservationRepository;

@RestController
public class ReservationRestController {

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	PassengerRepository passengerRepository;

	@Autowired
	ReservationRepository reservationRepository;

	
//	@RequestMapping(value = "/flights", method = RequestMethod.GET)
//	public List<Flight> findFlights(@RequestParam("from") String from,
//									@RequestParam("to") String to, 
//									@RequestParam("departureDate") @DateTimeFormat(pattern="mm-dd-yyyy")Date departureDate) 
//
//	{
//		return flightRepository.findFlights(from, to, departureDate);
//
//	}
	
	
	@RequestMapping(value = "/flights/{id}", method = RequestMethod.GET)
	public Flight findFlights(@PathVariable("id")int id) {
		return flightRepository.findById(id).get();

	}
	
	@RequestMapping(value = "/reservations", method = RequestMethod.POST)
	public Reservation saveReservation(CreateReservationRequest request) {

		Flight flight = flightRepository.findById(request.getFlightId()).get();

		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setMiddleName(request.getPassengerMiddleName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setEmail(request.getPassengerEmail());
		passenger.setPhone(request.getPassengerPhone());

		Passenger savePassenger = passengerRepository.save(passenger);

		Reservation reservation = new Reservation();
		reservation.setPassenger(savePassenger);
		reservation.setFlight(flight);
		reservation.setCheckedIn(false);

		reservationRepository.save(reservation);

		return reservationRepository.save(reservation);

	}

	@RequestMapping(value = "/reservations/{id}", method = RequestMethod.GET)
	public Reservation findReservation(@PathVariable("id") int id) {
		return reservationRepository.findById(id).get();

	}

	@RequestMapping(value = "/reservations", method = RequestMethod.PUT)
	public Reservation updateReservation(UpdateReservationRequest request) {
		
		Reservation reservation = reservationRepository.findById(request.getId()).get();
		reservation.setCheckedIn(request.isCheckIn());
		reservation.setNumberOfBags(request.getNumberOfBags());
		
		return reservationRepository.save(reservation);

	}

}
