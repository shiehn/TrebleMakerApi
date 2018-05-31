package com.treblemaker.utils;

import com.treblemaker.model.RhythmicAccents;

import java.util.ArrayList;
import java.util.List;

public class rythmTemplateParser
{
    public static List<RhythmicAccents> ParseAccents(List<Boolean> accentArray)
    {

        List<Boolean> accentsSection = new ArrayList<>();

        //determine how many accent arrays to make ..
        int numOfAccents = accentArray.size() / 16;

        List<List<Boolean>> accentsSections = new ArrayList<>();

        List<RhythmicAccents> accentCollection = new ArrayList<>();

        int currentAccentSelection = -1;
        for (int i = 0; i < accentArray.size(); i++)
        {
            if (i % 16 == 0)
            {
                currentAccentSelection++;
                accentsSections.add(new ArrayList<>());
            }

            List<Boolean> temp = accentsSections.get(currentAccentSelection);

            temp.add(accentArray.get(i));

            //accentsSections[currentAccentSelection].Add(accentArray[i]);
        }

        for (int j = 0; j < accentsSections.size(); j++)
        {
            List<Boolean> currentAccents = accentsSections.get(j);
            RhythmicAccents rhythmicAccents = new RhythmicAccents();

            for (int k = 0; k < currentAccents.size(); k++)
            {
                rhythmicAccents.setOneOne(boolToString(currentAccents.get(0)));
                rhythmicAccents.setOneTwo(boolToString(currentAccents.get(1)));
                rhythmicAccents.setOneThree(boolToString(currentAccents.get(2)));
                rhythmicAccents.setOneFour(boolToString(currentAccents.get(3)));

                rhythmicAccents.setTwoOne(boolToString(currentAccents.get(4)));
                rhythmicAccents.setTwoTwo(boolToString(currentAccents.get(5)));
                rhythmicAccents.setTwoThree(boolToString(currentAccents.get(6)));
                rhythmicAccents.setTwoFour(boolToString(currentAccents.get(7)));

                rhythmicAccents.setThreeOne(boolToString(currentAccents.get(8)));
                rhythmicAccents.setThreeTwo(boolToString(currentAccents.get(9)));
                rhythmicAccents.setThreeThree(boolToString(currentAccents.get(10)));
                rhythmicAccents.setThreeFour(boolToString(currentAccents.get(11)));

                rhythmicAccents.setFourOne(boolToString(currentAccents.get(12)));
                rhythmicAccents.setFourTwo(boolToString(currentAccents.get(13)));
                rhythmicAccents.setFourThree(boolToString(currentAccents.get(14)));
                rhythmicAccents.setFourFour(boolToString(currentAccents.get(15)));
            }

            accentCollection.add(rhythmicAccents);
        }

        return accentCollection;
    }

    private static String boolToString(Boolean bool){

        if(bool){
            return "1";
        }else{
            return "0";
        }
    }
}