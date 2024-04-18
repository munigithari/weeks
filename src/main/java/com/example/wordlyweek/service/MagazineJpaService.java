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
import com.example.wordlyweek.repository.MagazineJpaRepository;
import com.example.wordlyweek.repository.MagazineRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MagazineJpaService implements MagazineRepository {

    @Autowired
    private WriterJpaRepository sponsorJpaRepository;

    @Autowired
    private MagazineJpaRepository eventJpaRepository;

    @Override
    public ArrayList<Magazine> getMagazines() {
        List<Magazine> eventList = eventJpaRepository.findAll();
        ArrayList<Magazine> events = new ArrayList<>(eventList);
        return events;
    }

    @Override
    public Magazine getMagazineById(int magazineId) {
        try {
            Magazine magazine = eventJpaRepository.findById(magazineId).get();
            return magazine;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Magazine addMagazine(Magazine magazine) {
        List<Integer> witerIds = new ArrayList<>();
        for (Writer writer : magazine.getWriters()) {
            witerIds.add(writer.getWriterId());
        }

        List<Writer> sponsors = sponsorJpaRepository.findAllById(witerIds);
        magazine.setWriters(sponsors);

        for (Writer writer : sponsors) {
            writer.getMagazines().add(magazine);
        }

        Magazine savedEvent = eventJpaRepository.save(magazine);
        sponsorJpaRepository.saveAll(sponsors);

        return savedEvent;
    }

    @Override
    public Magazine updateMagazine(int magazineId, Magazine magazine) {
        try {
            Magazine newMagazine = eventJpaRepository.findById(magazineId).get();
            if (magazine.getMagazineName() != null) {
                newMagazine.setMagazineName(newMagazine.getMagazineName());
            }
            if (magazine.getPublicationDate() != null) {
                newMagazine.setPublicationDate(magazine.getPublicationDate());
            }

            if (magazine.getWriters() != null) {
                List<Writer> sponsors = newMagazine.getWriters();
                for (Writer writer : sponsors) {
                    writer.getMagazines().remove(newMagazine);
                }
                sponsorJpaRepository.saveAll(sponsors);
                List<Integer> newSponsorIds = new ArrayList<>();
                for (Writer writer : magazine.getWriters()) {
                    newSponsorIds.add(writer.getWriterId());
                }
                List<Writer> newSponsors = sponsorJpaRepository.findAllById(newSponsorIds);
                for (Writer sponsor : newSponsors) {
                    sponsor.getMagazines().add(newMagazine);
                }
                sponsorJpaRepository.saveAll(newSponsors);
                newMagazine.setWriters(newSponsors);
            }
            return eventJpaRepository.save(newMagazine);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteMagazine(int magazineId) {
        try {
            Magazine magazine = eventJpaRepository.findById(magazineId).get();

            List<Writer> writers = magazine.getWriters();
            for (Writer writer : writers) {
                writer.getMagazines().remove(magazine);
            }

            sponsorJpaRepository.saveAll(writers);

            eventJpaRepository.deleteById(magazineId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Writer> getMagazineWriter(int magazineId) {
        try {
            Magazine magazine = eventJpaRepository.findById(magazineId).get();
            return magazine.getWriters();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}