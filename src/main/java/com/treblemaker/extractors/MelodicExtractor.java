package com.treblemaker.extractors;

import com.treblemaker.extractors.model.HarmonyExtraction;
import com.treblemaker.model.HarmonicLoop;
import com.treblemaker.model.progressions.ProgressionUnit;
import com.treblemaker.model.progressions.ProgressionUnitBar;
import com.treblemaker.model.queues.QueueState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MelodicExtractor {

    public QueueState extractedAndSetHarmony(QueueState queueState) {
        for (ProgressionUnit pUnit : queueState.getStructure()) {
            for (int i = 0; i < pUnit.getProgressionUnitBars().size(); i++) {
                List<HarmonyExtraction> extractions = createHarmonicExtractionForBar(pUnit.getProgressionUnitBars().get(i));
                pUnit.getProgressionUnitBars().get(i).setHarmonicExtractions(extractions);
            }

        }

        return queueState;
    }

    public List<HarmonyExtraction> createHarmonicExtractionForBar(ProgressionUnitBar progressionUnitBar) {

        List<HarmonyExtraction> harmonyExtractions = new ArrayList<>();

        for (int noteIndex = 0; noteIndex < 16; noteIndex++) {
            harmonyExtractions.add(extractedAndSetHarmony(progressionUnitBar, noteIndex));
        }

        return harmonyExtractions;
    }

    public HarmonyExtraction extractedAndSetHarmony(ProgressionUnitBar progressionUnitBar, int noteIndex) {
        HarmonyExtraction harmonicExtraction = new HarmonyExtraction();
        harmonicExtraction.setChordname(progressionUnitBar.getChord().getChordName());

        List<String> harmonies = new ArrayList<>();
        if (!progressionUnitBar.getArpeggioLowPositions().get(noteIndex).equalsIgnoreCase("R")) {
            harmonies.add(progressionUnitBar.getArpeggioLowPositions().get(noteIndex));
        }

        if (!progressionUnitBar.getArpeggioHiPositions().get(noteIndex).equalsIgnoreCase("R")) {
            harmonies.add(progressionUnitBar.getArpeggioHiPositions().get(noteIndex));
        }

        //EXTRACT HARMONIC LOOPS ..
        HarmonicLoop hLoop = progressionUnitBar.getHarmonicLoop();
        String[] harmonicNotesAtIndex = hLoop.getPitchExtractions().get(hLoop.getCurrentBar() - 1).getAsList().get(noteIndex);
        if(harmonicNotesAtIndex != null){
            harmonies.addAll(Arrays.asList(harmonicNotesAtIndex));
        }

        //EXTRAC HARMONIC LOOP ALT ..
        HarmonicLoop hAltLoop = progressionUnitBar.getHarmonicLoopAlt();
        String[] harmonicAltNotesAtIndex = hAltLoop.getPitchExtractions().get(hAltLoop.getCurrentBar() - 1).getAsList().get(noteIndex);
        if(harmonicAltNotesAtIndex != null){
            harmonies.addAll(Arrays.asList(harmonicAltNotesAtIndex));
        }

        harmonicExtraction.setHarmonies(harmonies);

        return harmonicExtraction;
    }
}
