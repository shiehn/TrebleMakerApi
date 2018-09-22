package com.treblemaker.controllers;

import com.treblemaker.controllers.dto.CharacteristicItem;
import com.treblemaker.dal.interfaces.ICharacteristicsDal;
import com.treblemaker.model.Characteristics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class CharacteristicsController {

    @Autowired
    private ICharacteristicsDal characteristicsDal;

    @RequestMapping(value = "/api/characteristics/get", method = RequestMethod.GET)
    public @ResponseBody
    List<CharacteristicItem> get() {

        List<CharacteristicItem> characteristics = new ArrayList<>();

        Iterable<Characteristics> characteristList = characteristicsDal.findAll();

        for (Characteristics c : characteristList) {
            CharacteristicItem characteristicItem = new CharacteristicItem();
            characteristicItem.setId(c.getId());
            characteristicItem.setCharacteristic(c.getCharacteristic());

            characteristics.add(characteristicItem);
        }

        return characteristics;
    }
}
