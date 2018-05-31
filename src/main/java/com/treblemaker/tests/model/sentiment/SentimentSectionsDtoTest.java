package com.treblemaker.tests.model.sentiment;

import com.treblemaker.SpringConfiguration;
import com.treblemaker.model.sentiment.SentimentSection;
import com.treblemaker.model.sentiment.SentimentSectionDto;
import com.treblemaker.model.sentiment.TrackToLabels;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringConfiguration.class)
public class SentimentSectionsDtoTest {

    SentimentSection sentimentSection = new SentimentSection();

    @Before
    public void setup(){

        Map<String, List<String>> trackToLabelsOne = new HashMap<>();
        trackToLabelsOne.put("A", Arrays.asList("l1", "l4"));
        trackToLabelsOne.put("B", Arrays.asList("l2", "l3"));
        trackToLabelsOne.put("C", Arrays.asList("l2", "l4"));
        trackToLabelsOne.put("E", Arrays.asList("l1", "l4"));
        trackToLabelsOne.put("F", Arrays.asList("l1", "l1"));

        sentimentSection.setSentimentLabels(trackToLabelsOne);
    }

    @Test
    public void populateSentimentLabels(){

        SentimentSectionDto sentimentSectionsDto = new SentimentSectionDto(sentimentSection);

        List<TrackToLabels> trackToLabelsList = sentimentSectionsDto.getTrackToLabelsList();

        for(TrackToLabels trackToLabels : trackToLabelsList){
            if(trackToLabels.getName().equalsIgnoreCase("l1")){
                assertThat(trackToLabels.getLabels()).hasSize(3);
            }
        }

        for(TrackToLabels trackToLabels : trackToLabelsList){
            if(trackToLabels.getName().equalsIgnoreCase("l2")){
                assertThat(trackToLabels.getLabels()).hasSize(2);
            }
        }

        for(TrackToLabels trackToLabels : trackToLabelsList){
            if(trackToLabels.getName().equalsIgnoreCase("l3")){
                assertThat(trackToLabels.getLabels()).hasSize(1);
                assertThat(trackToLabels.getLabels().get(0)).isEqualToIgnoringCase("B");
            }
        }

        for(TrackToLabels trackToLabels : trackToLabelsList){
            if(trackToLabels.getName().equalsIgnoreCase("l4")){
                assertThat(trackToLabels.getLabels()).hasSize(3);
            }
        }
    }

}