package com.treblemaker.controllers;

import com.treblemaker.controllers.dto.GenreItem;
import com.treblemaker.dal.interfaces.IGenreDal;
import com.treblemaker.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GenreController {

    @Autowired
    private IGenreDal genreDal;

    @RequestMapping(value = "/api/genre/get", method = RequestMethod.GET)
    public @ResponseBody
    List<GenreItem> get()
    {
            List<GenreItem> genres = new ArrayList<>();

            Iterable<Genre> genreList = genreDal.findAll();
            for(Genre g : genreList) {

                GenreItem genreItem = new GenreItem();
                genreItem.setId(g.getId());
                genreItem.setGenre(g.getDescriptions());

                genres.add(genreItem);
            }

        return genres;
    }
}
