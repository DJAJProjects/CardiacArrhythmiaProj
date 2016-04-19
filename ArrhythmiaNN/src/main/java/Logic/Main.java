package Logic;

import View.MainForm;

/**
 * Created by Kuba on 16.04.2016.
 */
public class Main {

    public static void main(String[] args) {
        SupervisedLearning sl = new SupervisedLearning();
        sl.initialise();
        MainForm.main(args);
    }

}

