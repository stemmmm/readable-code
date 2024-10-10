package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;

public class StudyCafeApplication {

    public static void main(String[] args) {
        StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(new InputHandler(), new OutputHandler());
        studyCafePassMachine.run();
    }

}
