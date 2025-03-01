package com.example.sem3project.model

import android.os.Parcel
import android.os.Parcelable

data class Appointment(
    val id: String = "", // Unique ID for each appointment
    val title: String = "",
    val date: String = "",
    val text: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "", // Read the id, defaulting to empty string if null
        parcel.readString() ?: "", // Read the title
        parcel.readString() ?: "", // Read the date
        parcel.readString() ?: ""  // Read the text
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(date)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Appointment> {
        override fun createFromParcel(parcel: Parcel): Appointment {
            return Appointment(parcel)
        }

        override fun newArray(size: Int): Array<Appointment?> {
            return arrayOfNulls(size)
        }
    }
}
