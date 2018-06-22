package com.secondsave.health_med;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class Places implements Serializable,Place {
    String id;
    List<Integer> placetypes;
    @Override
    public String getId() {
        return null;
    }

    @Override
    public List<Integer> getPlaceTypes() {
        return null;
    }

    @Nullable
    @Override
    public CharSequence getAddress() {
        return null;
    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public CharSequence getName() {
        return null;
    }

    @Override
    public LatLng getLatLng() {
        return null;
    }

    @Nullable
    @Override
    public LatLngBounds getViewport() {
        return null;
    }

    @Nullable
    @Override
    public Uri getWebsiteUri() {
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPhoneNumber() {
        return null;
    }

    @Override
    public float getRating() {
        return 0;
    }

    @Override
    public int getPriceLevel() {
        return 0;
    }

    @Nullable
    @Override
    public CharSequence getAttributions() {
        return null;
    }

    @Override
    public Place freeze() {
        return null;
    }

    @Override
    public boolean isDataValid() {
        return false;
    }
}
