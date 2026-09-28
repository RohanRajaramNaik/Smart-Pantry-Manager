package com.example.smartpantrymanager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PantryItem {

    public static final int EXPIRY_NONE = 0;
    public static final int EXPIRY_SOON = 1;
    public static final int EXPIRY_PASSED = 2;

    private int id;
    private String name;
    private double quantity;
    private String unit;
    private String expiryDate;

    public PantryItem(int id, String name, double quantity, String unit, String expiryDate) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getExpiryStatus() {
        // No date
        if (expiryDate == null || expiryDate.isEmpty()) {
            return EXPIRY_NONE;
        }

        try {
            // Check status
            return statusForDays(getDaysLeft());
        } catch (Exception e) {
            return EXPIRY_NONE;
        }
    }

    private long getDaysLeft() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        format.setLenient(false);
        Date expiry = format.parse(expiryDate);
        long difference = expiry.getTime() - getTodayAtMidnight().getTimeInMillis();
        return Math.round(difference / 86400000.0);
    }

    private int statusForDays(long daysLeft) {
        if (daysLeft < 0) {
            return EXPIRY_PASSED;
        }
        if (daysLeft <= 3) {
            return EXPIRY_SOON;
        }
        return EXPIRY_NONE;
    }

    private Calendar getTodayAtMidnight() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        return today;
    }
}
