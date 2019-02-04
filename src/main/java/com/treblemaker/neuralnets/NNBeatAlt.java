package com.treblemaker.neuralnets;

import com.treblemaker.configs.AppConfigs;
import com.treblemaker.controllers.classify.utils.ClassificationUtils;
import com.treblemaker.utils.HttpHelper;
import org.apache.commons.compress.utils.IOUtils;
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
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.cpu.nativecpu.NDArray;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NNBeatAlt {

    private AppConfigs appConfigs;

    private MultiLayerNetwork model = null;
    private String apiUser;
    private String apiPassword;

    public NNBeatAlt(AppConfigs appConfigs, String apiUser, String apiPassword) {
        this.appConfigs = appConfigs;
        this.apiPassword = apiPassword;
        this.apiUser = apiUser;
    }

    public Integer trainBeatAltNetWork(float[] predictionInputs, String serverPort) throws URISyntaxException, IOException, InterruptedException {

        if(model == null) {

            //get the number of inputs!!
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(apiUser, apiPassword));
            Integer inputs = restTemplate.getForObject("http://localhost:" + serverPort + "/beatalt/inputcount", Integer.class);
            System.out.println("INPUTS:" + inputs);

            int seed = 123;
            double learningRate = 0.01;
            int batchSize = 50;
            int nEpochs = 30;

            int numInputs = inputs;
            int numOutputs = 3;
            int numHiddenNodes = inputs;

            //Load the training data:
            RecordReader rr = new CSVRecordReader();

            File tempFile;
            String OS = System.getProperty("os.name").toLowerCase();
            if (OS.indexOf("win") >= 0) {
                tempFile = new File("C:\\targetFileBeat.txt");
            } else {
                tempFile = new File(appConfigs.getTrainedModelsDir() + "/targetFileBeat.txt");
                if(!tempFile.exists()) {
                    Path pathToFile = Paths.get(appConfigs.getTrainedModelsDir() + "/targetFileBeat.txt");
                    Files.createDirectories(pathToFile.getParent());
                    Files.createFile(pathToFile);
                    tempFile = new File(appConfigs.getTrainedModelsDir() + "/targetFileBeat.txt");
                }
            }

            //tempFile.deleteOnExit();
            FileOutputStream out = new FileOutputStream(tempFile);
            HttpHelper httpHelper = new HttpHelper();
            IOUtils.copy(httpHelper.getURLConnection("http://localhost:" + serverPort + "/beatalt/data", apiUser, apiPassword).getInputStream(), out);


            rr.initialize(new FileSplit(tempFile));
            DataSetIterator trainIter = new RecordReaderDataSetIterator(rr, batchSize, 0, 3);

            MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                    .seed(seed)
                    .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                    .updater(new Nesterovs(learningRate))
                    .list()
                    .layer(0, new DenseLayer.Builder().nIn(numInputs).nOut(numHiddenNodes)
                            .weightInit(WeightInit.XAVIER)
                            .activation(Activation.RELU)
                            .build())
                    .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                            .weightInit(WeightInit.XAVIER)
                            .activation(Activation.SOFTMAX).weightInit(WeightInit.XAVIER)
                            .nIn(numHiddenNodes).nOut(numOutputs).build())
                    .pretrain(false).backprop(true).build();

            model = new MultiLayerNetwork(conf);
            model.init();
            model.setListeners(new ScoreIterationListener(10));  //Print score every 10 parameter updates

            for (int n = 0; n < nEpochs; n++) {
                model.fit(trainIter);
            }
        }

        INDArray inputArray = new NDArray(predictionInputs);
        INDArray predictionOutput = model.output(inputArray,false);

        return ClassificationUtils.extractRating(predictionOutput);
    }
}
