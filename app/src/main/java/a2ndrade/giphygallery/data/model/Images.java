package a2ndrade.giphygallery.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Images implements Parcelable {
    public final Image downsized_still;
    public final Image original_still;

    protected Images(Parcel in) {
        downsized_still = in.readParcelable(Image.class.getClassLoader());
        original_still = in.readParcelable(Image.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(downsized_still, flags);
        dest.writeParcelable(original_still, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Images> CREATOR = new Creator<Images>() {
        @Override
        public Images createFromParcel(Parcel in) {
            return new Images(in);
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };
}
