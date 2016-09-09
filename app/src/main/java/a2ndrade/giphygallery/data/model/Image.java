package a2ndrade.giphygallery.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Basic image model
 */
public class Image implements Parcelable {

    public final String url;
    public final String width;
    public final String height;

    protected Image(Parcel in) {
        url = in.readString();
        width = in.readString();
        height = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(width);
        dest.writeString(height);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
