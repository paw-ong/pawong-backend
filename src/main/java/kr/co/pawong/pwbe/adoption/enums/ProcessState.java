package kr.co.pawong.pwbe.adoption.enums;

import lombok.Getter;

@Getter
public enum ProcessState {
    PROTECTED("보호중"),
    RETURN("종료(반환)"),
    EUTHANASIA("종료(안락사)"),
    NATURAL_DEATH("종료(자연사)"),
    ADOPTION("종료(입양)"),
    DONATION("종료(기증)"),
    TERMINATION("종료(방사)");

    private final String state;

    ProcessState(String state) {
        this.state = state;
    }

    public static ProcessState fromValue(String state) {
        for (ProcessState processState : ProcessState.values()) {
            if (processState.getState().equals(state)) {
                return processState;
            }
        }
        throw new IllegalArgumentException("종류코드 오류: " + state);
    }
}
