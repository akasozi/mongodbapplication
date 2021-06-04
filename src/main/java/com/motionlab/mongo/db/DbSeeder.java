package com.motionlab.mongo.db;

import com.motionlab.mongo.models.Address;
import com.motionlab.mongo.models.Hotel;
import com.motionlab.mongo.models.Review;
import com.motionlab.mongo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DbSeeder implements CommandLineRunner {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public void run(String... args) throws Exception {

        Hotel marriot = new Hotel(
                "marriot",
                100,
                new Address("Paris", "France"),
                Arrays.asList(
                        new Review("John", 8, false),
                        new Review("Mary", 7, true),
                        new Review("David", 8, false)
                )
        );
        Hotel ibis = new Hotel(
                "Ibis",
                90,
                new Address("Bucharest", "Romania"),
                Arrays.asList(
                        new Review("Steve", 4, true),
                        new Review("Paulo", 8, true)
                )
        );
        Hotel kempinski = new Hotel(
                "kempinski",
                300,
                new Address("Nairobi", "Kenya"),
                Arrays.asList(
                        new Review("Abu", 9, false),
                        new Review("Victoria", 8, true),
                        new Review("Moses", 7, true)
                )
        );
        // Drop the existing collection
        this.hotelRepository.deleteAll();

        // Add our hotels to the database
        List<Hotel> hotels = Arrays.asList(marriot, ibis, kempinski);
        this.hotelRepository.saveAll(hotels);

    }
}
