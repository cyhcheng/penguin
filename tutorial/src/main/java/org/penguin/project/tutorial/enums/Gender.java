package org.penguin.project.tutorial.enums;

public enum Gender {

    Male(1),
    Female(0);

    private Integer index;

    public Integer getIndex() {
        return index;
    }

    Gender(Integer index) {
        this.index = index;
    }

    public static Gender getGender(int index) {
        for (Gender gender : Gender.values()) {
            if (gender.getIndex() == index) {
                return gender;
            }
        }
        return null;
    }


}
