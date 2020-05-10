package com.vignesh.healthcare.entity;

public class MedicineEntity {
    private String name;
    private String description;
    private String duration;
    private boolean morning;
    private boolean afternoon;
    private boolean night;
    private boolean after_meal;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }

    public boolean isMorning() {
        return morning;
    }

    public boolean isAfternoon() {
        return afternoon;
    }

    public boolean isNight() {
        return night;
    }

    public boolean isAfter_meal() {
        return after_meal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public void setAfternoon(boolean afternoon) {
        this.afternoon = afternoon;
    }

    public void setNight(boolean night) {
        this.night = night;
    }

    public void setAfter_meal(boolean after_meal) {
        this.after_meal = after_meal;
    }
}
