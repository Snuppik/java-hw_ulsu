package edu.phystech.hw2.contact;

import java.util.regex.Pattern;

record Contact(String username, String email) implements Comparable<Contact> {
    public static final String UNKNOWN_EMAIL = "unknown";

    private static final Pattern GMAIL_PATTERN = Pattern.compile(".+@gmail\\.com");

    Contact(String username) {
        this(username, UNKNOWN_EMAIL);
    }

    Contact {
        if (username == null || username.isBlank()) {
            throw new InvalidContactFieldException("username");
        }
        if (email == null || email.isBlank()) {
            throw new InvalidContactFieldException("email");
        }
        if (!UNKNOWN_EMAIL.equals(email) && !GMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidContactFieldException("email");
        }
    }

    @Override
    public int compareTo(Contact o) {
        return Integer.compare(this.username.length(), o.username.length());
    }
}