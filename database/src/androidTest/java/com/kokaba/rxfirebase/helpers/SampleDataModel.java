package com.kokaba.rxfirebase.helpers;

public class SampleDataModel {

    public SampleDataModel() {
    }

    public SampleDataModel(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String name;
    public String surname;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SampleDataModel) {
            return ((SampleDataModel) obj).name.equals(this.name) &&
                ((SampleDataModel) obj).surname.equals(this.surname);
        }
        return false;
    }
}
