package io.github.prathameshpatel.clientmanager.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Prathamesh Patel on 12/13/2017.
 */

@Entity(tableName = "clients")
public class Client {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "client_id")
    private int clientId;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    // Keep the default name for the column name
    private String address;
    private String phone;

    @ColumnInfo(name = "image_name")
    private String imageName;

    @ColumnInfo(name = "is_favorite")
    private int isFavorite = 0;

//    private final String picture = firstName+lastName+"picture";

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int ID) {
        this.clientId = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fname) {
        this.firstName = fname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lname) {
        this.lastName = lname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFav) {
        this.isFavorite = isFav;
    }

    public Client() {} //Empty Constructor

    public Client(int ID, String fname, String lname, String address, String phone) {
        this.clientId = ID;
        this.firstName = fname;
        this.lastName = lname;
        this.address = address;
        this.phone = phone;
    }

    public Client(String fname, String lname, String address, String phone) {
        this.firstName = fname;
        this.lastName = lname;
        this.address = address;
        this.phone = phone;
    }

    public String toString() {
        return String.format("id=%d first_name=%s last_name=%s address=%s phone=%s isFavorite=%d image_name=%s"
                ,clientId,firstName,lastName,address,phone,isFavorite,imageName);
    }
}