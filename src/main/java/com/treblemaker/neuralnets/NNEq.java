package com.treblemaker.neuralnets;

import com.treblemaker.controllers.classify.utils.ClassificationUtils;
import com.treblemaker.controllers.data.EqBandController;
import com.treblemaker.model.eq.EqWeightResponse;
import com.treblemaker.model.parametriceq.eqprediction.ParametricDto;
import com.treblemaker.model.parametriceq.eqprediction.ParametricDtoBand;
import com.treblemaker.model.parametriceq.eqprediction.ParametricDtoRow;
import com.treblemaker.weighters.enums.WeightClass;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.cpu.nativecpu.NDArray;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.HashMap;

import static com.treblemaker.model.IParametricEq.EqBand;

public class NNEq {

    private EqBandController eqBandController;

    private WeightClassificationUtils weightClassificationUtils;

    public NNEq(EqBandController eqBandController, WeightClassificationUtils weightClassificationUtils){
        this.eqBandController = eqBandController;
        this.weightClassificationUtils = weightClassificationUtils;
    }

    private HashMap<EqBand, MultiLayerNetwork> nnMap = new HashMap<>();

    public Integer trainNetWork(int harmonicLoopId, int beatLoopId) throws URISyntaxException, IOException, InterruptedException {

        EqWeightResponse eqWeightResponse = new EqWeightResponse();

        HashMap<EqBand, Integer> freqToRating = new HashMap<>();

        ParametricDto parametricDto = eqBandController.Get("one", harmonicLoopId, beatLoopId);

        for (EqBand freq : EqBand.values()) {
            createFreqencyCsv(freq, parametricDto);
            trainNnetIfNeeded(freq);

            ParametricDtoBand parametricDtoBand = parametricDto.getBandByEq(freq);
            ParametricDtoRow targetRow = parametricDtoBand.getTarget();

            INDArray inputArray = new NDArray(new float[]{(float)(double)targetRow.getHarm(),(float)(double)targetRow.getBeat()});
            INDArray predictionOutput = nnMap.get(freq).output(inputArray, false);

            freqToRating.put(freq, ClassificationUtils.extractRating(predictionOutput));

            eqWeightResponse.addRating(ClassificationUtils.extractRating(predictionOutput), freq);
        }

        WeightClass weightClass = weightClassificationUtils.eqResponseToWeightClass(eqWeightResponse);

        return weightClass.getValue();
    }

    public void trainNnetIfNeeded(EqBand freq) throws IOException, InterruptedException {
        if (nnMap.get(freq) == null) {
            int seed = 123;
            double learningRate = 0.1;
            int batchSize = 50;
            int nEpochs = 20;

            int numInputs = 2;
            int numOutputs = 3;
            int numHiddenNodes = 5;

            //Load the training data:
            RecordReader rr = new CSVRecordReader();

            rr.initialize(new FileSplit(Paths.get("freqdata", freq.toString() + ".csv").toFile()));
            DataSetIterator trainIter = new RecordReaderDataSetIterator(rr, batchSize, 0, 3);

            MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                    .seed(seed)
                    .iterations(2)
                    .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                    .learningRate(learningRate)
                    .updater(Updater.NESTEROVS).momentum(0.9)
                    .list()
                    .layer(0, new DenseLayer.Builder().nIn(numInputs).nOut(numHiddenNodes)
                            .weightInit(WeightInit.XAVIER)
                            .activation("relu")
                            .build())
                    .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                            .weightInit(WeightInit.XAVIER)
                            .activation("softmax").weightInit(WeightInit.XAVIER)
                            .nIn(numHiddenNodes).nOut(numOutputs).build())
                    .pretrain(false).backprop(true).build();

            MultiLayerNetwork model = new MultiLayerNetwork(conf);
            model.init();
            model.setListeners(new ScoreIterationListener(10));  //Print score every 10 parameter updates

            for (int n = 0; n < nEpochs; n++) {
                model.fit(trainIter);
            }

            nnMap.put(freq, model);
        }
    }

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    public void createFreqencyCsv(EqBand eqBand, ParametricDto parametricDto) throws IOException {
        String filePath = Paths.get("freqdata", eqBand.name() + ".csv").toString();

        //DELETE ..
        deleteIfExisits(filePath);

        //WRITE
        FileWriter writer = new FileWriter(filePath);

        for (ParametricDtoBand parametricDtoBand : parametricDto.getEqBands()) {

            if (parametricDtoBand.getFreq().equals(eqBand)) {

                for (ParametricDtoRow row : parametricDtoBand.getRows()) {

                    writer.write(convertRatingToInt(row.getRating()));
                    writer.write(COMMA_DELIMITER);
                    writer.write(Double.toString(row.getBeat()));
                    writer.write(COMMA_DELIMITER);
                    writer.write(Double.toString(row.getHarm()));
                    writer.write(NEW_LINE_SEPARATOR);
                }
            }
        }

        writer.flush();
        writer.close();
    }


    private void deleteIfExisits(String filePath) {
        File file = new File(filePath);

        if (file.delete()) {
            System.out.println(file.getName() + " is deleted!");
        } else {
            System.out.println("Delete operation is failed.");
        }
    }

    private String convertRatingToInt(String rating) {

        if ("bad".equalsIgnoreCase(rating)) {
            return "0";
        }

        if ("ok".equalsIgnoreCase(rating)) {
            return "1";
        }

        if ("good".equalsIgnoreCase(rating)) {
            return "2";
        }

        throw new RuntimeException("Could not convert rating to int");
    }
}