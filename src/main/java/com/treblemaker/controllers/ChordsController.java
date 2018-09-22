package com.treblemaker.controllers;

import com.treblemaker.controllers.dto.ChordItems;
import com.treblemaker.dal.interfaces.IChordProgressionsDal;
import com.treblemaker.dal.interfaces.IChordSequencesDal;
import com.treblemaker.dal.interfaces.IGenreDal;
import com.treblemaker.dal.interfaces.IHiveChordDal;
import com.treblemaker.model.ChordProgression;
import com.treblemaker.model.Genre;
import com.treblemaker.model.HiveChord;
import com.treblemaker.model.request.ChordProgressionPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class ChordsController {

    @Autowired
    private IHiveChordDal hiveChordDal;

    @Autowired
    private IChordSequencesDal chordSequencesDal;

    @Autowired
    private IChordProgressionsDal chordProgressionsDal;

    @Autowired
    private IGenreDal genreDal;

    @RequestMapping(value = "/api/chords/get", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ChordItems> Get() {

        List<ChordItems> chords = new ArrayList<>();

        List<HiveChord> chordList = hiveChordDal.findAll();

        for (HiveChord c : chordList) {
            ChordItems chordItem = new ChordItems();
            chordItem.setId(c.getId());
            chordItem.setChord(c.getChordName());

            chords.add(chordItem);
        }

        return chords;
    }

    @RequestMapping(value = "/api/Chords/Post", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public
    @ResponseBody
    String post(@RequestBody ChordProgressionPost post) {

        List<HiveChord> chordTypes = hiveChordDal.findAll();
        List<Genre> genres = genreDal.findAll();

        ChordProgression chordProgression = new ChordProgression();
        chordProgression.setSongName(post.getSongname());
        chordProgression.setSongKey(post.getKey());
        chordProgression.setTimeSignature(post.getTimesignature());

        if (post.getGenres() != null && !post.getGenres().isEmpty()) {
            Optional<Genre> genre = genres.stream().filter(g -> g.getId() == (int) post.getGenres().get(0)).findFirst();
            if (genre.isPresent()) {
                chordProgression.setGenre(genre.get().getDescriptions());
            } else {
                chordProgression.setGenre("");
            }
        }

        try {
            Optional<HiveChord> chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(0))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord1(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(1))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord2(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(2))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord3(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(3))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord4(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(4))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord5(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(5))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord6(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(6))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord7(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(7))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord8(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(8))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord9(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(9))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord10(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(10))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord11(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(11))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord12(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(12))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord13(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(13))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord14(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(14))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord15(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(15))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord16(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(16))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord17(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(17))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord18(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(18))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord19(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(19))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord20(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(20))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord21(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(21))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord22(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(22))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord23(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(23))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord24(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(24))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord25(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(25))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord26(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(26))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord27(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(27))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord28(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(28))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord29(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(29))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord30(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(30))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord31(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(31))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord32(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(32))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord33(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(33))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord34(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(34))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord35(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(35))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord36(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(36))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord37(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(37))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord38(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(38))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord39(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(39))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord40(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(40))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord41(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(41))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord42(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(42))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord43(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(43))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord44(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(44))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord45(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(45))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord46(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(46))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord47(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(47))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord48(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(48))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord49(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(49))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord50(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(50))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord51(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(51))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord52(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(52))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord53(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(53))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord54(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(54))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord55(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(55))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord56(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(56))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord57(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(57))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord58(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(58))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord59(chord.get().getRawChordName());
            }

            chord = chordTypes.stream().filter(ct -> ct.getId().equals(post.getChords().get(59))).findFirst();
            if (chord.isPresent()) {
                chordProgression.setChord60(chord.get().getRawChordName());
            }

            post.getChords().get(100);

        } catch (IndexOutOfBoundsException e) {
            chordProgressionsDal.save(chordProgression);
            return "success";
        }

        return "error";
    }
}
