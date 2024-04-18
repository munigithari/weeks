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
import com.example.wordlyweek.service.WriterJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WriterController {
    @Autowired
    private WriterJpaService sponsorJpaService;

    @GetMapping("/writers/magazines")
    public ArrayList<Writer> getWriters() {
        return sponsorJpaService.getWriters();
    }

    @GetMapping("/writers/magazines/{writerId}")
    public Writer getWriterById(@PathVariable("writerId") int writerId) {
        return sponsorJpaService.getWriterById(writerId);
    }

    @PostMapping("/writers/magazines")
    public Writer addWriter(@RequestBody Writer writer) {
        return sponsorJpaService.addWriter(writer);
    }

    @PutMapping("/writers/magazines/{writerId}")
    public Writer updateWriter(@PathVariable("writerId") int writerId, @RequestBody Writer writer) {
        return sponsorJpaService.updateWriter(writerId, writer);
    }

    @DeleteMapping("/writers/magazines/{writerId}")
    public void deleteWriter(@PathVariable("writerId") int writerId) {
        sponsorJpaService.deleteWriter(writerId);
    }

    @GetMapping("/magazines/{writerId}/writers")
    public List<Magazine> getWriterMagazine(@PathVariable("writerId") int writerId) {
        return sponsorJpaService.getWriterMagazine(writerId);
    }
}