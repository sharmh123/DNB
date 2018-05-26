package Testcodes;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static Testcodes.DNBCore.readInputFile;

public class DNBWords {
    private String configFileName;
    private String inputSentenceType = "word";
    private ConfigContainer configContainer;

    public DNBWords() {
        this.configFileName = DNBCore.getDNBConfigDir() + "/" + "words_config.txt";
        configContainer = new ConfigContainer(configFileName);
        configContainer.setInputSentenceType(inputSentenceType);
    }

    public List<String> getExerciseWords(String inputFileName, int exerciseLen, ConfigContainer configContainer) {
        boolean dualNBack = configContainer.isDualNBack();
        List<String> allWords = readInputFile(inputFileName);
        Random random = new Random(exerciseLen);
        Collections.shuffle(allWords);
        List<String> exerciseWords = allWords.subList(0, exerciseLen);

        if (dualNBack) {
            int randomNumber = random.nextInt(200);
            List<String> secondListOfExerciseWords = allWords.subList(0 + randomNumber, exerciseLen + randomNumber);
            for (int i = 0; i < secondListOfExerciseWords.size(); i++) {
                String combinedWord = exerciseWords.get(i) + ", " + secondListOfExerciseWords.get(i);
                exerciseWords.set(i, combinedWord);
            }
        }

        return exerciseWords;
    }

    public void runGame() throws Exception {

        String inputFileName = configContainer.getInputFileName();
        int exerciseLen = configContainer.getExerciseLen();
        List<String> exerciseWords = getExerciseWords(inputFileName, exerciseLen, configContainer);
        DNBCore.startGameExecution(exerciseWords, configContainer);
        DNBCore.displayResults(configContainer.getInputComparisonFile(), configContainer.getOutputFileName(),
                configContainer.getOutputHistoryFileName());
    }

    public static void main(String[] args) throws Exception {
        DNBWords game = new DNBWords();
        game.runGame();
    }
}


