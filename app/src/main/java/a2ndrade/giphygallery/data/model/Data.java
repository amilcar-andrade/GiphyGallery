package a2ndrade.giphygallery.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {

    public final String type;
    public final String url;
    public final String username;
    public final String rating;
    public final String import_datetime;
    public final String trending_datetime;
    public final Images images;


    protected Data(Parcel in) {
        type = in.readString();
        url = in.readString();
        username = in.readString();
        rating = in.readString();
        import_datetime = in.readString();
        trending_datetime = in.readString();
        images = in.readParcelable(Images.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(url);
        dest.writeString(username);
        dest.writeString(rating);
        dest.writeString(import_datetime);
        dest.writeString(trending_datetime);
        dest.writeParcelable(images, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
