package model.value;

import model.type.RefType;
import model.type.Type;

public class RefValue implements Value{
    int address;
    Type locationType;
    public RefValue(int address,Type locationType){
        this.address=address;
        this.locationType=locationType;
    }
    public int getAddr() {return address;}
     @Override
     public Type getType() { return new RefType(locationType);}

    public Type getLocationType() {
        return locationType;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public void setLocationType(Type locationType) {
        this.locationType = locationType;
    }

    @Override
    public String toString() {
        return "RefValue("+ address+", " +locationType+
                ")";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RefValue;
    }
}
