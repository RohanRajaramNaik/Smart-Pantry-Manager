package com.example.smartpantrymanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExpiryHelper {

    public static final int EXPIRY_NONE = 0;
    public static final int EXPIRY_SOON = 1;
    public static final int EXPIRY_PASSED = 2;
    public static final long NO_DATE = Long.MAX_VALUE;

    public static long getDaysLeft(String expiryDate) {
        // No date
        if (expiryDate == null || expiryDate.isEmpty()) {
            return NO_DATE;
        }

        try {
            // Days until expiry
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            format.setLenient(false);
            Date expiry = format.parse(expiryDate);
            long difference = expiry.getTime() - getTodayAtMidnight().getTimeInMillis();
            return Math.round(difference / 86400000.0);
        } catch (Exception e) {
            return NO_DATE;
        }
    }

    public static int getStatus(String expiryDate) {
        long daysLeft = getDaysLeft(expiryDate);

        // Check days
        if (daysLeft == NO_DATE) {
            return EXPIRY_NONE;
        }
        if (daysLeft < 0) {
            return EXPIRY_PASSED;
        }
        if (daysLeft <= 3) {
            return EXPIRY_SOON;
        }
        return EXPIRY_NONE;
    }

    public static boolean isExpiring(String expiryDate) {
        return getStatus(expiryDate) != EXPIRY_NONE;
    }

    public static String makeDaysLeftText(String expiryDate) {
        long daysLeft = getDaysLeft(expiryDate);

        // Pick wording
        if (daysLeft == NO_DATE) {
            return "";
        }
        if (daysLeft < 0) {
            return "Expired";
        }
        if (daysLeft == 0) {
            return "Expires today";
        }
        if (daysLeft == 1) {
            return "1 day left";
        }
        return daysLeft + " days left";
    }

    public static ArrayList<PantryItem> getExpiringItems(ArrayList<PantryItem> pantry) {
        ArrayList<PantryItem> expiring = new ArrayList<PantryItem>();

        // Keep expiring items
        for (int i = 0; i < pantry.size(); i++) {
            if (isExpiring(pantry.get(i).getExpiryDate())) {
                expiring.add(pantry.get(i));
            }
        }
        sortBySoonest(expiring);
        return expiring;
    }

    private static void sortBySoonest(ArrayList<PantryItem> items) {
        // Insertion sort
        for (int i = 1; i < items.size(); i++) {
            PantryItem item = items.get(i);
            long itemDays = getDaysLeft(item.getExpiryDate());
            int j = i - 1;

            // Shift later dates
            while (j >= 0 && getDaysLeft(items.get(j).getExpiryDate()) > itemDays) {
                items.set(j + 1, items.get(j));
                j--;
            }
            items.set(j + 1, item);
        }
    }

    private static Calendar getTodayAtMidnight() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        return today;
    }
}
