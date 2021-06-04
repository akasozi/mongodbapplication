package com.motionlab.mongo.controller;

import com.motionlab.mongo.models.Hotel;
import com.motionlab.mongo.models.QHotel;
import com.motionlab.mongo.repository.HotelRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping("/all")
    public List<Hotel> getAllHotels() {
          List<Hotel> hotels = this.hotelRepository.findAll();
          return hotels;
    }

    @PostMapping
    public void insert(@RequestBody Hotel hotel) {
        this.hotelRepository.insert(hotel);
    }

    @PutMapping
    public void update(@RequestBody Hotel hotel) {
        this.hotelRepository.save(hotel);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        this.hotelRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    public Hotel getById(@PathVariable("id") String id) {
        Optional<Hotel> optionalHotel = this.hotelRepository.findById(id);
        return optionalHotel.get();
    }

    @GetMapping("/price/{maxPrice}")
    public List<Hotel> getByPricePerNight(@PathVariable("maxPrice") int maxPrice) {
        List<Hotel> hotels = this.hotelRepository.findByPricePerNightLessThan(maxPrice);
        return hotels;
    }

    @GetMapping("/address/{city}")
    public List<Hotel> getByCity(@PathVariable("city") String city) {
        List<Hotel> hotels = this.hotelRepository.findByCity(city);
        return hotels;
    }

    @GetMapping("/country/{country}")
    public List<Hotel> getByCountry(@PathVariable("country") String country) {
        // Create a query class (QHotel)
        QHotel qHotel = new QHotel("hotel"); // Does not matter what you type in here
        // Create the filters
        BooleanExpression filterByCountry = qHotel.address.country.eq(country);
        // Pass the filters inside the repositories to the findAll method
        List<Hotel> hotels = (List<Hotel>)this.hotelRepository.findAll(filterByCountry);
        return hotels;
    }

    @GetMapping("/recommended")
    public List<Hotel> getRecommended() {
        final int maxPrice = 100;
        final int minRating = 7;
        QHotel qHotel = new QHotel("hotel");
        BooleanExpression filterByPrice = qHotel.pricePerNight.lt(maxPrice);
        BooleanExpression filterByRating = qHotel.reviews.any().rating.gt(minRating);
        List<Hotel> hotels = (List<Hotel>)this.hotelRepository.findAll(filterByPrice.and(filterByRating));
        return hotels;
    }

}
