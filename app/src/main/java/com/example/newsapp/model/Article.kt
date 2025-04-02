package com.example.newsapp.model

import android.os.Parcel
import android.os.Parcelable

data class Article(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val publishedAt: String,
    val urlToImage: String,
    val content: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        TODO("source"),
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(publishedAt)
        parcel.writeString(urlToImage)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article = Article(parcel)

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }

}
