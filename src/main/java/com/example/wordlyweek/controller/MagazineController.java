/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.web.bind.annotation.*;
 * 
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here

package com.example.wordlyweek.controller;

import com.example.wordlyweek.model.Writer;
import com.example.wordlyweek.model.Magazine;
import com.example.wordlyweek.service.MagazineJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MagazineController {
    @Autowired
    private MagazineJpaService eventJpaService;

    @GetMapping("/magazines")
    public ArrayList<Magazine> getMagazines() {
        return eventJpaService.getMagazines();
    }

    @GetMapping("/magazines/{magazineId}")
    public Magazine getMagazineById(@PathVariable("magazineId") int magazineId) {
        return eventJpaService.getMagazineById(magazineId);

    }

    @PostMapping("/magazines")
    public Magazine addMgazine(@RequestBody Magazine magazine) {
        return eventJpaService.addMagazine(magazine);
    }

    @PutMapping("/magazines/{magazineId}")
    public Magazine updateMgazine(@PathVariable("magazineId") int magazineId, @RequestBody Magazine magazine) {
        return eventJpaService.updateMagazine(magazineId, magazine);
    }

    @DeleteMapping("/magazines/{magazineId}")
    public void deleteMgazine(@PathVariable("magazineId") int magazineId) {
        eventJpaService.deleteMagazine(magazineId);
    }

    @GetMapping("/magazines/{magazineId}/writers")
    public List<Writer> getMagazineWriter(@PathVariable("magazineId") int magazineId) {
        return eventJpaService.getMagazineWriter(magazineId);
    }
}