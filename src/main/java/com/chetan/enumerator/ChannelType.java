package com.chetan.enumerator;

public enum ChannelType {
    slack, email;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
