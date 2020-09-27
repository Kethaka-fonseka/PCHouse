package com.example.pchouse;

import android.os.Parcel;
import android.os.Parcelable;

public class Item  implements Parcelable {
    private String ID;
    private String Name;
    private double Price;
    private int Qty;
    private String url;

    public Item() {
    }

    protected Item(Parcel in) {
        ID = in.readString();
        Name = in.readString();
        Price = in.readDouble();
        Qty = in.readInt();
        url = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ID);
        parcel.writeString(Name);
        parcel.writeDouble(Price);
        parcel.writeInt(Qty);
        parcel.writeString(url);
    }
}
