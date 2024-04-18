/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * 
 * import java.util.*;
 *
 */

// Write your code here
package com.example.wordlyweek.service;

import com.example.wordlyweek.model.Writer;
import com.example.wordlyweek.model.Magazine;
import com.example.wordlyweek.repository.WriterJpaRepository;
import com.example.wordlyweek.repository.WriterRepository;
import com.example.wordlyweek.repository.MagazineJpaRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WriterJpaService implements WriterRepository {

    @Autowired
    private WriterJpaRepository sponsorJpaRepository;

    @Autowired
    private MagazineJpaRepository eventJpaRepository;

    @Override
    public ArrayList<Writer> getWriters() {
        List<Writer> sponsorList = sponsorJpaRepository.findAll();
        ArrayList<Writer> sponsors = new ArrayList<>(sponsorList);
        return sponsors;
    }

    @Override
    public Writer getWriterById(int writerId) {
        try {
            Writer writer = sponsorJpaRepository.findById(writerId).get();
            return writer;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Writer addWriter(Writer writer) {
        List<Integer> eventIds = new ArrayList<>();
        for (Magazine magazine : writer.getMagazines()) {
            eventIds.add(magazine.getMagazineId());
        }

        List<Magazine> magazines = eventJpaRepository.findAllById(eventIds);

        if (magazines.size() != eventIds.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        writer.setMagazines(magazines);

        return sponsorJpaRepository.save(writer);
    }

    @Override
    public Writer updateWriter(int writerId, Writer writer) {
        try {
            Writer newSponsor = sponsorJpaRepository.findById(writerId).get();
            if (writer.getWriterName() != null) {
                newSponsor.setWriterName(writer.getWriterName());
            }
            if (writer.getBio() != null) {
                newSponsor.setBio(writer.getBio());
            }
            if (writer.getMagazines() != null) {
                List<Integer> eventIds = new ArrayList<>();
                for (Magazine magazine : writer.getMagazines()) {
                    eventIds.add(magazine.getMagazineId());
                }
                List<Magazine> events = eventJpaRepository.findAllById(eventIds);
                if (events.size() != eventIds.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                newSponsor.setMagazines(events);
            }
            return sponsorJpaRepository.save(newSponsor);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteWriter(int writerId) {
        try {
            sponsorJpaRepository.deleteById(writerId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Magazine> getWriterMagazine(int writerId) {
        try {
            Writer writer = sponsorJpaRepository.findById(writerId).get();
            return writer.getMagazines();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
