package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    public StudyCafePassMachine(InputHandler inputHandler, OutputHandler outputHandler) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.askPassTypeSelection();
            StudyCafePassType selectedPassType = inputHandler.getPassTypeSelectingUserAction();

            if (isSelectedPassTypeHourly(selectedPassType)) {
                List<StudyCafePass> hourlyPasses = getStudyCafePassesTypeOf(StudyCafePassType.HOURLY);
                outputHandler.showPassListForSelection(hourlyPasses);
                StudyCafePass selectedPass = inputHandler.getSelectPass(hourlyPasses);
                outputHandler.showPassOrderSummary(selectedPass);
            }

            if (isSelectedPassTypeWeekly(selectedPassType)) {
                List<StudyCafePass> weeklyPasses = getStudyCafePassesTypeOf(StudyCafePassType.WEEKLY);
                outputHandler.showPassListForSelection(weeklyPasses);
                StudyCafePass selectedPass = inputHandler.getSelectPass(weeklyPasses);
                outputHandler.showPassOrderSummary(selectedPass);
            }

            if (isSelectedPassTypeFixed(selectedPassType)) {
                List<StudyCafePass> fixedPasses = getStudyCafePassesTypeOf(StudyCafePassType.FIXED);
                outputHandler.showPassListForSelection(fixedPasses);
                StudyCafePass selectedPass = inputHandler.getSelectPass(fixedPasses);
                Optional<StudyCafeLockerPass> lockerPass = getLockerPass(selectedPass);

                lockerPass.ifPresentOrElse(
                    pass -> {
                        outputHandler.askLockerPass(pass);

                        if (ifUserSelectsLocker()) {
                            outputHandler.showPassOrderSummaryWithLockerPass(selectedPass, pass);
                        }
                    },
                    () -> outputHandler.showPassOrderSummary(selectedPass)
                );
            }
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private boolean isSelectedPassTypeHourly(StudyCafePassType studyCafePassType) {
        return studyCafePassType == StudyCafePassType.HOURLY;
    }

    private boolean isSelectedPassTypeWeekly(StudyCafePassType studyCafePassType) {
        return studyCafePassType == StudyCafePassType.WEEKLY;
    }

    private boolean isSelectedPassTypeFixed(StudyCafePassType studyCafePassType) {
        return studyCafePassType == StudyCafePassType.FIXED;
    }

    private List<StudyCafePass> getStudyCafePassesTypeOf(StudyCafePassType passType) {
        StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();

        return studyCafePasses.stream()
            .filter(studyCafePass -> studyCafePass.isEqualToType(passType))
            .toList();
    }

    private Optional<StudyCafeLockerPass> getLockerPass(StudyCafePass selectedPass) {
        StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();

        return lockerPasses.stream()
            .filter(lockerPass -> lockerPass.isEqualTo(selectedPass))
            .findFirst();
    }

    private boolean ifUserSelectsLocker() {
        return inputHandler.getLockerSelection();
    }
}
