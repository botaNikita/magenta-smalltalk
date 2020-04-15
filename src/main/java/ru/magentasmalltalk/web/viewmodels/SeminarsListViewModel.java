package ru.magentasmalltalk.web.viewmodels;

import java.util.List;

public class SeminarsListViewModel {

    List<SeminarViewModel> seminars;

    public List<SeminarViewModel> getSeminars() {
        return seminars;
    }

    public void setSeminars(List<SeminarViewModel> seminars) {
        this.seminars = seminars;
    }
}
