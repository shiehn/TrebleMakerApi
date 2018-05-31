package com.treblemaker.dal;

import com.treblemaker.dal.interfaces.*;
import com.treblemaker.model.BeatLoop;
import com.treblemaker.model.HarmonicLoop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DALCacheFillService {

    @Autowired
    private ICompositionDal compositionDal;

    @Autowired
    private IAnalyticsVerticalDal analyticsVerticalDal;

    @Autowired
    private IBeatLoopsDal beatLoopsDal;

    @Autowired
    private IHarmonicLoopsDal harmonicLoopsDal;

    @Autowired
    private IBasslineDal basslineDal;

    @Autowired
    private IArpeggioDal arpeggioDal;


    //NEW ONES:
    //NEW ONES:
    //NEW ONES:
    @Autowired
    private IFXArpeggioDelayDal arpeggioDelayDal;

    @Autowired
    private IFXArpeggioDelayRatingDal arpeggioDelayRatingDal;

    @Autowired
    private ICompositionTimeSlotDal compositionTimeSlotDal;

    @Autowired
    private ICompositionTimeSlotLevelsDal compositionTimeSlotLevelsDal;


    public void fillCache(){

        //get timeslots where fx is not null
        //get arpeggio for timeslot
        //get fx_arpeggio_delay fro timeslot
        //get arpeggio_id from fx_arpeggio_delay_rating

        /*
        delay_type
        delay_volume
        master_volume
         */

        if (DALCache.getInstance().getAnalyticsVertical() == null) {
            DALCache.getInstance().setAnalyticsVertical(analyticsVerticalDal.findAll());
        }

        if (DALCache.getInstance().getBeatLoops() == null) {
            DALCache.getInstance().setBeatLoops(beatLoopsDal.findByNormalizedLength(BeatLoop.ALREADY_NORMALIZED));
        }

        if (DALCache.getInstance().getHarmonicLoops() == null) {
            DALCache.getInstance().setHarmonicLoops(harmonicLoopsDal.findByNormalizedLength(HarmonicLoop.ALREADY_NORMALIZED));
        }

        if (DALCache.getInstance().getCompositions() == null) {
            DALCache.getInstance().setCompositions(compositionDal.findAll());
        }

        if (DALCache.getInstance().getBasslines() == null) {
            DALCache.getInstance().setBasslines(basslineDal.findAll());
        }

        if (DALCache.getInstance().getArpeggios() == null) {
            DALCache.getInstance().setArpeggios(arpeggioDal.findAll());
        }

        if (DALCache.getInstance().getCompositionTimeSlots() == null) {
            DALCache.getInstance().setCompositionTimeSlots(compositionTimeSlotDal.findByRated(1));
        }

        if(DALCache.getInstance().getCompositionTimeSlotLevels() == null){
            DALCache.getInstance().setCompositionTimeSlotLevels(compositionTimeSlotLevelsDal.findAll());
        }

        if (DALCache.getInstance().getFxArpeggioDelays() == null) {
            DALCache.getInstance().setFxArpeggioDelays(arpeggioDelayDal.findAll());
        }

        if (DALCache.getInstance().getFxArpeggioDelayRatings() == null) {
            DALCache.getInstance().setFxArpeggioDelayRatings(arpeggioDelayRatingDal.findAll());
        }
    }
}
