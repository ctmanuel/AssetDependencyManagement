package com.company.model;

/**
 * Dependency Model Object
 * <P>Model representing the Dependency class.
 * Holds the fromAsset model and toAsset model.
 * </P>
 */
public class Dependency {
    private Asset fromAsset;
    private Asset toAsset;

    public Dependency(Asset fromAsset, Asset toAsset) {
        this.fromAsset = fromAsset;
        this.toAsset = toAsset;
    }

    public Asset getFromAsset() {
        return fromAsset;
    }

    public Asset getToAsset() {
        return toAsset;
    }

    @Override
    public String toString(){
        return "[" + fromAsset.getId() + "," + toAsset.getId() + "]";
    }
}
